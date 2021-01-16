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
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen extends ScreenAdapter implements ContactListener{

    ColourGame game;
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
	  boolean jumping = false;

	  float torque = 0.0f;
	  boolean drawSprite = true;

	  final float PIXELS_TO_METERS = 100f;
	  final float MAX_VELOCITY = 5f;

    public GameScreen(ColourGame game) {
      this.game = game;
    }

    @Override
    public void show() {
      batch = new SpriteBatch();
      img = new Texture("assets/sprites/player.png");
      player = new Sprite(img);
      player.setSize(64, 64);
  
      player.setPosition(- player.getWidth()/2, - player.getHeight()/2);
  
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
      bodyFix.friction = 1.5f;
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
  
      debug = new Box2DDebugRenderer();
      font = new BitmapFont();
      font.setColor(Color.BLACK);
      camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
      System.out.println(jumping);
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
  
      if(drawSprite){
        batch.draw(player, player.getX(), player.getY(), player.getOriginX(), player.getOriginY(), player.getWidth(), player.getHeight(), player.getScaleX(), player.getScaleY(), player.getRotation());
      }
  
      font.draw(batch, "Restitution: " + pBody.getFixtureList().first().getRestitution(), -Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
      batch.end();
  
      debug.render(gameWorld, matrix);
  
      Vector2 vel = pBody.getLinearVelocity();
      Vector2 pos = pBody.getPosition();
  
      if(Gdx.input.isKeyPressed(Keys.LEFT) && vel.x > - MAX_VELOCITY){
        pBody.applyLinearImpulse(-0.80f, 0, pos.x, pos.y, true);
      }
      if(Gdx.input.isKeyPressed(Keys.RIGHT) && vel.x < MAX_VELOCITY){
        pBody.applyLinearImpulse( 0.80f, 0, pos.x, pos.y, true);
      }
      if(Gdx.input.isKeyJustPressed(Keys.UP)){
        if(!jumping){
          System.out.println("jump");
          pBody.applyLinearImpulse( 0, 7f, pos.x, pos.y, true);
          this.jumping = true;
          System.out.println(jumping + "1");
        }
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