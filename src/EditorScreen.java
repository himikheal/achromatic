import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.file.Files;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * EditorScreen
 * screen to encompass the level editor
 * contains "buttons" to represent different blocks and enemies, as well as 
 * an upload button and a playtest button
 */
public class EditorScreen extends ScreenAdapter {

  private ColourGame game;
  private ShapeRenderer sr;
  private Tile selected;
  private Tile[][] map = new Tile[50][50];
  private Texture zero = new Texture("assets/sprites/Idle 0.png");
  private Texture one = new Texture("assets/sprites/Idle 1.png");
  private Texture two = new Texture("assets/sprites/Idle 2.png");
  private Texture three = new Texture("assets/sprites/Idle 3.png");
  private Texture four = new Texture("assets/sprites/Idle 4.png");
  private Texture five = new Texture("assets/sprites/Idle 5.png");
  private Texture six = new Texture("assets/sprites/Idle 6.png");
  private Texture seven = new Texture("assets/sprites/Idle 7.png");
  private Texture eight = new Texture("assets/sprites/Idle 8.png");
  private Texture nine = new Texture("assets/sprites/Idle 9.png");
  private Texture zeroDown = new Texture("assets/sprites/Pressed 0.png");
  private Texture oneDown = new Texture("assets/sprites/Pressed 1.png");
  private Texture twoDown = new Texture("assets/sprites/Pressed 2.png");
  private Texture threeDown = new Texture("assets/sprites/Pressed 3.png");
  private Texture fourDown = new Texture("assets/sprites/Pressed 4.png");
  private Texture fiveDown = new Texture("assets/sprites/Pressed 5.png");
  private Texture sixDown = new Texture("assets/sprites/Pressed 6.png");
  private Texture sevenDown = new Texture("assets/sprites/Pressed 7.png");
  private Texture eightDown = new Texture("assets/sprites/Pressed 8.png");
  private Texture nineDown = new Texture("assets/sprites/Pressed 9.png");
  private Texture box = new Texture("assets/sprites/boxSprite0_2.png");
  private Texture boxR = new Texture("assets/sprites/boxSprite1_1.png");
  private Texture boxO = new Texture("assets/sprites/boxSprite2_1.png");
  private Texture boxY = new Texture("assets/sprites/boxSprite3_1.png");
  private Texture boxG = new Texture("assets/sprites/boxSprite4_1.png");
  private Texture boxB = new Texture("assets/sprites/boxSprite5_1.png");
  private Texture boxP = new Texture("assets/sprites/boxSprite6_1.png");
  private Texture checkpoint = new Texture("assets/sprites/checkSprite_1.png");
  private Texture giver = new Texture("assets/sprites/giverSprite_0.png");
  private Texture giverR = new Texture("assets/sprites/giverSprite_1.png");
  private Texture giverO = new Texture("assets/sprites/giverSprite_2.png");
  private Texture giverY = new Texture("assets/sprites/giverSprite_3.png");
  private Texture giverG = new Texture("assets/sprites/giverSprite_4.png");
  private Texture giverB = new Texture("assets/sprites/giverSprite_5.png");
  private Texture giverP = new Texture("assets/sprites/giverSprite_6.png");
  private Texture remover = new Texture("assets/sprites/removerSprite.png");
  private Texture spawn = new Texture("assets/sprites/spawnSprite_1.png");
  private Texture spikes = new Texture("assets/sprites/spikeSprite0_1.png");
  private Texture spikesR = new Texture("assets/sprites/spikeSprite1_2.png");
  private Texture spikesO = new Texture("assets/sprites/spikeSprite2_2.png");
  private Texture spikesY = new Texture("assets/sprites/spikeSprite3_2.png");
  private Texture spikesG = new Texture("assets/sprites/spikeSprite4_2.png");
  private Texture spikesB = new Texture("assets/sprites/spikeSprite5_2.png");
  private Texture spikesP = new Texture("assets/sprites/spikeSprite6_2.png");
  private Texture tile = new Texture("assets/sprites/tileSprite0_1.png");
  private Texture tileR = new Texture("assets/sprites/tileSprite1_2.png");
  private Texture tileO = new Texture("assets/sprites/tileSprite2_2.png");
  private Texture tileY = new Texture("assets/sprites/tileSprite3_2.png");
  private Texture tileG = new Texture("assets/sprites/tileSprite4_2.png");
  private Texture tileB = new Texture("assets/sprites/tileSprite5_2.png");
  private Texture tileP = new Texture("assets/sprites/tileSprite6_2.png");
  private Texture broken = new Texture("assets/sprites/breakSprite0_1.png");
  private Texture brokenR = new Texture("assets/sprites/breakSprite1_2.png");
  private Texture brokenO = new Texture("assets/sprites/breakSprite2_2.png");
  private Texture brokenY = new Texture("assets/sprites/breakSprite3_2.png");
  private Texture brokenG = new Texture("assets/sprites/breakSprite4_2.png");
  private Texture brokenB = new Texture("assets/sprites/breakSprite5_2.png");
  private Texture brokenP = new Texture("assets/sprites/breakSprite6_2.png");
  private Texture move = new Texture("assets/sprites/moveSprite0_1.png");
  private Texture moveR = new Texture("assets/sprites/moveSprite1_2.png");
  private Texture moveO = new Texture("assets/sprites/moveSprite2_2.png");
  private Texture moveY = new Texture("assets/sprites/moveSprite3_2.png");
  private Texture moveG = new Texture("assets/sprites/moveSprite4_2.png");
  private Texture moveB = new Texture("assets/sprites/moveSprite5_2.png");
  private Texture moveP = new Texture("assets/sprites/moveSprite6_2.png");
  private Texture ice = new Texture("assets/sprites/iceSprite0_1.png");
  private Texture iceR = new Texture("assets/sprites/iceSprite1_2.png");
  private Texture iceO = new Texture("assets/sprites/iceSprite2_2.png");
  private Texture iceY = new Texture("assets/sprites/iceSprite3_2.png");
  private Texture iceG = new Texture("assets/sprites/iceSprite4_2.png");
  private Texture iceB = new Texture("assets/sprites/iceSprite5_2.png");
  private Texture iceP = new Texture("assets/sprites/iceSprite6_2.png");
  private Texture crusher = new Texture("assets/sprites/crushSprite0_1.png");
  private Texture crusherR = new Texture("assets/sprites/crushSprite1_2.png");
  private Texture crusherO = new Texture("assets/sprites/crushSprite2_2.png");
  private Texture crusherY = new Texture("assets/sprites/crushSprite3_2.png");
  private Texture crusherG = new Texture("assets/sprites/crushSprite4_2.png");
  private Texture crusherB = new Texture("assets/sprites/crushSprite5_2.png");
  private Texture crusherP = new Texture("assets/sprites/crushSprite6_2.png");
  private Texture floater = new Texture("assets/sprites/floatSprite0_1.png");
  private Texture floaterR = new Texture("assets/sprites/floatSprite1_2.png");
  private Texture floaterO = new Texture("assets/sprites/floatSprite2_2.png");
  private Texture floaterY = new Texture("assets/sprites/floatSprite3_2.png");
  private Texture floaterG = new Texture("assets/sprites/floatSprite4_2.png");
  private Texture floaterB = new Texture("assets/sprites/floatSprite5_2.png");
  private Texture floaterP = new Texture("assets/sprites/floatSprite6_2.png");
  private Texture sticker = new Texture("assets/sprites/stickSprite0_1.png");
  private Texture stickerR = new Texture("assets/sprites/stickSprite1_2.png");
  private Texture stickerO = new Texture("assets/sprites/stickSprite2_2.png");
  private Texture stickerY = new Texture("assets/sprites/stickSprite3_2.png");
  private Texture stickerG = new Texture("assets/sprites/stickSprite4_2.png");
  private Texture stickerB = new Texture("assets/sprites/stickSprite5_2.png");
  private Texture stickerP = new Texture("assets/sprites/stickSprite6_2.png");
  private Texture walker = new Texture("assets/sprites/walkSprite0_1.png");
  private Texture walkerR = new Texture("assets/sprites/walkSprite1_2.png");
  private Texture walkerO = new Texture("assets/sprites/walkSprite2_2.png");
  private Texture walkerY = new Texture("assets/sprites/walkSprite3_2.png");
  private Texture walkerG = new Texture("assets/sprites/walkSprite4_2.png");
  private Texture walkerB = new Texture("assets/sprites/walkSprite5_2.png");
  private Texture walkerP = new Texture("assets/sprites/walkSprite6_2.png");
  private Texture goal = new Texture("assets/sprites/doorSprite0_1.png");
  private Texture goalR = new Texture("assets/sprites/doorSprite1_2.png");
  private Texture goalO = new Texture("assets/sprites/doorSprite2_2.png");
  private Texture goalY = new Texture("assets/sprites/doorSprite3_2.png");
  private Texture goalG = new Texture("assets/sprites/doorSprite4_2.png");
  private Texture goalB = new Texture("assets/sprites/doorSprite5_2.png");
  private Texture goalP = new Texture("assets/sprites/doorSprite6_2.png");

