import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.headless.mock.audio.MockAudio;
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
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import org.lwjgl.Sys;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

import androidx.core.view.accessibility.AccessibilityViewCommand.SetSelectionArguments;

public class GameScreen extends ScreenAdapter implements ContactListener{

    ColourGame game;
    SpriteBatch batch;
	  Texture img;
	  World gameWorld;
	  Body screenEdge;
	  Box2DDebugRenderer debug;
	  Matrix4 matrix;
	  OrthographicCamera camera;
	  BitmapFont font;
	  String[][] level;
    Tile[][] levelMap;
    Player player;
    File levelFile;
    Sprite back = new Sprite(new Texture("assets/sprites/back.png"));
    boolean jumping = false;
    boolean dead = false;
    boolean nextLevel = false;
	  int lvlH;
    int lvlW;
    //int lvlNum;
    String levelName;
    
    final short PLAYER = 0x0002;
    final short TILE = 0x0004;
    final short BOX = 0x0008;
    final short BULLET = 0x0010;
    final short ENEMY = 0x0020;
    final short SENSOR = 0x0040;

	  float torque = 0.0f;
	  boolean drawSprite = true;
  
	  final float PIXELS_TO_METERS = 64f;
	  final float MAX_VELOCITY = 5f;

    public GameScreen(ColourGame game, String levelName) {
      this.game = game;
      this.levelName = levelName;
    }

