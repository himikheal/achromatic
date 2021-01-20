import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class EditorScreen extends ScreenAdapter {

  ColourGame game;
  ShapeRenderer sr;
  Tile selected;
  Tile[][] map = new Tile[50][50];
  Texture box = new Texture("assets/sprites/boxSprite0_2.png");
  Texture boxR = new Texture("assets/sprites/boxSprite1_1.png");
  Texture boxO = new Texture("assets/sprites/boxSprite2_1.png");
  Texture boxY = new Texture("assets/sprites/boxSprite3_1.png");
  Texture boxG = new Texture("assets/sprites/boxSprite4_1.png");
  Texture boxB = new Texture("assets/sprites/boxSprite5_1.png");
  Texture boxP = new Texture("assets/sprites/boxSprite6_1.png");
  Texture checkpoint = new Texture("assets/sprites/checkSprite_1.png");
  Texture giverR = new Texture("assets/sprites/giverSprite_1.png");
  Texture giverO = new Texture("assets/sprites/giverSprite_2.png");
  Texture giverY = new Texture("assets/sprites/giverSprite_3.png");
  Texture giverG = new Texture("assets/sprites/giverSprite_4.png");
  Texture giverB = new Texture("assets/sprites/giverSprite_5.png");
  Texture giverP = new Texture("assets/sprites/giverSprite_6.png");
  Texture remover = new Texture("assets/sprites/removerSprite.png");
  Texture spawn = new Texture("assets/sprites/spawnSprite_1.png");
  Texture spikes = new Texture("assets/sprites/spikeSprite0_1.png");
  Texture spikesR = new Texture("assets/sprites/spikeSprite1_2.png");
  Texture spikesO = new Texture("assets/sprites/spikeSprite2_2.png");
  Texture spikesY = new Texture("assets/sprites/spikeSprite3_2.png");
  Texture spikesG = new Texture("assets/sprites/spikeSprite4_2.png");
  Texture spikesB = new Texture("assets/sprites/spikeSprite5_2.png");
  Texture spikesP = new Texture("assets/sprites/spikeSprite6_2.png");
  Texture tile = new Texture("assets/sprites/tileSprite0_1.png");
  Texture tileR = new Texture("assets/sprites/tileSprite1_2.png");
  Texture tileO = new Texture("assets/sprites/tileSprite2_2.png");
  Texture tileY = new Texture("assets/sprites/tileSprite3_2.png");
  Texture tileG = new Texture("assets/sprites/tileSprite4_2.png");
  Texture tileB = new Texture("assets/sprites/tileSprite5_2.png");
  Texture tileP = new Texture("assets/sprites/tileSprite6_2.png");
  Sprite boxSprite = new Sprite(box);
  Sprite checkpointSprite = new Sprite(checkpoint);
  Sprite giverSprite = new Sprite(giverR);
  Sprite removerSprite = new Sprite(remover);
  Sprite spawnSprite = new Sprite(spawn);
  Sprite spikesSprite = new Sprite(spikes);
  Sprite tileSprite = new Sprite(tile);
  int colour = 0;
  Texture[] boxTextures = new Texture[7];
  Texture[] giverTextures = new Texture[6];
  Texture[] spikesTextures = new Texture[7];
  Texture[] tileTextures = new Texture[7];
  boolean spawnPlaced = false;

  EditorScreen(ColourGame game) {
    this.game = game;
  }

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
    boxTextures[0] = box;
    boxTextures[1] = boxR;
    boxTextures[2] = boxO;
    boxTextures[3] = boxY;
    boxTextures[4] = boxG;
    boxTextures[5] = boxB;
    boxTextures[6] = boxP;
    giverTextures[0] = giverR;
    giverTextures[1] = giverO;
    giverTextures[2] = giverY;
    giverTextures[3] = giverG;
    giverTextures[4] = giverB;
    giverTextures[5] = giverP;
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
  }

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
    sr.rect(16, Gdx.graphics.getHeight() - 160, 64, 64);

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
        System.out.println("\nX: " + Gdx.input.getX());
        System.out.println("Y: " + Gdx.input.getY());  
        System.out.println("Grid X: " + (int)((Gdx.input.getX() - 630) / 16.2));
        System.out.println("Grid Y: " + (int)(Gdx.input.getY() / 16.2));
        int gridX = (int)((Gdx.input.getX() - 630) / 16.2);
        int gridY = (int)(Gdx.input.getY() / 16.2);
        if(!(selected instanceof Spawn) || (selected instanceof Spawn && !spawnPlaced)) {
          if(selected == null && map[gridX][gridY] instanceof Spawn) {
            spawnPlaced = false;
          }
          map[gridX][gridY] = selected;
          if(selected instanceof Spawn) {
            spawnPlaced = true;
          }
        }
        
      }
    }
    if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
      if(Gdx.input.getY() > 16 && Gdx.input.getY() < 80) {
        if(Gdx.input.getX() > 16
        && Gdx.input.getX() < 80) {
          selected = new PushableBlock(null, new Sprite(boxSprite.getTexture()), null, 1);
          System.out.println("TEST1");
        }
        else if(Gdx.input.getX() > 96
             && Gdx.input.getX() < 160) {
          selected = new Checkpoint(null, new Sprite(checkpointSprite.getTexture()), null);
          System.out.println("TEST2");
        }
        else if(Gdx.input.getX() > 176
             && Gdx.input.getX() < 240) {
          selected = new ColourGiver(null, new Sprite(giverSprite.getTexture()), null, this.colour);
          System.out.println("TEST3");
        }
        else if(Gdx.input.getX() > 256
             && Gdx.input.getX() < 320) {
          selected = new ColourRemover(null, new Sprite(removerSprite.getTexture()), null);
          System.out.println("TEST4");
        }
        else if(Gdx.input.getX() > 336
             && Gdx.input.getX() < 400) {
          selected = new Spawn(null, new Sprite(spawnSprite.getTexture()), null);
          System.out.println("TEST5");
        }
        else if(Gdx.input.getX() > 416
             && Gdx.input.getX() < 480) {
          selected = new Spikes(null, new Sprite(spikesSprite.getTexture()), null);
          System.out.println("TEST6");
        }
        else if(Gdx.input.getX() > 496
             && Gdx.input.getX() < 560) {
          selected = new Solid(null, new Sprite(tileSprite.getTexture()), null);
          System.out.println("TEST7");
        }
      }
      else if(Gdx.input.getY() > 96 && Gdx.input.getY() < 160) {
        if(Gdx.input.getX() > 16
        && Gdx.input.getX() < 80) {
          selected = null;
          System.out.println("CLEAR");
        }
      }
      if(Gdx.input.getX() > 600 && Gdx.input.getX() < 630) {
        if(Gdx.input.getY() > Gdx.graphics.getHeight() - 744 - 50
        && Gdx.input.getY() < Gdx.graphics.getHeight() - 744) {
          this.colour = 0;
          System.out.println("COLOUR0");
        }
        else if(Gdx.input.getY() > Gdx.graphics.getHeight() - 694 - 50
             && Gdx.input.getY() < Gdx.graphics.getHeight() - 694) {
          this.colour = 1;
          System.out.println("COLOUR1");
        }
        else if(Gdx.input.getY() > Gdx.graphics.getHeight() - 644 - 50
             && Gdx.input.getY() < Gdx.graphics.getHeight() - 644) {
          this.colour = 2;
          System.out.println("COLOUR2");
        }
        else if(Gdx.input.getY() > Gdx.graphics.getHeight() - 594 - 50
             && Gdx.input.getY() < Gdx.graphics.getHeight() - 594) {
          this.colour = 3;
          System.out.println("COLOUR3");
        }
        else if(Gdx.input.getY() > Gdx.graphics.getHeight() - 544 - 50
             && Gdx.input.getY() < Gdx.graphics.getHeight() - 544) {
          this.colour = 4;
          System.out.println("COLOUR4");
        }
        else if(Gdx.input.getY() > Gdx.graphics.getHeight() - 494 - 50
             && Gdx.input.getY() < Gdx.graphics.getHeight() - 494) {
          this.colour = 5;
          System.out.println("COLOUR5");
        }
        else if(Gdx.input.getY() > Gdx.graphics.getHeight() - 444 - 50
             && Gdx.input.getY() < Gdx.graphics.getHeight() - 444) {
          this.colour = 6;
          System.out.println("COLOUR6");
        }
        boxSprite.setTexture(boxTextures[colour]);
        if(colour != 0) {
          giverSprite.setTexture(giverTextures[colour - 1]);
        }
        spikesSprite.setTexture(spikesTextures[colour]);
        tileSprite.setTexture(tileTextures[colour]);
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

  @Override
  public void hide() {
    
  }
}