  private Texture playButton = new Texture("assets/sprites/playButton.png");
  private Texture uploadButton = new Texture("assets/sprites/uploadButton.png");
  private Sprite boxSprite = new Sprite(box);
  private Sprite checkpointSprite = new Sprite(checkpoint);
  private Sprite giverSprite = new Sprite(giver);
  private Sprite removerSprite = new Sprite(remover);
  private Sprite spawnSprite = new Sprite(spawn);
  private Sprite spikesSprite = new Sprite(spikes);
  private Sprite tileSprite = new Sprite(tile);
  private Sprite playButtonSprite = new Sprite(playButton);
  private Sprite brokenSprite = new Sprite(broken);
  private Sprite moveSprite = new Sprite(move);
  private Sprite iceSprite = new Sprite(ice);
  private Sprite crushSprite = new Sprite(crusher);
  private Sprite floatSprite = new Sprite(floater);
  private Sprite stickSprite = new Sprite(sticker);
  private Sprite walkSprite = new Sprite(walker);
  private Sprite goalSprite = new Sprite(goal);

  private Sprite uploadButtonSprite = new Sprite(uploadButton);
  private int colour = 0;
  private Texture[] boxTextures = new Texture[7];
  private Texture[] giverTextures = new Texture[7];
  private Texture[] spikesTextures = new Texture[7];
  private Texture[] tileTextures = new Texture[7];
  private Texture[] brokenTextures = new Texture[7];
  private Texture[] moveTextures = new Texture[7];
  private Texture[] iceTextures = new Texture[7];
  private Texture[] crushTextures = new Texture[7];
  private Texture[] floatTextures = new Texture[7];
  private Texture[] stickTextures = new Texture[7];
  private Texture[] walkTextures = new Texture[7];
  private Texture[] goalTextures = new Texture[7];
  private boolean spawnPlaced = false;
  private boolean goalPlaced = false;
  private boolean configured = false;
  private boolean moving = false;
  private int forwards, backwards, lvlW, lvlH;

  private String[] tileChars = new String[] {"@", "^", "#", "V", "X", "=", "D"};
  private Tile[] tileTypes = new Tile[] {new PushableBlock(null, null, null, 0)
                               , new Spikes(null, null, null)
                               , new Solid(null, null, null)
                               , new CrushingBlock(null, null, null, 3, null)
                               , new BrokenBlock(null, null, null, 2)
                               , new SlipperyBlock(null, null, null)
                               , new Goal(null, null, null, 0)};
  private Socket socket;
  private ObjectInputStream input;
  private ObjectOutputStream output;

  /**
   * EditorScreen
   * constructor for the editorscreen
   * passes in the ColourGame reference
   * @param game reference to the game object
   */
  EditorScreen(ColourGame game) {
    this.game = game;
  }

  /**
   * EditorScreen
   * alternate constructor for when escape is pressed in a gameScreen
   * @param game reference to the game object
   * @param file file wanted to be loaded into the editor
   */
  EditorScreen(ColourGame game, File file) {
    this.game = game;
    loadMap(file);
  }

