import javax.swing.Spring;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.Input.Keys;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Exception;
import java.awt.Point;

public class ColourGame implements ApplicationListener, ContactListener{
	SpriteBatch batch;
	Sprite player;
	Texture img;
	World gameWorld;
	Body pBody;
	Body screenEdge;
	Box2DDebugRenderer debug;
	Matrix4 matrix;
	OrthographicCamera camera;
	BitmapFont font;
	String[][] level;
	Tile[][] levelMap;
	File levelFile;
	boolean jumping = false;
	int lvlH;
	int lvlW;

	float torque = 0.0f;
	boolean drawSprite = true;

	final float PIXELS_TO_METERS = 64f;
	final float MAX_VELOCITY = 7f;
	

	public void create () {

		batch = new SpriteBatch();
		img = new Texture("assets/sprites/player.png");
		player = new Sprite(img);
		player.setSize(64, 64);

		//player.setPosition(- player.getWidth()/2, - player.getHeight()/2);
		player.setPosition(0, 0);

		gameWorld = new World(new Vector2(0, -15f), true);
		gameWorld.setContactListener(this);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set((player.getX() + player.getWidth()/2)*PIXELS_TO_METERS, (player.getY() + player.getHeight()/2)*PIXELS_TO_METERS);
		pBody = gameWorld.createBody(bodyDef);
		pBody.setFixedRotation(true);
		pBody.setUserData("THEPLAYER");

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(player.getWidth() / 2 / PIXELS_TO_METERS, player.getHeight() / 2 / PIXELS_TO_METERS);


		FixtureDef bodyFix = new FixtureDef();
		bodyFix.shape = shape;
		bodyFix.density = 2f;
		bodyFix.friction = 10f;
		bodyFix.restitution = 0.0f;



		pBody.createFixture(bodyFix);

		shape.dispose();

		FixtureDef platFix = new FixtureDef();
		PolygonShape platShape = new PolygonShape();
		platShape.setAsBox(128 / 2 / PIXELS_TO_METERS, 64 / 2 / PIXELS_TO_METERS, new Vector2(128/PIXELS_TO_METERS, -407/PIXELS_TO_METERS), 0f);
		platFix.shape = platShape;

		BodyDef edgeDef = new BodyDef();
		edgeDef.type = BodyDef.BodyType.StaticBody;
		float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
		float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS - 50/PIXELS_TO_METERS;

		edgeDef.position.set(0,0);
		FixtureDef edgeFix = new FixtureDef();

		EdgeShape edgeShape = new EdgeShape();
		edgeShape.set(-w/2, -h/2, w/2, -h/2);
		edgeFix.shape = edgeShape;

		screenEdge = gameWorld.createBody(edgeDef);
		screenEdge.createFixture(edgeFix);
		screenEdge.createFixture(platFix);

		edgeShape.dispose();
		platShape.dispose();

		loadMap(new File("assets/levels/level_1.txt"));
		debug = new Box2DDebugRenderer();
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public void render () {
		gameWorld.step(1f/60f, 6, 2);
		camera.position.set(pBody.getPosition().x * PIXELS_TO_METERS, pBody.getPosition().y * PIXELS_TO_METERS, 0);
		camera.update();

		pBody.applyTorque(torque, true);
		player.setPosition((pBody.getPosition().x * PIXELS_TO_METERS) - player.getWidth()/2, (pBody.getPosition().y * PIXELS_TO_METERS) - player.getHeight()/2);
		player.setRotation((float)Math.toDegrees(pBody.getAngle()));

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		matrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0);
		batch.begin();

		player.draw(batch);
		//batch.draw(player, player.getX(), player.getY(), player.getOriginX(), player.getOriginY(), player.getWidth(), player.getHeight(), player.getScaleX(), player.getScaleY(), player.getRotation());

		for(int i = 0; i < levelMap.length; i++){
			for(int j = 0; j < levelMap[0].length; j++){
				float bx = levelMap[i][j].getBody().getPosition().x;
				float by = levelMap[i][j].getBody().getPosition().y;
				float sw = levelMap[i][j].getSprite().getWidth();
				float sh = levelMap[i][j].getSprite().getHeight();
				levelMap[i][j].getSprite().setPosition(bx * PIXELS_TO_METERS - (sw / 2), by * PIXELS_TO_METERS - (sh / 2));
				levelMap[i][j].getSprite().draw(batch);
			}
		}

		font.draw(batch, "Restitution: " + pBody.getFixtureList().first().getRestitution(), -Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		batch.end();

		debug.render(gameWorld, matrix);

		Vector2 vel = pBody.getLinearVelocity();
		Vector2 pos = pBody.getPosition();

		if(Gdx.input.isKeyPressed(Keys.LEFT) && vel.x > - MAX_VELOCITY){
			pBody.applyLinearImpulse(-4f, 0, pos.x, pos.y, true);
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT) && vel.x < MAX_VELOCITY){
			pBody.applyLinearImpulse( 4f, 0, pos.x, pos.y, true);
		}
		if(Gdx.input.isKeyJustPressed(Keys.UP)){
			if(!jumping){
				System.out.println("jump");
				pBody.applyLinearImpulse( 0, 18f, pos.x, pos.y, true);
				this.jumping = true;
				System.out.println(jumping + "1");
			}
		}
		
	}

