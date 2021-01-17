import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen extends ScreenAdapter implements ContactListener{

    ColourGame game;
    SpriteBatch batch;
	  //Sprite player;
	  Texture img;
	  World gameWorld;
	  //Body pBody;
	  Body screenEdge;
	  Box2DDebugRenderer debug;
	  Matrix4 matrix;
	  OrthographicCamera camera;
	  BitmapFont font;
	  String[][] level;
    Tile[][] levelMap;
    Player player;
	  File levelFile;
	  boolean jumping = false;
	  int lvlH;
    int lvlW;
    int lvlNum;
  
	  float torque = 0.0f;
	  boolean drawSprite = true;
  
	  final float PIXELS_TO_METERS = 64f;
	  final float MAX_VELOCITY = 7f;

    public GameScreen(ColourGame game, int level) {
      this.game = game;
      this.lvlNum = level;
    }

    @Override
    public void show() {
      batch = new SpriteBatch();
 
      gameWorld = new World(new Vector2(0, -15f), true);
      gameWorld.setContactListener(this);
  
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
  
      loadMap(new File("assets/levels/level_" + lvlNum + ".txt"));
      //debug = new Box2DDebugRenderer();
      font = new BitmapFont();
      font.setColor(Color.BLACK);
      camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
      gameWorld.step(1f/60f, 6, 2);
		  camera.position.set(player.getBody().getPosition().x * PIXELS_TO_METERS, player.getBody().getPosition().y * PIXELS_TO_METERS, 0);
		  camera.update();

		  player.getBody().applyTorque(torque, true);
		  player.getSprite().setPosition((player.getBody().getPosition().x * PIXELS_TO_METERS) - player.getSprite().getWidth()/2, (player.getBody().getPosition().y * PIXELS_TO_METERS) - player.getSprite().getHeight()/2);
		  player.getSprite().setRotation((float)Math.toDegrees(player.getBody().getAngle()));

		  Gdx.gl.glClearColor(1, 1, 1, 1);
		  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		  batch.setProjectionMatrix(camera.combined);
		  matrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0);
		  batch.begin();

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
      player.getSprite().draw(batch);

		  font.draw(batch, "Restitution: " + player.getBody().getFixtureList().first().getRestitution(), -Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		  batch.end();

		  //debug.render(gameWorld, matrix);

		  Vector2 vel = player.getBody().getLinearVelocity();
		  Vector2 pos = player.getBody().getPosition();

		  if(Gdx.input.isKeyPressed(Keys.LEFT) && vel.x > - MAX_VELOCITY){
		  	player.getBody().applyLinearImpulse(-4f, 0, pos.x, pos.y, true);
		  }
		  if(Gdx.input.isKeyPressed(Keys.RIGHT) && vel.x < MAX_VELOCITY){
		  	player.getBody().applyLinearImpulse( 4f, 0, pos.x, pos.y, true);
		  }
		  if(Gdx.input.isKeyJustPressed(Keys.UP)){
		  	if(!jumping){
		  		System.out.println("jump");
		  		player.getBody().applyLinearImpulse( 0, 18f, pos.x, pos.y, true);
		  		this.jumping = true;
		  		System.out.println(jumping + "1");
		  	}
		  }
      
    }

    public void updateMap(){
      for(Tile[] tiles: levelMap){
        for(Tile tile: tiles){
          if(tile instanceof Solid && player.getColours().size() > 0){
            for(int i = 0; i < player.getColours().size(); i++){
              if(((Solid)tile).isColoured() && ((Solid)tile).checkColour((int)player.getColours().get(i))){
                tile.getBody().getFixtureList().get(0).setSensor(false);
                tile.setSprite(new Sprite(new Texture("assets/sprites/tileSprite" + ((Solid)tile).getColour() + "_1.png")));
              }
            }
          }else if(tile instanceof Solid && ((Solid)tile).isColoured()){
            tile.getBody().getFixtureList().get(0).setSensor(true);
            tile.setSprite(new Sprite(new Texture("assets/sprites/tileSprite" + ((Solid)tile).getColour() + "_2.png")));
          }
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
              Texture tex = new Texture("assets/sprites/tileSprite" + tileData[3] + "_" + tileData[1] + ".png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, i / PIXELS_TO_METERS);
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
                levelMap[i][j].getBody().getFixtureList().get(0).setSensor(true);
              }
            }
            if(tileData[0].equals("$")){
              Texture tex = new Texture("assets/sprites/checkP.png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, i / PIXELS_TO_METERS);
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.StaticBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
  
              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;

  
              body.createFixture(fix).setSensor(true);
              shape.dispose();

              levelMap[i][j] = new Spawn(new Point(i, j), sprite, body);

              tex = new Texture("assets/sprites/player.png");
              sprite = new Sprite(tex);
              sprite.setSize(64, 64);

              sprite.setPosition(j / PIXELS_TO_METERS, i / PIXELS_TO_METERS);
              Body pBody;
              BodyDef bodyDef = new BodyDef();
              bodyDef.type = BodyDef.BodyType.DynamicBody;

              bodyDef.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              pBody = gameWorld.createBody(bodyDef);
              pBody.setFixedRotation(true);
              pBody.setUserData("THEPLAYER");

              PolygonShape pShape = new PolygonShape();
              pShape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
  
              FixtureDef bodyFix = new FixtureDef();
              bodyFix.shape = shape;
              bodyFix.density = 2f;
              bodyFix.friction = 10f;
              bodyFix.restitution = 0.0f;
              pBody.createFixture(bodyFix);

              shape.dispose();

              player = new Player(((Spawn)levelMap[i][j]), ((Spawn)levelMap[i][j]).getPoint(), sprite, pBody);

            }
            if(tileData[0].equals("~")){
              Texture tex = new Texture("assets/sprites/airSprite_" + tileData[1] + ".png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, i / PIXELS_TO_METERS);
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.StaticBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
  
              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;

  
              body.createFixture(fix).setSensor(true);
              shape.dispose();

              levelMap[i][j] = new Air(new Point(i, j), sprite, body);
            }
            if(tileData[0].equals("G")){
              Texture tex = new Texture("assets/sprites/giverSprite_" + tileData[1] + ".png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, i / PIXELS_TO_METERS);
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.StaticBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
              body.setUserData(tileData[3] + "giver");

              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;

  
              body.createFixture(fix).setSensor(true);
              shape.dispose();

              levelMap[i][j] = new ColourGiver(new Point(i, j), sprite, body, Integer.parseInt(tileData[3]));
            }
            if(tileData[0].equals("R")){
              Texture tex = new Texture("assets/sprites/removerSprite.png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, i / PIXELS_TO_METERS);
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.StaticBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
              body.setUserData("remover");

              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;

  
              body.createFixture(fix).setSensor(true);
              shape.dispose();

              levelMap[i][j] = new ColourRemover(new Point(i, j), sprite, body);
            }
          }
        }
        
      }catch(IOException e){
        System.out.println("File not found");
        e.printStackTrace();
      }
    }

    @Override
    public void hide() {
      Gdx.input.setInputProcessor(null);
      img.dispose();
		  gameWorld.dispose();
    }

    public void beginContact(Contact contact) {
      Fixture a = contact.getFixtureA();
      Fixture b = contact.getFixtureB();
      if((b.getBody().getUserData() != null && b.getBody().getUserData().equals("THEPLAYER") ||
          a.getBody().getUserData() != null && a.getBody().getUserData().equals("THEPLAYER")) && 
          (!b.isSensor() && !a.isSensor())) 
      {
        System.out.println(a.getBody().getType() + " has hit " + b.getBody().getType());
        this.jumping = false;
      }
      if((b.getBody().getUserData() != null && b.getBody().getUserData().equals("THEPLAYER") ||
            a.getBody().getUserData() != null && a.getBody().getUserData().equals("THEPLAYER")) &&
          (b.getBody().getUserData() != null && ((String)b.getBody().getUserData()).substring(1, 6).equals("giver") || 
            a.getBody().getUserData() != null && ((String)a.getBody().getUserData()).substring(1, 6).equals("giver")))
      {
        if(((String)b.getBody().getUserData()).substring(1, 6).equals("giver")){
          player.addColour(Integer.parseInt(((String)b.getBody().getUserData()).substring(0, 1)));
        }else{
          player.addColour(Integer.parseInt(((String)a.getBody().getUserData()).substring(0, 1)));
        }
        updateMap();
      }
      if((b.getBody().getUserData() != null && b.getBody().getUserData().equals("THEPLAYER") ||
            a.getBody().getUserData() != null && a.getBody().getUserData().equals("THEPLAYER")) &&
          (b.getBody().getUserData() != null && b.getBody().getUserData().equals("remover") || 
            a.getBody().getUserData() != null && a.getBody().getUserData().equals("remover")))
      {
        player.clearColour();
        updateMap();
      }
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