    @Override
    public void show() {
      back.setPosition(16, Gdx.graphics.getHeight() - 80);
      batch = new SpriteBatch();
 
      gameWorld = new World(new Vector2(0, -15f), true);
      gameWorld.setContactListener(this);
  
      //loadMap(new File("assets/levels/level_" + lvlNum + ".txt"));
      loadMap(new File("assets/levels/" + levelName));
      debug = new Box2DDebugRenderer();
      font = new BitmapFont();
      font.setColor(Color.BLACK);
      camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) { 
      updateBroken(Gdx.graphics.getDeltaTime());
      updateMoving();
      if(dead){
        player.respawn();
        dead = false;
      }
      if(nextLevel){
        gameWorld.destroyBody(player.getBody());
        player.setBody(null);
        for(int i = 0; i < levelMap.length; i++){
          for(int j = 0; j < levelMap[0].length; j++){
            if(levelMap[i][j] != null){
              gameWorld.destroyBody(levelMap[i][j].getBody());
              levelMap[i][j].setBody(null);
            }
          }
        }
        gameWorld.dispose();
        gameWorld = null;
        game.setScreen(new LevelScreen(game));
        nextLevel = false;
        return;
      }
      gameWorld.step(1f/60f, 6, 2);
      System.out.println("hi");
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

		  for(int i = 0; i < levelMap.length; i++){
		  	for(int j = 0; j < levelMap[0].length; j++){
          if(levelMap[i][j] instanceof CrushingBlock){
            ((CrushingBlock)levelMap[i][j]).update();
            if(((CrushingBlock)levelMap[i][j]).isMoving()){
              ((CrushingBlock)levelMap[i][j]).move();
            }
          }else if(levelMap[i][j] instanceof MovingBlock){
            ((MovingBlock)levelMap[i][j]).move(((MovingBlock)levelMap[i][j]).getPoints());
          }else if(levelMap[i][j] instanceof Floater){
            ((Floater)levelMap[i][j]).move(((Floater)levelMap[i][j]).getPoints());
          }else if(levelMap[i][j] instanceof Sticker){
            ((Sticker)levelMap[i][j]).move(levelMap);
          }else if(levelMap[i][j] instanceof Walker){
            ((Walker)levelMap[i][j]).move();
          }
          if(levelMap[i][j] != null){
		  		  float bx = levelMap[i][j].getBody().getPosition().x;
		  		  float by = levelMap[i][j].getBody().getPosition().y;
		  		  float sw = levelMap[i][j].getSprite().getWidth();
		  		  float sh = levelMap[i][j].getSprite().getHeight();
		  		  levelMap[i][j].getSprite().setPosition(bx * PIXELS_TO_METERS - (sw / 2), by * PIXELS_TO_METERS - (sh / 2));
            levelMap[i][j].getSprite().draw(batch);
          }
		  	}
      }
      player.getSprite().draw(batch);

		  font.draw(batch, "Restitution: " + player.getBody().getFixtureList().first().getRestitution(), -Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		  batch.end();

      debug.render(gameWorld, matrix);
		  Vector2 vel = player.getBody().getLinearVelocity();
		  Vector2 pos = player.getBody().getPosition();

		  if(Gdx.input.isKeyPressed(Keys.LEFT) && vel.x > - MAX_VELOCITY){
        player.getBody().applyLinearImpulse(-1.5f, 0, pos.x, pos.y, true);
		  }
		  if(Gdx.input.isKeyPressed(Keys.RIGHT) && vel.x < MAX_VELOCITY){
        player.getBody().applyLinearImpulse( 1.5f, 0, pos.x, pos.y, true);
		  }
		  if(Gdx.input.isKeyJustPressed(Keys.UP)){
		  	if(!jumping){
		  		player.getBody().applyLinearImpulse( 0, 18f, pos.x, pos.y, true);
		  		this.jumping = true;
		  	}
		  }
      
    }

    public void updateMoving(){
      for(Tile[] tiles: levelMap){
        for(Tile tile: tiles){
          if(tile instanceof Moving && player.getColours().size() > 0){
            Filter filter = tile.getBody().getFixtureList().get(0).getFilterData();
            for(int i = 0; i < player.getColours().size(); i++){
              if(((Solid)tile).isColoured() && ((Solid)tile).checkColour((int)player.getColours().get(i))){
                if(tile instanceof CrushingBlock){
                  Body body = tile.getBody();
                  body.setType(BodyType.KinematicBody);
                  tile.setBody(body);
                  filter.maskBits = -1;
                  tile.getBody().getFixtureList().get(0).setFilterData(filter);
                  tile.setSprite(new Sprite(new Texture("assets/sprites/crushSprite" + ((Solid)tile).getColour() + "_1.png")));
                }else if(tile instanceof MovingBlock){
                  Body body = tile.getBody();
                  body.setType(BodyType.KinematicBody);
                  tile.setBody(body);
                  filter.maskBits = -1;
                  tile.getBody().getFixtureList().get(0).setFilterData(filter);
                  tile.setSprite(new Sprite(new Texture("assets/sprites/moveSprite" + ((Solid)tile).getColour() + "_1.png")));
                }else if(tile instanceof Floater){
                  Body body = tile.getBody();
                  body.setType(BodyType.KinematicBody);
                  tile.setBody(body);
                  filter.maskBits = -1;
                  tile.getBody().getFixtureList().get(0).setFilterData(filter);
                  tile.setSprite(new Sprite(new Texture("assets/sprites/floatSprite" + ((Solid)tile).getColour() + "_1.png")));
                }else if(tile instanceof Walker){
                  Body body = tile.getBody();
                  body.setType(BodyType.KinematicBody);
                  tile.setBody(body);
                  filter.maskBits = -1;
                  tile.getBody().getFixtureList().get(0).setFilterData(filter);
                  tile.setSprite(new Sprite(new Texture("assets/sprites/walkSprite" + ((Solid)tile).getColour() + "_1.png")));
                }else if(tile instanceof Sticker){
                  Body body = tile.getBody();
                  body.setType(BodyType.KinematicBody);
                  tile.setBody(body);
                  filter.maskBits = -1;
                  tile.getBody().getFixtureList().get(0).setFilterData(filter);
                  tile.setSprite(new Sprite(new Texture("assets/sprites/stickSprite" + ((Solid)tile).getColour() + "_1.png")));
                }
              }
            }
          }else if(tile instanceof Moving && ((Solid)tile).isColoured()){
            Filter filter = tile.getBody().getFixtureList().get(0).getFilterData();
            if(tile instanceof CrushingBlock){
              Body body = tile.getBody();
              body.setType(BodyType.StaticBody);
              tile.setBody(body);
              filter.maskBits = ~PLAYER;
              tile.getBody().getFixtureList().get(0).setFilterData(filter);
              tile.setSprite(new Sprite(new Texture("assets/sprites/crushSprite" + ((Solid)tile).getColour() + "_2.png")));
            }else if(tile instanceof MovingBlock){
              Body body = tile.getBody();
              body.setType(BodyType.StaticBody);
              tile.setBody(body);
              filter.maskBits = ~PLAYER;
              tile.getBody().getFixtureList().get(0).setFilterData(filter);
              tile.setSprite(new Sprite(new Texture("assets/sprites/moveSprite" + ((Solid)tile).getColour() + "_2.png")));
            }else if(tile instanceof Floater){
              Body body = tile.getBody();
              body.setType(BodyType.StaticBody);
              tile.setBody(body);
              filter.maskBits = ~PLAYER;
              tile.getBody().getFixtureList().get(0).setFilterData(filter);
              tile.setSprite(new Sprite(new Texture("assets/sprites/floatSprite" + ((Solid)tile).getColour() + "_2.png")));
            }else if(tile instanceof Walker){
              Body body = tile.getBody();
              body.setType(BodyType.StaticBody);
              tile.setBody(body);
              filter.maskBits = ~PLAYER;
              tile.getBody().getFixtureList().get(0).setFilterData(filter);
              tile.setSprite(new Sprite(new Texture("assets/sprites/walkSprite" + ((Solid)tile).getColour() + "_2.png")));
            }else if(tile instanceof Sticker){
              Body body = tile.getBody();
              body.setType(BodyType.StaticBody);
              tile.setBody(body);
              filter.maskBits = ~PLAYER;
              tile.getBody().getFixtureList().get(0).setFilterData(filter);
              tile.setSprite(new Sprite(new Texture("assets/sprites/stickSprite" + ((Solid)tile).getColour() + "_2.png")));
            }
          }
          if(tile instanceof CrushingBlock && ((CrushingBlock)tile).isMoving()){
            if(((CrushingBlock)tile).isAggro()){
              Filter filter = tile.getBody().getFixtureList().get(0).getFilterData();
              filter.categoryBits = ENEMY;
              tile.getBody().getFixtureList().get(0).setFilterData(filter);
            }else{
              Filter filter = tile.getBody().getFixtureList().get(0).getFilterData();
              filter.categoryBits = TILE;
              tile.getBody().getFixtureList().get(0).setFilterData(filter);
            }
          }
        }
      }
    }

    public void updateBroken(float time){
      for(Tile[] tiles: levelMap){
        for(Tile tile: tiles){
          if(tile instanceof BrokenBlock && ((BrokenBlock)tile).getActive()){
            Filter filter = tile.getBody().getFixtureList().get(0).getFilterData();
            if(((BrokenBlock)tile).getElapsed() >= ((BrokenBlock)tile).getTimer()){
              ((BrokenBlock)tile).setBroken(true);
              ((BrokenBlock)tile).setActive(false);
              ((BrokenBlock)tile).setElapsed(0);
              filter.maskBits = ~PLAYER;
              tile.getBody().getFixtureList().get(0).setFilterData(filter);
              ((BrokenBlock)tile).setSprite(new Sprite(new Texture("assets/sprites/breakSprite" + ((BrokenBlock)tile).getColour() + "_3.png")));
            }
            ((BrokenBlock)tile).setElapsed(((BrokenBlock)tile).getElapsed() + time);
          }else if(tile instanceof BrokenBlock && ((BrokenBlock)tile).getBroken()){
            Filter filter = tile.getBody().getFixtureList().get(0).getFilterData();
            if(((BrokenBlock)tile).getElapsed() >= ((BrokenBlock)tile).getTimer()){
              ((BrokenBlock)tile).setBroken(false);
              ((BrokenBlock)tile).setElapsed(0);
              filter.maskBits = -1;
              tile.getBody().getFixtureList().get(0).setFilterData(filter);
              ((BrokenBlock)tile).setSprite(new Sprite(new Texture("assets/sprites/breakSprite" + ((BrokenBlock)tile).getColour() + "_1.png")));
            }
            ((BrokenBlock)tile).setElapsed(((BrokenBlock)tile).getElapsed() + time);
          }
        }
      }
    }

    public void updateMap(){
      for(Tile[] tiles: levelMap){
        for(Tile tile: tiles){
          if(tile instanceof Solid && player.getColours().size() > 0){
            Filter filter = tile.getBody().getFixtureList().get(0).getFilterData();
            for(int i = 0; i < player.getColours().size(); i++){
              if(((Solid)tile).isColoured() && ((Solid)tile).checkColour((int)player.getColours().get(i)) && (!(tile instanceof Moving) || tile instanceof PushableBlock)){
                if(tile instanceof Spikes){
                  filter.maskBits = -1;
                  tile.getBody().getFixtureList().get(0).setFilterData(filter);
                  tile.setSprite(new Sprite(new Texture("assets/sprites/SpikeSprite" + ((Solid)tile).getColour() + "_1.png")));
                }else if(tile instanceof PushableBlock){
                  filter.maskBits = -1;
                  tile.getBody().getFixtureList().get(0).setFilterData(filter);
                  tile.setSprite(new Sprite(new Texture("assets/sprites/boxSprite" + ((Solid)tile).getColour() + "_1.png")));
                }else if(tile instanceof BrokenBlock){
                  filter.maskBits = -1;
                  tile.getBody().getFixtureList().get(0).setFilterData(filter);
                  tile.setSprite(new Sprite(new Texture("assets/sprites/breakSprite" + ((Solid)tile).getColour() + "_1.png")));
                }else if(tile instanceof SlipperyBlock){
                  filter.maskBits = -1;
                  tile.getBody().getFixtureList().get(0).setFilterData(filter);
                  tile.setSprite(new Sprite(new Texture("assets/sprites/iceSprite" + ((Solid)tile).getColour() + "_1.png")));
                }else{
                  filter.maskBits = -1;
                  tile.getBody().getFixtureList().get(0).setFilterData(filter);
                  tile.setSprite(new Sprite(new Texture("assets/sprites/tileSprite" + ((Solid)tile).getColour() + "_1.png")));
                }
              }
            }
          }else if(tile instanceof Solid && ((Solid)tile).isColoured() && (!(tile instanceof Moving) || tile instanceof PushableBlock)){
            Filter filter = tile.getBody().getFixtureList().get(0).getFilterData();
            if(tile instanceof Spikes){
              filter.maskBits = ~PLAYER;
              tile.getBody().getFixtureList().get(0).setFilterData(filter);
              tile.setSprite(new Sprite(new Texture("assets/sprites/SpikeSprite" + ((Solid)tile).getColour() + "_2.png")));
            }else if(tile instanceof PushableBlock){
              filter.maskBits = ~PLAYER;
              tile.getBody().getFixtureList().get(0).setFilterData(filter);
              tile.setSprite(new Sprite(new Texture("assets/sprites/boxSprite" + ((Solid)tile).getColour() + "_2.png")));
            }else if(tile instanceof BrokenBlock){
              ((BrokenBlock)tile).setActive(false);
              ((BrokenBlock)tile).setBroken(false);
              ((BrokenBlock)tile).setElapsed(0);
              filter.maskBits = ~PLAYER;
              tile.getBody().getFixtureList().get(0).setFilterData(filter);
              tile.setSprite(new Sprite(new Texture("assets/sprites/breakSprite" + ((Solid)tile).getColour() + "_2.png")));
            }else if(tile instanceof SlipperyBlock){
              filter.maskBits = ~PLAYER;
              tile.getBody().getFixtureList().get(0).setFilterData(filter);
              tile.setSprite(new Sprite(new Texture("assets/sprites/iceSprite" + ((Solid)tile).getColour() + "_2.png")));
            }else{
              filter.maskBits = ~PLAYER;
              tile.getBody().getFixtureList().get(0).setFilterData(filter);
              tile.setSprite(new Sprite(new Texture("assets/sprites/tileSprite" + ((Solid)tile).getColour() + "_2.png")));
            }
          }else if(tile instanceof Checkpoint){
            if(tile instanceof Spawn){
              if(!(((Spawn)tile).equals(player.getSpawn()))){
                tile.setSprite(new Sprite(new Texture("assets/sprites/spawnSprite_1.png")));
              }
            }else{
              if(((Checkpoint)tile).equals(player.getSpawn())){
                tile.setSprite(new Sprite(new Texture("assets/sprites/checkSprite_2.png")));
              }else{
                tile.setSprite(new Sprite(new Texture("assets/sprites/checkSprite_1.png")));
              }
            }
          }else if(tile instanceof Goal){
            Filter filter = tile.getBody().getFixtureList().get(0).getFilterData();
            if(((Goal)tile).isColoured() && player.getColours().size() > 0){
              for(int i = 0; i < player.getColours().size(); i++){
                if(((Goal)tile).checkColour(player.getColours().get(i))){
                  filter.maskBits = -1;
                  tile.getBody().getFixtureList().get(0).setFilterData(filter);
                  tile.setSprite(new Sprite(new Texture("assets/sprites/doorSprite" + ((Goal)tile).getColour() + "_1.png")));
                }
              }
            }else if(((Goal)tile).isColoured()){
              filter.maskBits = ~PLAYER;
              tile.getBody().getFixtureList().get(0).setFilterData(filter);
              tile.setSprite(new Sprite(new Texture("assets/sprites/doorSprite" + ((Goal)tile).getColour() + "_2.png")));
            }
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
          System.out.println("");
          for(int j = 0; j < lvlW; j++){
            String[] tileData = new String[4];
            String st = level[i][j];
            for(int k = 0; k < 4; k++){
              tileData[k] = st.substring(k, k+1);
            }
            System.out.print(tileData[0]);
  
            if(tileData[0].equals("#")){
              Texture tex = new Texture("assets/sprites/tileSprite" + tileData[3] + "_" + tileData[1] + ".png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.StaticBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
  
              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;
              fix.filter.categoryBits = TILE;
  
              body.createFixture(fix);
              shape.dispose();
  
              levelMap[i][j] = new Solid(new Point(i, j), sprite, body);
              ((Solid)levelMap[i][j]).setHarmful(1 == Integer.parseInt(tileData[2]));
              if(Integer.parseInt(tileData[3]) != 0){
                ((Solid)levelMap[i][j]).setColoured(true);
                ((Solid)levelMap[i][j]).setColour(Integer.parseInt(tileData[3]));
                fix.filter.maskBits = ~PLAYER;
                levelMap[i][j].getBody().getFixtureList().get(0).setFilterData(fix.filter);
              }
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);
            }
            if(tileData[0].equals("$")){
              Texture tex = new Texture("assets/sprites/spawnSprite_2.png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.StaticBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
  
              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;
              fix.filter.categoryBits = SENSOR;

  
              body.createFixture(fix).setSensor(true);
              shape.dispose();

              levelMap[i][j] = new Spawn(new Point(i, j), sprite, body);
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);

              tex = new Texture("assets/sprites/player.png");
              sprite = new Sprite(tex);
              sprite.setSize(64, 64);

              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body pBody;
              BodyDef bodyDef = new BodyDef();
              bodyDef.type = BodyDef.BodyType.DynamicBody;

              bodyDef.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              pBody = gameWorld.createBody(bodyDef);
              pBody.setFixedRotation(true);

              PolygonShape pShape = new PolygonShape();
              pShape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS / 2, sprite.getHeight() / 2.2f / PIXELS_TO_METERS);
  
              FixtureDef bodyFix = new FixtureDef();
              bodyFix.shape = pShape;
              bodyFix.density = 4f;
              bodyFix.friction = 10f;
              bodyFix.restitution = 0.0f;
              bodyFix.filter.categoryBits = PLAYER;
              pBody.createFixture(bodyFix);
              System.out.println(pBody.getPosition().x - 2048);
              System.out.println(j);

              shape.dispose();

              player = new Player(((Spawn)levelMap[i][j]), ((Spawn)levelMap[i][j]).getPoint(), sprite, pBody);
              player.getBody().setUserData(player);

            }
            //if(tileData[0].equals("~")){
            //  Texture tex = new Texture("assets/sprites/airSprite_" + tileData[1] + ".png");
            //  Sprite sprite = new Sprite(tex);
            //  sprite.setPosition(j / PIXELS_TO_METERS, i / PIXELS_TO_METERS);
            //  Body body;
            //  BodyDef def = new BodyDef();
            //  def.type = BodyDef.BodyType.StaticBody;
            //  def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
            //  body = gameWorld.createBody(def);
  //
            //  PolygonShape shape = new PolygonShape();
            //  shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
            //  FixtureDef fix = new FixtureDef();
            //  fix.shape = shape;
//
  //
            //  body.createFixture(fix).setSensor(true);
            //  shape.dispose();
//
            //  levelMap[i][j] = new Air(new Point(i, j), sprite, body);
            //  levelMap[i][j].getBody().setUserData(levelMap[i][j]);
            //}
            if(tileData[0].equals("G")){
              Texture tex = new Texture("assets/sprites/giverSprite_" + tileData[3] + ".png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.StaticBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);

              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;
              fix.filter.categoryBits = SENSOR;

  
              body.createFixture(fix).setSensor(true);
              shape.dispose();

              levelMap[i][j] = new ColourGiver(new Point(i, j), sprite, body, Integer.parseInt(tileData[1]));
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);
            }
            if(tileData[0].equals("R")){
              Texture tex = new Texture("assets/sprites/removerSprite.png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
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
              fix.filter.categoryBits = SENSOR;

  
              body.createFixture(fix).setSensor(true);
              shape.dispose();

              levelMap[i][j] = new ColourRemover(new Point(i, j), sprite, body);
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);
            }
            if(tileData[0].equals("^")){
              Texture tex = new Texture("assets/sprites/spikeSprite" + tileData[3] + "_" + tileData[1] + ".png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.StaticBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
  
              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 4 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;
              fix.filter.categoryBits = ENEMY;
  
              body.createFixture(fix);
              shape.dispose();
  
              levelMap[i][j] = new Spikes(new Point(i, j), sprite, body);
              ((Spikes)levelMap[i][j]).setHarmful(1 == Integer.parseInt(tileData[2]));
              if(Integer.parseInt(tileData[3]) != 0){
                ((Spikes)levelMap[i][j]).setColoured(true);
                ((Spikes)levelMap[i][j]).setColour(Integer.parseInt(tileData[3]));
                fix.filter.maskBits = ~PLAYER;
                levelMap[i][j].getBody().getFixtureList().get(0).setFilterData(fix.filter);
              }
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);
            }
            if(tileData[0].equals("*")){
              Texture tex = new Texture("assets/sprites/checkSprite_1.png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.StaticBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;
              fix.filter.categoryBits = SENSOR;

  
              body.createFixture(fix).setSensor(true);
              shape.dispose();

              levelMap[i][j] = new Checkpoint(new Point(i, j), sprite, body);
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);
            }
            if(tileData[0].equals("@")){
              Texture tex = new Texture("assets/sprites/boxSprite" + tileData[3] + "_" + tileData[1] + ".png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body boxBody;
              BodyDef boxDef = new BodyDef();
              boxDef.type = BodyDef.BodyType.DynamicBody;
              boxDef.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              boxBody = gameWorld.createBody(boxDef);
              boxBody.setFixedRotation(true);
  
              PolygonShape bShape = new PolygonShape();
              bShape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef bFix = new FixtureDef();
              bFix.shape = bShape;
              bFix.density = 4f;
              bFix.filter.categoryBits = BOX;
              
  
              boxBody.createFixture(bFix);
              bShape.dispose();
  
              levelMap[i][j] = new PushableBlock(new Point(i, j), sprite, boxBody, Integer.parseInt(tileData[2]));
              if(Integer.parseInt(tileData[3]) != 0){
                ((PushableBlock)levelMap[i][j]).setColoured(true);
                ((PushableBlock)levelMap[i][j]).setColour(Integer.parseInt(tileData[3]));
                bFix.filter.maskBits = ~PLAYER;
                levelMap[i][j].getBody().getFixtureList().get(0).setFilterData(bFix.filter);
              }
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);
            }
            if(tileData[0].equals("X")){
              Texture tex = new Texture("assets/sprites/breakSprite" + tileData[3] + "_" + tileData[1] + ".png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.StaticBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
  
              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;
              fix.filter.categoryBits = TILE;
  
              body.createFixture(fix);
              shape.dispose();
  
              levelMap[i][j] = new BrokenBlock(new Point(i, j), sprite, body, Integer.parseInt(tileData[2]));
              if(Integer.parseInt(tileData[3]) != 0){
                ((BrokenBlock)levelMap[i][j]).setColoured(true);
                ((BrokenBlock)levelMap[i][j]).setColour(Integer.parseInt(tileData[3]));
                fix.filter.maskBits = ~PLAYER;
                levelMap[i][j].getBody().getFixtureList().get(0).setFilterData(fix.filter);
              }
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);
            }
            if(tileData[0].equals("V")){
              Texture tex = new Texture("assets/sprites/crushSprite" + tileData[3] + "_" + tileData[1] + ".png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.KinematicBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
  
              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;
              fix.filter.categoryBits = TILE;
  
              body.createFixture(fix);
              shape.dispose();
  
              levelMap[i][j] = new CrushingBlock(new Point(i, j), sprite, body, Integer.parseInt(tileData[2]), gameWorld);
              if(Integer.parseInt(tileData[3]) != 0){
                ((CrushingBlock)levelMap[i][j]).setColoured(true);
                ((CrushingBlock)levelMap[i][j]).setColour(Integer.parseInt(tileData[3]));
                fix.filter.maskBits = ~PLAYER;
                levelMap[i][j].getBody().getFixtureList().get(0).setFilterData(fix.filter);
              }
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);
            }
            if(tileData[0].equals("-")){
              Texture tex;
              if(Integer.parseInt(tileData[3]) != 0){
                tex = new Texture("assets/sprites/moveSprite" + tileData[3] + "_2.png");
              }else{
                tex = new Texture("assets/sprites/moveSprite0_1.png");
              }
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.KinematicBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
  
              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;
              fix.filter.categoryBits = TILE;
  
              body.createFixture(fix);
              shape.dispose();
  
              int p1 = Integer.parseInt(tileData[1]);
              int p2 = Integer.parseInt(tileData[2]);

              levelMap[i][j] = new MovingBlock(new Point(i, j), sprite, body, new int[]{p1, p2});
              if(Integer.parseInt(tileData[3]) != 0){
                ((MovingBlock)levelMap[i][j]).setColoured(true);
                ((MovingBlock)levelMap[i][j]).setColour(Integer.parseInt(tileData[3]));
                fix.filter.maskBits = ~PLAYER;
                levelMap[i][j].getBody().getFixtureList().get(0).setFilterData(fix.filter);
              }
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);
              ((MovingBlock)levelMap[i][j]).setAxis(true);
              ((MovingBlock)levelMap[i][j]).setDirection(true);
              ((MovingBlock)levelMap[i][j]).setInit();
            }
            if(tileData[0].equals("|")){
              Texture tex;
              if(Integer.parseInt(tileData[3]) != 0){
                tex = new Texture("assets/sprites/moveSprite" + tileData[3] + "_2.png");
              }else{
                tex = new Texture("assets/sprites/moveSprite0_1.png");
              }
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.KinematicBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
  
              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;
              fix.filter.categoryBits = TILE;
  
              body.createFixture(fix);
              shape.dispose();
  
              int p1 = Integer.parseInt(tileData[1]);
              int p2 = Integer.parseInt(tileData[2]);

              levelMap[i][j] = new MovingBlock(new Point(i, j), sprite, body, new int[]{p1, p2});
              if(Integer.parseInt(tileData[3]) != 0){
                ((MovingBlock)levelMap[i][j]).setColoured(true);
                ((MovingBlock)levelMap[i][j]).setColour(Integer.parseInt(tileData[3]));
                fix.filter.maskBits = ~PLAYER;
                levelMap[i][j].getBody().getFixtureList().get(0).setFilterData(fix.filter);
              }
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);
              ((MovingBlock)levelMap[i][j]).setAxis(false);
              ((MovingBlock)levelMap[i][j]).setDirection(true);
              ((MovingBlock)levelMap[i][j]).setInit();
            }
            if(tileData[0].equals("&")){
              Texture tex;
              if(Integer.parseInt(tileData[3]) != 0){
                tex = new Texture("assets/sprites/stickSprite" + tileData[3] + "_2.png");
              }else{
                tex = new Texture("assets/sprites/stickSprite0_1.png");
              }
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.KinematicBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
  
              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;
              fix.filter.categoryBits = ENEMY;
  
              body.createFixture(fix);
              shape.dispose();
            

              levelMap[i][j] = new Sticker(new Point(i, j), sprite, body, Integer.parseInt(tileData[1]), Integer.parseInt(tileData[2]));
              
              if(Integer.parseInt(tileData[3]) != 0){
                ((Sticker)levelMap[i][j]).setColoured(true);
                ((Sticker)levelMap[i][j]).setColour(Integer.parseInt(tileData[3]));
                fix.filter.maskBits = ~PLAYER;
                levelMap[i][j].getBody().getFixtureList().get(0).setFilterData(fix.filter);
              }
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);
              ((Sticker)levelMap[i][j]).setClockwise(true);
            }
            if(tileData[0].equals("f")){
              Texture tex;
              if(Integer.parseInt(tileData[3]) != 0){
                tex = new Texture("assets/sprites/floatSprite" + tileData[3] + "_2.png");
              }else{
                tex = new Texture("assets/sprites/floatSprite0_1.png");
              }
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.KinematicBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
  
              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;
              fix.filter.categoryBits = ENEMY;
  
              body.createFixture(fix);
              shape.dispose();
  
              int p1 = Integer.parseInt(tileData[1]);
              int p2 = Integer.parseInt(tileData[2]);

              levelMap[i][j] = new Floater(new Point(i, j), sprite, body, new int[]{p1, p2});
              if(Integer.parseInt(tileData[3]) != 0){
                ((Floater)levelMap[i][j]).setColoured(true);
                ((Floater)levelMap[i][j]).setColour(Integer.parseInt(tileData[3]));
                fix.filter.maskBits = ~PLAYER;
                levelMap[i][j].getBody().getFixtureList().get(0).setFilterData(fix.filter);
              }
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);
              ((Floater)levelMap[i][j]).setAxis(true);
              ((Floater)levelMap[i][j]).setDirection(true);
              ((Floater)levelMap[i][j]).setInit();
            }
            if(tileData[0].equals("=")){
              Texture tex = new Texture("assets/sprites/iceSprite" + tileData[3] + "_" + tileData[1] + ".png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.StaticBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
  
              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;
              fix.friction = 0.01f;
              fix.filter.categoryBits = TILE;
  
              body.createFixture(fix);
              shape.dispose();
  
              levelMap[i][j] = new SlipperyBlock(new Point(i, j), sprite, body);
              ((SlipperyBlock)levelMap[i][j]).setHarmful(1 == Integer.parseInt(tileData[2]));
              if(Integer.parseInt(tileData[3]) != 0){
                ((SlipperyBlock)levelMap[i][j]).setColoured(true);
                ((SlipperyBlock)levelMap[i][j]).setColour(Integer.parseInt(tileData[3]));
                fix.filter.maskBits = ~PLAYER;
                levelMap[i][j].getBody().getFixtureList().get(0).setFilterData(fix.filter);
              }
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);
            }
            if(tileData[0].equals("w")){
              Texture tex;
              if(Integer.parseInt(tileData[3]) != 0){
                tex = new Texture("assets/sprites/walkSprite" + tileData[3] + "_2.png");
              }else{
                tex = new Texture("assets/sprites/walkSprite0_1.png");
              }
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.KinematicBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);
  
              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;
              fix.filter.categoryBits = ENEMY;
  
              body.createFixture(fix);
              shape.dispose();

              int p1 = Integer.parseInt(tileData[1]);
              int p2 = Integer.parseInt(tileData[2]);

              levelMap[i][j] = new Walker(new Point(i, j), sprite, body, new int[]{p1, p2});
              if(Integer.parseInt(tileData[3]) != 0){
                ((Walker)levelMap[i][j]).setColoured(true);
                ((Walker)levelMap[i][j]).setColour(Integer.parseInt(tileData[3]));
                fix.filter.maskBits = ~PLAYER;
                levelMap[i][j].getBody().getFixtureList().get(0).setFilterData(fix.filter);
              }
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);
              ((Walker)levelMap[i][j]).setInit();
            }
            if(tileData[0].equals("D")){
              Texture tex = new Texture("assets/sprites/doorSprite" + tileData[3] + "_" + tileData[1] + ".png");
              Sprite sprite = new Sprite(tex);
              sprite.setPosition(j / PIXELS_TO_METERS, Gdx.graphics.getHeight() - (i / PIXELS_TO_METERS));
              Body body;
              BodyDef def = new BodyDef();
              def.type = BodyDef.BodyType.StaticBody;
              def.position.set((sprite.getX() + sprite.getWidth()/2)*PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight()/2)*PIXELS_TO_METERS);
              body = gameWorld.createBody(def);

              PolygonShape shape = new PolygonShape();
              shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, sprite.getHeight() / 2 / PIXELS_TO_METERS);
              FixtureDef fix = new FixtureDef();
              fix.shape = shape;
              fix.filter.categoryBits = SENSOR;
  
              body.createFixture(fix).setSensor(true);
              shape.dispose();

              levelMap[i][j] = new Goal(new Point(i, j), sprite, body, Integer.parseInt(tileData[3]));
              if(Integer.parseInt(tileData[3]) != 0){
                ((Goal)levelMap[i][j]).setColoured(true);
                ((Goal)levelMap[i][j]).setColour(Integer.parseInt(tileData[3]));
                fix.filter.maskBits = ~PLAYER;
                levelMap[i][j].getBody().getFixtureList().get(0).setFilterData(fix.filter);
              }
              levelMap[i][j].getBody().setUserData(levelMap[i][j]);
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
      super.hide();
      Gdx.input.setInputProcessor(null);
      //img.dispose();
		  //gameWorld.dispose();
    }

    public void beginContact(Contact contact) {
      Fixture a = contact.getFixtureA();
      Fixture b = contact.getFixtureB();
      Object obj1 = a.getBody().getUserData();
      Object obj2 = b.getBody().getUserData();
      if(obj2 instanceof Player || obj1 instanceof Player){
        if(b.getFilterData().categoryBits == ENEMY || a.getFilterData().categoryBits == ENEMY){
          dead = true;
        }
        if(obj2 instanceof Solid || obj1 instanceof Solid){
          System.out.println(a.getBody().getType() + " has hit " + b.getBody().getType());
          this.jumping = false;
        }
        if(obj2 instanceof Checkpoint || obj1 instanceof Checkpoint){
          if(obj2 instanceof Checkpoint && !(obj2 instanceof Spawn)){
            player.setSpawn((Checkpoint)obj2);
            updateMap();
          }else if(obj1 instanceof Checkpoint && !(obj1 instanceof Spawn)){
            player.setSpawn((Checkpoint)obj1);
            updateMap();
          }
        }
        if(obj2 instanceof Goal || obj1 instanceof Goal){
          //if(obj2 instanceof Goal){
          //  gameWorld.destroyBody(((Player)obj1).getBody());
          //  gameWorld.destroyBody(((Tile)obj2).getBody());
          //  ((Tile)obj2).setBody(null);
          //  ((Player)obj1).setBody(null);
          //}else{
          //  gameWorld.destroyBody(((Tile)obj1).getBody());
          //  gameWorld.destroyBody(((Player)obj2).getBody());
          //  ((Tile)obj1).setBody(null);
          //  ((Player)obj2).setBody(null);
          //}
          nextLevel = true;
          //this.dispose();
          //game.setScreen(new GameScreen(game, "level_" + ((Integer.parseInt(levelName.substring(levelName.indexOf("_") + 1, levelName.indexOf("."))))+1) + ".txt"));
        }
        if(obj2 instanceof ColourGiver || obj1 instanceof ColourGiver){
          if(obj2 instanceof ColourGiver){
            player.addColour(((ColourGiver)obj2).getColour());
            updateMap();
          }else{
            player.addColour(((ColourGiver)obj1).getColour());
            updateMap();
          }
        }
        if(obj2 instanceof ColourRemover || obj1 instanceof ColourRemover){
          player.clearColour();
          updateMap();
        }
        if(obj2 instanceof BrokenBlock || obj1 instanceof BrokenBlock){
          if(obj2 instanceof BrokenBlock){
            ((BrokenBlock)obj2).setActive(true);
          }else{
            ((BrokenBlock)obj1).setActive(true);
          }
        }
      }
      if(obj2 instanceof CrushingBlock || obj1 instanceof CrushingBlock){

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