	public void loadMap(File file){
		BufferedReader reader;
		try{
			reader = new BufferedReader(new FileReader(file));
			lvlH = Integer.parseInt(reader.readLine());
			lvlW = Integer.parseInt(reader.readLine());
			System.out.println(lvlH + "\n" + lvlW);
			level = new String[lvlH][lvlW];
			for(int i = 0; i < lvlH; i++){
				String st = reader.readLine();
				for(int j = 0; j < lvlW; j++){
					level[i][j] = st.substring(0, st.indexOf(" "));
					System.out.print(level[i][j]);
					try{
						st = st.substring(st.indexOf(" ") + 1, st.length());
					}catch(Exception e){
						st = null;
					}
				}
			}
			
			levelMap = new Tile[lvlH][lvlW];

			for(int i = 0; i < lvlH; i++){
				for(int j = 0; j < lvlW; j++){
					String[] tileData = new String[4];
					String st = level[i][j];
					for(int k = 0; k < 4; k++){
						tileData[k] = st.substring(k, k+1);
					}

					if(tileData[0].equals("#")){
						Texture tex = new Texture("assets/sprites/tileSprite_" + tileData[1] + ".png");
						Sprite sprite = new Sprite(tex);
						sprite.setPosition(j / PIXELS_TO_METERS, i / PIXELS_TO_METERS); //might need to switch these numbers idk
						Body body;
						BodyDef def = new BodyDef();
						def.type = BodyDef.BodyType.StaticBody;
						def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
						body = gameWorld.createBody(def);

						PolygonShape shape = new PolygonShape();
						shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
						FixtureDef fix = new FixtureDef();
						fix.shape = shape;

						body.createFixture(fix);
						shape.dispose();

						levelMap[i][j] = new Solid(new Point(i, j), sprite, body);
						((Solid)levelMap[i][j]).setHarmful(1 == Integer.parseInt(tileData[2]));
						if(Integer.parseInt(tileData[3]) != 0){
							((Solid)levelMap[i][j]).setColoured(true);
							((Solid)levelMap[i][j]).setColour(Integer.parseInt(tileData[3]));
						}
					}


				}
			}
			
		}catch(IOException e){
			System.out.println("File not found");
			e.printStackTrace();
		}
	}

	public void resize (int width, int height) {
	}

	public void pause () {
	}

	public void resume () {
	}

	public void dispose () {
		img.dispose();
		gameWorld.dispose();
	}

	public void beginContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		System.out.println(a.getBody().getType() + " has hit " + b.getBody().getType());
		if(b.getBody().getUserData() != null && b.getBody().getUserData().equals("THEPLAYER") || a.getBody().getUserData() != null && a.getBody().getUserData().equals("THEPLAYER")) {
			this.jumping = false;
		}
		System.out.println(jumping + "2");
	}
 
	public void endContact(Contact contact) {	
		Fixture a = contact.getFixtureA();	
		Fixture b = contact.getFixtureB();
		System.out.println(a.getBody().getType() + " has stopped hitting " + b.getBody().getType());
		System.out.println(jumping + "3");
	}
 
	public void preSolve(Contact contact, Manifold oldManifold) {		
	}
 
	public void postSolve(Contact contact, ContactImpulse impulse) {		
	}

}