  /**
   * show
   * automatically runs once after constructor is called
   * contains all the information for initializing class variables and drawing sprite positions
   */
  @Override
  public void show() {

    sr = new ShapeRenderer();
    sr.setAutoShapeType(true);
    sr.setColor(Color.BLACK);
    boxSprite.setPosition(16, Gdx.graphics.getHeight() - 80);
    checkpointSprite.setPosition(96, Gdx.graphics.getHeight() - 80);
    giverSprite.setPosition(176, Gdx.graphics.getHeight() - 80);
    removerSprite.setPosition(256, Gdx.graphics.getHeight() - 80);
    spawnSprite.setPosition(336, Gdx.graphics.getHeight() - 80);
    spikesSprite.setPosition(416, Gdx.graphics.getHeight() - 80);
    tileSprite.setPosition(496, Gdx.graphics.getHeight() - 80);
    brokenSprite.setPosition(16, Gdx.graphics.getHeight() - 160);
    moveSprite.setPosition(96, Gdx.graphics.getHeight() - 160);
    iceSprite.setPosition(176, Gdx.graphics.getHeight() - 160);
    crushSprite.setPosition(256, Gdx.graphics.getHeight() - 160);
    floatSprite.setPosition(336, Gdx.graphics.getHeight() - 160);
    stickSprite.setPosition(416, Gdx.graphics.getHeight() - 160);
    walkSprite.setPosition(496, Gdx.graphics.getHeight() - 160);
    goalSprite.setPosition(16, Gdx.graphics.getHeight() - 240);
    playButtonSprite.setSize(174, 78);
    playButtonSprite.setPosition(10, 10);
    uploadButtonSprite.setSize(246,78);
    uploadButtonSprite.setPosition(200, 10);
    boxTextures[0] = box;
    boxTextures[1] = boxR;
    boxTextures[2] = boxO;
    boxTextures[3] = boxY;
    boxTextures[4] = boxG;
    boxTextures[5] = boxB;
    boxTextures[6] = boxP;
    giverTextures[0] = giver;
    giverTextures[1] = giverR;
    giverTextures[2] = giverO;
    giverTextures[3] = giverY;
    giverTextures[4] = giverG;
    giverTextures[5] = giverB;
    giverTextures[6] = giverP;
    spikesTextures[0] = spikes;
    spikesTextures[1] = spikesR;
    spikesTextures[2] = spikesO;
    spikesTextures[3] = spikesY;
    spikesTextures[4] = spikesG;
    spikesTextures[5] = spikesB;
    spikesTextures[6] = spikesP;
    tileTextures[0] = tile;
    tileTextures[1] = tileR;
    tileTextures[2] = tileO;
    tileTextures[3] = tileY;
    tileTextures[4] = tileG;
    tileTextures[5] = tileB;
    tileTextures[6] = tileP;
    brokenTextures[0] = broken;
    brokenTextures[1] = brokenR;
    brokenTextures[2] = brokenO;
    brokenTextures[3] = brokenY;
    brokenTextures[4] = brokenG;
    brokenTextures[5] = brokenB;
    brokenTextures[6] = brokenP;
    moveTextures[0] = move;
    moveTextures[1] = moveR;
    moveTextures[2] = moveO;
    moveTextures[3] = moveY;
    moveTextures[4] = moveG;
    moveTextures[5] = moveB;
    moveTextures[6] = moveP;
    iceTextures[0] = ice;
    iceTextures[1] = iceR;
    iceTextures[2] = iceO;
    iceTextures[3] = iceY;
    iceTextures[4] = iceG;
    iceTextures[5] = iceB;
    iceTextures[6] = iceP;
    crushTextures[0] = crusher;
    crushTextures[1] = crusherR;
    crushTextures[2] = crusherO;
    crushTextures[3] = crusherY;
    crushTextures[4] = crusherG;
    crushTextures[5] = crusherB;
    crushTextures[6] = crusherP;
    floatTextures[0] = floater;
    floatTextures[1] = floaterR;
    floatTextures[2] = floaterO;
    floatTextures[3] = floaterY;
    floatTextures[4] = floaterG;
    floatTextures[5] = floaterB;
    floatTextures[6] = floaterP;
    stickTextures[0] = sticker;
    stickTextures[1] = stickerR;
    stickTextures[2] = stickerO;
    stickTextures[3] = stickerY;
    stickTextures[4] = stickerG;
    stickTextures[5] = stickerB;
    stickTextures[6] = stickerP;
    walkTextures[0] = walker;
    walkTextures[1] = walkerR;
    walkTextures[2] = walkerO;
    walkTextures[3] = walkerY;
    walkTextures[4] = walkerG;
    walkTextures[5] = walkerB;
    walkTextures[6] = walkerP;
    goalTextures[0] = goal;
    goalTextures[1] = goalR;
    goalTextures[2] = goalO;
    goalTextures[3] = goalY;
    goalTextures[4] = goalG;
    goalTextures[5] = goalB;
    goalTextures[6] = goalP;
  }

