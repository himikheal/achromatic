import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LevelScreen extends ScreenAdapter {

  ColourGame game;

  private Texture levelOne = new Texture("assets/sprites/Idle 1.png");
  private Texture levelTwo = new Texture("assets/sprites/Idle 2.png");
  private Texture levelThree = new Texture("assets/sprites/Idle 3.png");
  private Texture levelFour = new Texture("assets/sprites/Idle 4.png");
  private Texture levelFive = new Texture("assets/sprites/Idle 5.png");
  private Texture levelOneDown = new Texture("assets/sprites/Pressed 1.png");
  private Texture levelTwoDown = new Texture("assets/sprites/Pressed 2.png");
  private Texture levelThreeDown = new Texture("assets/sprites/Pressed 3.png");
  private Texture levelFourDown = new Texture("assets/sprites/Pressed 4.png");
  private Texture levelFiveDown = new Texture("assets/sprites/Pressed 5.png");
  private Sprite levelOneSprite = new Sprite(levelOne);
  private Sprite levelTwoSprite = new Sprite(levelTwo);
  private Sprite levelThreeSprite = new Sprite(levelThree);
  private Sprite levelFourSprite = new Sprite(levelFour);
  private Sprite levelFiveSprite = new Sprite(levelFive);
  private Sprite levelOneDownSprite = new Sprite(levelOneDown);
  private Sprite levelTwoDownSprite = new Sprite(levelTwoDown);
  private Sprite levelThreeDownSprite = new Sprite(levelThreeDown);
  private Sprite levelFourDownSprite = new Sprite(levelFourDown);
  private Sprite levelFiveDownSprite = new Sprite(levelFiveDown);


  public LevelScreen(ColourGame game) {
    this.game = game;
  }

  @Override
  public void show() {
    levelOneSprite.setSize(64, 64);
    levelTwoSprite.setSize(64, 64);
    levelThreeSprite.setSize(64, 64);
    levelFourSprite.setSize(64, 64);
    levelFiveSprite.setSize(64, 64);
    levelOneDownSprite.setSize(64, 64);
    levelTwoDownSprite.setSize(64, 64);
    levelThreeDownSprite.setSize(64, 64);
    levelFourDownSprite.setSize(64, 64);
    levelFiveDownSprite.setSize(64, 64);
    levelOneSprite.setPosition(Gdx.graphics.getWidth() / 2 - levelOneSprite.getWidth() / 2 - 400, Gdx.graphics.getHeight() / 2);
    levelTwoSprite.setPosition(Gdx.graphics.getWidth() / 2 - levelTwoSprite.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2);
    levelThreeSprite.setPosition(Gdx.graphics.getWidth() / 2 - levelThreeSprite.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    levelFourSprite.setPosition(Gdx.graphics.getWidth() / 2 - levelFourSprite.getWidth() / 2 + 200, Gdx.graphics.getHeight() / 2);
    levelFiveSprite.setPosition(Gdx.graphics.getWidth() / 2 - levelFiveSprite.getWidth() / 2 + 400, Gdx.graphics.getHeight() / 2);
    levelOneDownSprite.setPosition(Gdx.graphics.getWidth() / 2 - levelOneSprite.getWidth() / 2 - 400, Gdx.graphics.getHeight() / 2);
    levelTwoDownSprite.setPosition(Gdx.graphics.getWidth() / 2 - levelTwoSprite.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2);
    levelThreeDownSprite.setPosition(Gdx.graphics.getWidth() / 2 - levelThreeSprite.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    levelFourDownSprite.setPosition(Gdx.graphics.getWidth() / 2 - levelFourSprite.getWidth() / 2 + 200, Gdx.graphics.getHeight() / 2);
    levelFiveDownSprite.setPosition(Gdx.graphics.getWidth() / 2 - levelFiveSprite.getWidth() / 2 + 400, Gdx.graphics.getHeight() / 2);

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    game.batch.begin();
    if(Gdx.input.getX() > levelOneSprite.getX()
    && Gdx.input.getX() < levelOneSprite.getX() + levelOneSprite.getWidth()
    && Gdx.input.getY() > levelOneSprite.getY() - levelOneSprite.getHeight()
    && Gdx.input.getY() < levelOneSprite.getY()) {
      if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
        game.setScreen(new GameScreen(game, "level_1.txt"));
        this.dispose();
      }
      else {
        levelOneDownSprite.draw(game.batch);
      }
    }
    else {
      levelOneSprite.draw(game.batch);
    }
    if(Gdx.input.getX() > levelTwoSprite.getX()
    && Gdx.input.getX() < levelTwoSprite.getX() + levelTwoSprite.getWidth()
    && Gdx.input.getY() > levelTwoSprite.getY() - levelTwoSprite.getHeight()
    && Gdx.input.getY() < levelTwoSprite.getY()) {
      if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
        game.setScreen(new GameScreen(game, "level_2.txt"));
      }
      else {
        levelTwoDownSprite.draw(game.batch);
      }
    }
    else {
      levelTwoSprite.draw(game.batch);
    }
    if(Gdx.input.getX() > levelThreeSprite.getX()
    && Gdx.input.getX() < levelThreeSprite.getX() + levelThreeSprite.getWidth()
    && Gdx.input.getY() > levelThreeSprite.getY() - levelThreeSprite.getHeight()
    && Gdx.input.getY() < levelThreeSprite.getY()) {
      if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
        game.setScreen(new GameScreen(game, "level_3.txt"));
      }
      else {
        levelThreeDownSprite.draw(game.batch);
      }
    }
    else {
      levelThreeSprite.draw(game.batch);
    }
    if(Gdx.input.getX() > levelFourSprite.getX()
    && Gdx.input.getX() < levelFourSprite.getX() + levelFourSprite.getWidth()
    && Gdx.input.getY() > levelFourSprite.getY() - levelFourSprite.getHeight()
    && Gdx.input.getY() < levelFourSprite.getY()) {
      if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
        game.setScreen(new GameScreen(game, "level_4.txt"));
      }
      else {
        levelFourDownSprite.draw(game.batch);
      }
    }
    else {
      levelFourSprite.draw(game.batch);
    }
    if(Gdx.input.getX() > levelFiveSprite.getX()
    && Gdx.input.getX() < levelFiveSprite.getX() + levelFiveSprite.getWidth()
    && Gdx.input.getY() > levelFiveSprite.getY() - levelFiveSprite.getHeight()
    && Gdx.input.getY() < levelFiveSprite.getY()) {
      if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
        game.setScreen(new GameScreen(game, "level_5.txt"));
      }
      else {
        levelFiveDownSprite.draw(game.batch);
      }
    }
    else {
      levelFiveSprite.draw(game.batch);
    }
    game.batch.end();
  }

  @Override
  public void hide() {
    Gdx.input.setInputProcessor(null);
  }
}