  /**
   * render
   * method called constantly when screen is instantiated
   * contains all instructions for drawing to the screen and updating position of sprites and such
   * the main loop of a screen
   */
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(255, 255, 255, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  
    sr.begin(ShapeType.Line);
    sr.setColor(Color.BLACK);
    for(int i = 0; i <= 50; i++) {
      sr.line(630 + (i * 16.2f), 0f, 630 + (i * 16.2f), 810f);
      sr.line(630, (i * 16.2f), 1440, (i * 16.2f));
    }
    sr.rect(96, Gdx.graphics.getHeight() - 240, 64, 64);

    sr.set(ShapeType.Filled);

    sr.setColor(Color.GRAY);
    sr.rect(600, 744, 30, 50);

    sr.setColor(Color.RED);
    sr.rect(600, 694, 30, 50);

    sr.setColor(Color.ORANGE);
    sr.rect(600, 644, 30, 50);

    sr.setColor(Color.YELLOW);
    sr.rect(600, 594, 30, 50);

    sr.setColor(Color.GREEN);
    sr.rect(600, 544, 30, 50);

    sr.setColor(Color.BLUE);
    sr.rect(600, 494, 30, 50);

    sr.setColor(Color.PURPLE);
    sr.rect(600, 444, 30, 50);

    sr.end();

    if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
      if(Gdx.input.getX() >= 630
      && Gdx.input.getX() < 1440
      && Gdx.input.getY() > 0
      && Gdx.input.getY() < 810) {
        int gridX = (int)((Gdx.input.getX() - 630) / 16.2);
        int gridY = (int)(Gdx.input.getY() / 16.2);
        if((!(selected instanceof Spawn) || (selected instanceof Spawn && !spawnPlaced)) 
        && (!(selected instanceof Goal) || (selected instanceof Goal && !goalPlaced))) {
          if(map[gridX][gridY] instanceof Spawn) {
            spawnPlaced = false;
          }
          else if(map[gridX][gridY] instanceof Goal) {
            goalPlaced = false;
          }
          if(!moving || (moving && configured)) {
            map[gridX][gridY] = selected;
            if(selected instanceof Spawn) {
              spawnPlaced = true;
            }
            else if(selected instanceof Goal) {
              goalPlaced = true;
            }
          }
        }
      }
    }
    if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
      if(Gdx.input.getY() > 16 && Gdx.input.getY() < 80) {
        configured = false;
        if(Gdx.input.getX() > 16
        && Gdx.input.getX() < 80) {
          selected = new PushableBlock(null, new Sprite(boxSprite.getTexture()), null, 1);
          ((PushableBlock)selected).setColour(this.colour);
          moving = false;
        }
        else if(Gdx.input.getX() > 96
             && Gdx.input.getX() < 160) {
          selected = new Checkpoint(null, new Sprite(checkpointSprite.getTexture()), null);
          moving = false;
        }
        else if(Gdx.input.getX() > 176
             && Gdx.input.getX() < 240
             && this.colour != 0) {
          selected = new ColourGiver(null, new Sprite(giverSprite.getTexture()), null, this.colour);
          ((ColourGiver)selected).setColour(this.colour);
          moving = false;
        }
        else if(Gdx.input.getX() > 256
             && Gdx.input.getX() < 320) {
          selected = new ColourRemover(null, new Sprite(removerSprite.getTexture()), null);
          moving = false;
        }
        else if(Gdx.input.getX() > 336
             && Gdx.input.getX() < 400) {
          selected = new Spawn(null, new Sprite(spawnSprite.getTexture()), null);
          moving = false;
        }
        else if(Gdx.input.getX() > 416
             && Gdx.input.getX() < 480) {
          selected = new Spikes(null, new Sprite(spikesSprite.getTexture()), null);
          ((Spikes)selected).setColour(this.colour);
          moving = false;
        }
        else if(Gdx.input.getX() > 496
             && Gdx.input.getX() < 560) {
          selected = new Solid(null, new Sprite(tileSprite.getTexture()), null);
          ((Solid)selected).setColour(this.colour);
          moving = false;
        }
      }
      else if(Gdx.input.getY() > 96 && Gdx.input.getY() < 160) {
        configured = false;
        if(Gdx.input.getX() > 16
        && Gdx.input.getX() < 80) {
          selected = new BrokenBlock(null, new Sprite(brokenSprite.getTexture()), null, 1);
          ((BrokenBlock)selected).setColour(this.colour);
          moving = false;
        }
        else if(Gdx.input.getX() > 96
             && Gdx.input.getX() < 160) {
          selected = new MovingBlock(null, new Sprite(moveSprite.getTexture()), null, null);
          ((MovingBlock)selected).setColour(this.colour);
          moving = true;
        }
        else if(Gdx.input.getX() > 176
             && Gdx.input.getX() < 240) {
          selected = new SlipperyBlock(null, new Sprite(iceSprite.getTexture()), null);
          ((SlipperyBlock)selected).setColour(this.colour);
          moving = false;
        }
        else if(Gdx.input.getX() > 256
             && Gdx.input.getX() < 320) {
          selected = new CrushingBlock(null, new Sprite(crushSprite.getTexture()), null, 1, null);
          ((CrushingBlock)selected).setColour(this.colour);
          moving = false;
        }
        else if(Gdx.input.getX() > 336
             && Gdx.input.getX() < 400) {
          selected = new Floater(null, new Sprite(floatSprite.getTexture()), null, null);
          ((Floater)selected).setColour(this.colour);
          moving = true;
        }
        else if(Gdx.input.getX() > 416
             && Gdx.input.getX() < 480) {
          selected = new Sticker(null, new Sprite(stickSprite.getTexture()), null, 1, 0);
          ((Sticker)selected).setColour(this.colour);
          moving = false;
        }
        else if(Gdx.input.getX() > 496
             && Gdx.input.getX() < 560) {
          selected = new Walker(null, new Sprite(walkSprite.getTexture()), null, null);
          ((Walker)selected).setColour(this.colour);
          moving = true;
        }
      }
      else if(Gdx.input.getY() > 176 && Gdx.input.getY() < 240) {
        configured = false;
        if(Gdx.input.getX() > 16
        && Gdx.input.getX() < 80) {
          selected = new Goal(null, new Sprite(goalSprite.getTexture()), null, 1);
          ((Goal)selected).setColour(this.colour);
          moving = false;
        }
        else if(Gdx.input.getX() > 96
             && Gdx.input.getX() < 160) {
          selected = null;
          moving = false;
        }
      }
      else if(Gdx.input.getY() > 432 && Gdx.input.getY() < 480 && moving) {
        if(Gdx.input.getX() > 16
        && Gdx.input.getX() < 64) {
          forwards = 0;
        }else if(Gdx.input.getX() > (64)
        && Gdx.input.getX() < 112) {
          forwards = 1;
        }else if(Gdx.input.getX() > (112)
        && Gdx.input.getX() < 160) {
          forwards = 2;
        }else if(Gdx.input.getX() > (160)
        && Gdx.input.getX() < 208) {
          forwards = 3;
        }else if(Gdx.input.getX() > (208)
        && Gdx.input.getX() < 256) {
          forwards = 4;
        }else if(Gdx.input.getX() > (256)
        && Gdx.input.getX() < 304) {
          forwards = 5;
        }else if(Gdx.input.getX() > (304)
        && Gdx.input.getX() < 352) {
          forwards = 6;
        }else if(Gdx.input.getX() > (352)
        && Gdx.input.getX() < 400) {
          forwards = 7;
        }else if(Gdx.input.getX() > (400)
        && Gdx.input.getX() < 448) {
          forwards = 8;
        }else if(Gdx.input.getX() > (448)
        && Gdx.input.getX() < 496) {
          forwards = 9;
        }
      }
      else if(Gdx.input.getY() > 512 && Gdx.input.getY() < 560 && moving) {
        if(Gdx.input.getX() > 16
        && Gdx.input.getX() < 64) {
          backwards = 0;
        }
        else if(Gdx.input.getX() > (64)
        && Gdx.input.getX() < 112) {
          backwards = 1;
        }
        else if(Gdx.input.getX() > (112)
        && Gdx.input.getX() < 160) {
          backwards = 2;
        }
        else if(Gdx.input.getX() > (160)
        && Gdx.input.getX() < 208) {
          backwards = 3;
        }
        else if(Gdx.input.getX() > (208)
        && Gdx.input.getX() < 256) {
          backwards = 4;
        }
        else if(Gdx.input.getX() > (256)
        && Gdx.input.getX() < 304) {
          backwards = 5;
        }
        else if(Gdx.input.getX() > (304)
        && Gdx.input.getX() < 352) {
          backwards = 6;
        }
        else if(Gdx.input.getX() > (352)
        && Gdx.input.getX() < 400) {
          backwards = 7;
        }
        else if(Gdx.input.getX() > (400)
        && Gdx.input.getX() < 448) {
          backwards = 8;
        }
        else if(Gdx.input.getX() > (448)
        && Gdx.input.getX() < 496) {
          backwards = 9;
        }
        if(forwards < 9 && backwards < 9) {
          if(selected instanceof MovingBlock) {
            ((MovingBlock)selected).setPoints(new int[]{backwards, forwards});
            configured = true;
          }else if(selected instanceof Floater) {
            ((Floater)selected).setPoints(new int[]{backwards, forwards});
            configured = true;
          }else if(selected instanceof Walker) {
            ((Walker)selected).setPatrol(new int[]{backwards, forwards});
            configured = true;
          }
        }
      }
      if(Gdx.input.getX() > 600 && Gdx.input.getX() < 630) {
        if(Gdx.input.getY() > Gdx.graphics.getHeight() - 744 - 50
        && Gdx.input.getY() < Gdx.graphics.getHeight() - 744) {
          this.colour = 0;
        }
        else if(Gdx.input.getY() > Gdx.graphics.getHeight() - 694 - 50
             && Gdx.input.getY() < Gdx.graphics.getHeight() - 694) {
          this.colour = 1;
        }
        else if(Gdx.input.getY() > Gdx.graphics.getHeight() - 644 - 50
             && Gdx.input.getY() < Gdx.graphics.getHeight() - 644) {
          this.colour = 2;
        }
        else if(Gdx.input.getY() > Gdx.graphics.getHeight() - 594 - 50
             && Gdx.input.getY() < Gdx.graphics.getHeight() - 594) {
          this.colour = 3;
        }
        else if(Gdx.input.getY() > Gdx.graphics.getHeight() - 544 - 50
             && Gdx.input.getY() < Gdx.graphics.getHeight() - 544) {
          this.colour = 4;
        }
        else if(Gdx.input.getY() > Gdx.graphics.getHeight() - 494 - 50
             && Gdx.input.getY() < Gdx.graphics.getHeight() - 494) {
          this.colour = 5;
        }
        else if(Gdx.input.getY() > Gdx.graphics.getHeight() - 444 - 50
             && Gdx.input.getY() < Gdx.graphics.getHeight() - 444) {
          this.colour = 6;
        }
        boxSprite.setTexture(boxTextures[colour]);
        giverSprite.setTexture(giverTextures[colour]);
        spikesSprite.setTexture(spikesTextures[colour]);
        tileSprite.setTexture(tileTextures[colour]);
        brokenSprite.setTexture(brokenTextures[colour]);
        moveSprite.setTexture(moveTextures[colour]);
        iceSprite.setTexture(iceTextures[colour]);
        crushSprite.setTexture(crushTextures[colour]);
        floatSprite.setTexture(floatTextures[colour]);
        stickSprite.setTexture(stickTextures[colour]);
        walkSprite.setTexture(walkTextures[colour]);
        goalSprite.setTexture(goalTextures[colour]);
      }

      if(Gdx.input.getY() < Gdx.graphics.getHeight() - 10
      && Gdx.input.getY() > Gdx.graphics.getHeight() - 88
      && spawnPlaced 
      && goalPlaced) {
        if(Gdx.input.getX() > 10   
        && Gdx.input.getX() < 184){
          writeToFile(map, "tempMap");
          game.setScreen(new GameScreen(game, "tempMap.txt"));
        }
        else if(Gdx.input.getX() > 200
             && Gdx.input.getX() < 446) {
          writeToFile(map, "uploadMap");
          connect("127.0.0.1", 5000);
          try {
            output.writeObject("/UPLOAD!");
            File tempFile = new File("assets/levels/uploadMap.txt");
            byte[] fileBytes = Files.readAllBytes(tempFile.toPath());
            output.writeObject(fileBytes);
            String o = (String) input.readObject();
            System.out.println("Your map code is " + o);
            
            input.close();
            output.close();
            socket.close();
          } catch(IOException e) {
            System.out.println("IOException occured");
            e.printStackTrace();
          } catch(ClassNotFoundException e2) {
            System.out.println("Class not found");
          }
        }
      }
    }
    

    

    game.batch.begin();

    boxSprite.draw(game.batch);
    checkpointSprite.draw(game.batch);
    giverSprite.draw(game.batch);
    removerSprite.draw(game.batch);
    spawnSprite.draw(game.batch);
    spikesSprite.draw(game.batch);
    tileSprite.draw(game.batch);
    brokenSprite.draw(game.batch);
    moveSprite.draw(game.batch);
    iceSprite.draw(game.batch);
    crushSprite.draw(game.batch);
    floatSprite.draw(game.batch);
    stickSprite.draw(game.batch);
    walkSprite.draw(game.batch);
    goalSprite.draw(game.batch);
    playButtonSprite.draw(game.batch);
    if(moving) {
      drawMenu(Gdx.graphics.getHeight() - 480);
      drawMenu(Gdx.graphics.getHeight() - 560);
    }
    uploadButtonSprite.draw(game.batch);

    for(int i = 0; i < map.length; i++) {
      for(int j = 0; j < map.length; j++) {
        if(map[i][j] != null) {
          map[i][j].getSprite().setSize(16.2f, 16.2f);
          map[i][j].getSprite().setPosition(630 + (i * 16.2f), Gdx.graphics.getHeight() - ((j + 1) * 16.2f));
          map[i][j].getSprite().draw(game.batch);
        }
      }
    }
    game.batch.end();
  }

  /**
   * drawMenu
   * method to draw the menu shown when selecting a tile that has additional parameters
   * ie a floater or moving platform
   * @param y stands for the y value at which the menu will be drawn
   */
  public void drawMenu(float y) {
    Sprite[] menuUp = new Sprite[10];
    menuUp[0] = new Sprite(zero);
    menuUp[1] = new Sprite(one);
    menuUp[2] = new Sprite(two);
    menuUp[3] = new Sprite(three);
    menuUp[4] = new Sprite(four);
    menuUp[5] = new Sprite(five);
    menuUp[6] = new Sprite(six);
    menuUp[7] = new Sprite(seven);
    menuUp[8] = new Sprite(eight);
    menuUp[9] = new Sprite(nine);
    for(int i = 0; i < 10; i++) {
      menuUp[i].setSize(48, 48);
      menuUp[i].setPosition((48*i) + 16, y);
      menuUp[i].draw(game.batch);
    }
    
  }

  /**
   * loadMap
   * method called when using alternate constructor, loads file called in constructor into editor
   * @param file file passed in from the alternate constructor
   */
  public void loadMap(File file){
    spawnPlaced = true;
    goalPlaced = true;
    BufferedReader reader;
    String[][] level;
    try{
      reader = new BufferedReader(new FileReader(file));
      lvlH = Integer.parseInt(reader.readLine());
      lvlW = Integer.parseInt(reader.readLine());
      level = new String[lvlH][lvlW];
      for(int i = 0; i < lvlH; i++){
        String st = reader.readLine();
        for(int j = 0; j < lvlW; j++){
          level[i][j] = st.substring(0, st.indexOf(" "));
          try{
            st = st.substring(st.indexOf(" ") + 1, st.length());
          }catch(Exception e){
            st = null;
          }
        }
      }

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

            map[j][i] = new Solid(null, sprite, null);
            if(Integer.parseInt(tileData[3]) != 0){
              ((Solid)map[j][i]).setColour(Integer.parseInt(tileData[3]));
            }
          }
          if(tileData[0].equals("$")){
            Texture tex = new Texture("assets/sprites/spawnSprite_2.png");
            Sprite sprite = new Sprite(tex);

            map[j][i] = new Spawn(null, sprite, null);
          }
          if(tileData[0].equals("G")){
            Texture tex = new Texture("assets/sprites/giverSprite_" + tileData[3] + ".png");
            Sprite sprite = new Sprite(tex);

            map[j][i] = new ColourGiver(null, sprite, null, Integer.parseInt(tileData[1]));
          }
          if(tileData[0].equals("R")){
            Texture tex = new Texture("assets/sprites/removerSprite.png");
            Sprite sprite = new Sprite(tex);
            map[j][i] = new ColourRemover(null, sprite, null);
          }
          if(tileData[0].equals("^")){
            Texture tex = new Texture("assets/sprites/spikeSprite" + tileData[3] + "_" + tileData[1] + ".png");
            Sprite sprite = new Sprite(tex);

            map[j][i] = new Spikes(null, sprite, null);

            if(Integer.parseInt(tileData[3]) != 0){
              ((Spikes)map[j][i]).setColour(Integer.parseInt(tileData[3]));
            }
          }
          if(tileData[0].equals("*")){
            Texture tex = new Texture("assets/sprites/checkSprite_1.png");
            Sprite sprite = new Sprite(tex);

            map[j][i] = new Checkpoint(null, sprite, null);
          }
          if(tileData[0].equals("@")){
            Texture tex = new Texture("assets/sprites/boxSprite" + tileData[3] + "_" + tileData[1] + ".png");
            Sprite sprite = new Sprite(tex);

            map[j][i] = new PushableBlock(null, sprite, null, Integer.parseInt(tileData[2]));
            if(Integer.parseInt(tileData[3]) != 0){
              ((PushableBlock)map[j][i]).setColour(Integer.parseInt(tileData[3]));
            }
          }
          if(tileData[0].equals("X")){
            Texture tex = new Texture("assets/sprites/breakSprite" + tileData[3] + "_" + tileData[1] + ".png");
            Sprite sprite = new Sprite(tex);
            
            map[j][i] = new BrokenBlock(null, sprite, null, Integer.parseInt(tileData[2]));
            if(Integer.parseInt(tileData[3]) != 0){
              ((BrokenBlock)map[j][i]).setColour(Integer.parseInt(tileData[3]));
            }
          }
          if(tileData[0].equals("V")){
            Texture tex = new Texture("assets/sprites/crushSprite" + tileData[3] + "_" + tileData[1] + ".png");
            Sprite sprite = new Sprite(tex);
            
            map[j][i] = new CrushingBlock(null, sprite, null, Integer.parseInt(tileData[2]), null);
            if(Integer.parseInt(tileData[3]) != 0){
              ((CrushingBlock)map[j][i]).setColour(Integer.parseInt(tileData[3]));
            }
          }
          if(tileData[0].equals("-")){
            Texture tex;
            if(Integer.parseInt(tileData[3]) != 0){
              tex = new Texture("assets/sprites/moveSprite" + tileData[3] + "_2.png");
            }else{
              tex = new Texture("assets/sprites/moveSprite0_1.png");
            }
            Sprite sprite = new Sprite(tex);

            int p1 = Integer.parseInt(tileData[1]);
            int p2 = Integer.parseInt(tileData[2]);

            map[j][i] = new MovingBlock(null, sprite, null, new int[]{p1, p2});
            if(Integer.parseInt(tileData[3]) != 0){
              ((MovingBlock)map[j][i]).setColour(Integer.parseInt(tileData[3]));
            }
          }
          if(tileData[0].equals("|")){
            Texture tex;
            if(Integer.parseInt(tileData[3]) != 0){
              tex = new Texture("assets/sprites/moveSprite" + tileData[3] + "_2.png");
            }else{
              tex = new Texture("assets/sprites/moveSprite0_1.png");
            }
            Sprite sprite = new Sprite(tex);
            int p1 = Integer.parseInt(tileData[1]);
            int p2 = Integer.parseInt(tileData[2]);

            map[j][i] = new MovingBlock(null, sprite, null, new int[]{p1, p2});
            if(Integer.parseInt(tileData[3]) != 0){
              ((MovingBlock)map[j][i]).setColour(Integer.parseInt(tileData[3]));
            }
          }
          if(tileData[0].equals("&")){
            Texture tex;
            if(Integer.parseInt(tileData[3]) != 0){
              tex = new Texture("assets/sprites/stickSprite" + tileData[3] + "_2.png");
            }else{
              tex = new Texture("assets/sprites/stickSprite0_1.png");
            }
            Sprite sprite = new Sprite(tex);

            map[j][i] = new Sticker(null, sprite, null, Integer.parseInt(tileData[1]), Integer.parseInt(tileData[2]));
            
            if(Integer.parseInt(tileData[3]) != 0){
              ((Sticker)map[j][i]).setColour(Integer.parseInt(tileData[3]));
            }
          }
          if(tileData[0].equals("f")){
            Texture tex;
            if(Integer.parseInt(tileData[3]) != 0){
              tex = new Texture("assets/sprites/floatSprite" + tileData[3] + "_2.png");
            }else{
              tex = new Texture("assets/sprites/floatSprite0_1.png");
            }
            Sprite sprite = new Sprite(tex);
            
            int p1 = Integer.parseInt(tileData[1]);
            int p2 = Integer.parseInt(tileData[2]);

            map[j][i] = new Floater(null, sprite, null, new int[]{p1, p2});
            if(Integer.parseInt(tileData[3]) != 0){
              ((Floater)map[j][i]).setColour(Integer.parseInt(tileData[3]));
            }
          }
          if(tileData[0].equals("=")){
            Texture tex = new Texture("assets/sprites/iceSprite" + tileData[3] + "_" + tileData[1] + ".png");
            Sprite sprite = new Sprite(tex);

            map[j][i] = new SlipperyBlock(null, sprite, null);
            if(Integer.parseInt(tileData[3]) != 0){
              ((SlipperyBlock)map[j][i]).setColour(Integer.parseInt(tileData[3]));
            }
          }
          if(tileData[0].equals("w")){
            Texture tex;
            if(Integer.parseInt(tileData[3]) != 0){
              tex = new Texture("assets/sprites/walkSprite" + tileData[3] + "_2.png");
            }else{
              tex = new Texture("assets/sprites/walkSprite0_1.png");
            }
            Sprite sprite = new Sprite(tex);
            
            int p1 = Integer.parseInt(tileData[1]);
            int p2 = Integer.parseInt(tileData[2]);

            map[j][i] = new Walker(null, sprite, null, new int[]{p1, p2});
            if(Integer.parseInt(tileData[3]) != 0){
              ((Walker)map[j][i]).setColour(Integer.parseInt(tileData[3]));
            }
          }
          if(tileData[0].equals("D")){
            Texture tex = new Texture("assets/sprites/doorSprite" + tileData[3] + "_" + tileData[1] + ".png");
            Sprite sprite = new Sprite(tex);

            map[j][i] = new Goal(null, sprite, null, Integer.parseInt(tileData[3]));
            if(Integer.parseInt(tileData[3]) != 0){
              ((Goal)map[j][i]).setColour(Integer.parseInt(tileData[3]));
            }
          }
        }
      }
      
    }catch(IOException e){
      System.out.println("File not found");
    }
  }

  /**
   * hide
   * called on close of the screen
   * contains instructions to dispose of all the textures initialized
   */
  @Override
  public void hide() {
    zero.dispose(); 
    one.dispose();  
    two.dispose();  
    three.dispose();  
    four.dispose(); 
    five.dispose(); 
    six.dispose();  
    seven.dispose();  
    eight.dispose();  
    nine.dispose(); 
    zeroDown.dispose(); 
    oneDown.dispose();  
    twoDown.dispose();  
    threeDown.dispose();  
    fourDown.dispose(); 
    fiveDown.dispose(); 
    sixDown.dispose();  
    sevenDown.dispose();  
    eightDown.dispose();  
    nineDown.dispose(); 
    box.dispose();  
    boxR.dispose(); 
    boxO.dispose(); 
    boxY.dispose(); 
    boxG.dispose(); 
    boxB.dispose(); 
    boxP.dispose(); 
    checkpoint.dispose(); 
    giver.dispose();  
    giverR.dispose(); 
    giverO.dispose(); 
    giverY.dispose(); 
    giverG.dispose(); 
    giverB.dispose(); 
    giverP.dispose(); 
    remover.dispose();  
    spawn.dispose();  
    spikes.dispose(); 
    spikesR.dispose();  
    spikesO.dispose();  
    spikesY.dispose();  
    spikesG.dispose();  
    spikesB.dispose();  
    spikesP.dispose();  
    tile.dispose(); 
    tileR.dispose();  
    tileO.dispose();  
    tileY.dispose();  
    tileG.dispose();  
    tileB.dispose();  
    tileP.dispose();  
    broken.dispose(); 
    brokenR.dispose();  
    brokenO.dispose();  
    brokenY.dispose();  
    brokenG.dispose();  
    brokenB.dispose();  
    brokenP.dispose();  
    move.dispose(); 
    moveR.dispose();  
    moveO.dispose();  
    moveY.dispose();  
    moveG.dispose();  
    moveB.dispose();  
    moveP.dispose();  
    ice.dispose();  
    iceR.dispose(); 
    iceO.dispose(); 
    iceY.dispose(); 
    iceG.dispose(); 
    iceB.dispose(); 
    iceP.dispose(); 
    crusher.dispose();  
    crusherR.dispose(); 
    crusherO.dispose(); 
    crusherY.dispose(); 
    crusherG.dispose(); 
    crusherB.dispose(); 
    crusherP.dispose(); 
    floater.dispose();  
    floaterR.dispose(); 
    floaterO.dispose(); 
    floaterY.dispose(); 
    floaterG.dispose(); 
    floaterB.dispose(); 
    floaterP.dispose(); 
    sticker.dispose();  
    stickerR.dispose(); 
    stickerO.dispose(); 
    stickerY.dispose(); 
    stickerG.dispose(); 
    stickerB.dispose(); 
    stickerP.dispose(); 
    walker.dispose(); 
    walkerR.dispose();  
    walkerO.dispose();  
    walkerY.dispose();  
    walkerG.dispose();  
    walkerB.dispose();  
    walkerP.dispose();  
    goal.dispose(); 
    goalR.dispose();  
    goalO.dispose();  
    goalY.dispose();  
    goalG.dispose();  
    goalB.dispose();  
    goalP.dispose();  
  }

  /**
   * connect
   * method for connecting to the server, for level uploading
   * @param ip the ip of the server
   * @param port port of the server
   * @return socket the socket used to connect to the server
   */
  public Socket connect(String ip, int port) {
    System.out.println("Attempting to make a connection..");

    try {

      socket = new Socket("127.0.0.1", 5000); //attempt socket connection (local address). This will wait until a connection is made
      output = new ObjectOutputStream(socket.getOutputStream());
      input = new ObjectInputStream(socket.getInputStream());
      
    } catch (IOException e) {  //connection error occured
      System.out.println("Connection to Server Failed");
      e.printStackTrace();
    }
    
    System.out.println("Connection made.");
    return socket;
  }

  /**
   * writeToFile
   * writes the editor plane to a file while minimizing the size of the text file to encompass only the area added by the player
   * @param map map to write to a file
   * @param fileName name of the file to write to
   */
  public void writeToFile(Tile[][] map, String fileName) {
    try {
      int[] hasTilesH = new int[2];
      int[] hasTilesV = new int[2];
      boolean firstH = true;
      boolean firstV = true;
      boolean firstHEnd = true;
      boolean firstVEnd = true;

      for(int i = 0; i < map.length; i++) {
        boolean emptyH = true;
        boolean emptyV = true;
        boolean emptyHEnd = true;
        boolean emptyVEnd = true;
        for(int j = 0; j < map.length; j++) {
          if(map[j][i] != null) {
            emptyH = false;
          }
          if(map[i][j] != null) {
            emptyV = false;
          }
          if(map[49 - j][49 - i] != null) {
            emptyHEnd = false;
          }
          if(map[49 - i][49 - j] != null) {
            emptyVEnd = false;
          }
        }
        if(!emptyH && firstH) {
          hasTilesH[0] = i;
          firstH = false;
        }
        if(!emptyV && firstV) {
          hasTilesV[0] = i;
          firstV = false;
        }
        if(!emptyHEnd && firstHEnd) {
          hasTilesH[1] = 49 - i;
          firstHEnd = false;
        }
        if(!emptyVEnd && firstVEnd) {
          hasTilesV[1] = 49 - i;
          firstVEnd = false;
        }
      }

      PrintWriter writer = new PrintWriter("assets/levels/" + fileName + ".txt", "UTF-8");
      writer.println(hasTilesH[1] + 1 - hasTilesH[0]);
      writer.println(hasTilesV[1] + 1 - hasTilesV[0]);
      for(int i = hasTilesH[0]; i <= hasTilesH[1]; i++) {
        for(int j = hasTilesV[0]; j <= hasTilesV[1]; j++) {
          if(map[j][i] instanceof Spawn) {
            writer.print("$000");
          }
          else if(map[j][i] instanceof Checkpoint) {
            writer.print("*000");
          }
          else if(map[j][i] instanceof ColourRemover) {
            writer.print("R000");
          }
          else if(map[j][i] instanceof ColourGiver) {
            writer.print("G" + ((ColourGiver)map[j][i]).getColour() + "0" + ((ColourGiver)map[j][i]).getColour());
          }
          else if(map[j][i] instanceof Sticker) {
            writer.print("&10" + ((Sticker)map[j][i]).getColour());
          }
          else if(map[j][i] instanceof MovingBlock) {
            writer.print("-" + ((MovingBlock)map[j][i]).getPoints()[0] + ((MovingBlock)map[j][i]).getPoints()[1] + ((MovingBlock)map[j][i]).getColour());
          }
          else if(map[j][i] instanceof Floater) {
            writer.print("f" + ((Floater)map[j][i]).getPoints()[0] + ((Floater)map[j][i]).getPoints()[1] + ((Floater)map[j][i]).getColour());
          }
          else if(map[j][i] instanceof Walker) {
            writer.print("w" + ((Walker)map[j][i]).getPatrol()[0] + ((Walker)map[j][i]).getPatrol()[1] + ((Walker)map[j][i]).getColour());
          }
          else if(map[j][i] == null) {
            writer.print("~100");
          }
          else {
            for(int k = 0; k < tileTypes.length; k++) {
              if(map[j][i].getClass() == tileTypes[k].getClass()) {
                int solid = 2;
                if(map[j][i] instanceof Solid) {
                  if(((Solid)map[j][i]).getColour() == 0) {
                    solid = 1;
                  }
                }
                else if(map[j][i] instanceof Goal) {
                  if(((Goal)map[j][i]).getColour() == 0) {
                    solid = 1;
                  }
                }
                if(map[j][i] instanceof CrushingBlock) {
                  writer.print(tileChars[k] + solid + "3" + ((Solid)map[j][i]).getColour());
                }
                if(map[j][i] instanceof BrokenBlock) {
                  writer.print(tileChars[k] + solid + "2" + ((Solid)map[j][i]).getColour());
                }
                if(map[j][i] instanceof Goal) {
                  writer.print(tileChars[k] + solid + "2" + ((Goal)map[j][i]).getColour());
                }
                else {
                  writer.print(tileChars[k] + solid + "0" + ((Solid)map[j][i]).getColour());
                }
              }
            }
          }
          writer.print(" ");
        }
        writer.println("");
      }
      writer.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    } catch (UnsupportedEncodingException e2) {
      System.out.println("Unsupported encoding");
    }
  }
}