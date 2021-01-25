import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class TitleScreen extends ScreenAdapter {
  ColourGame game;
  //private SpriteBatch batch;
  private Texture startButton = new Texture("assets/sprites/startButton.png");
  private Texture startButtonDown = new Texture("assets/sprites/startButtonDown.png");
  private Texture startButtonClicked = new Texture("assets/sprites/startButtonClicked.png");
  private Sprite startButtonSprite = new Sprite(startButton);
  private Sprite startButtonDownSprite = new Sprite(startButtonDown);
  private Sprite startButtonClickedSprite = new Sprite(startButtonClicked);
  

  public TitleScreen(ColourGame game) {
    this.game = game;
  }

  @Override
  public void show() {
    startButtonSprite.setSize(256, 101);
    startButtonDownSprite.setSize(256, 101);
    startButtonClickedSprite.setSize(256, 101);
    startButtonSprite.setPosition(Gdx.graphics.getWidth()/2 - startButtonSprite.getWidth()/2, Gdx.graphics.getHeight()/2 - startButtonSprite.getHeight()/2);
    startButtonDownSprite.setPosition(Gdx.graphics.getWidth()/2 - startButtonDownSprite.getWidth()/2, Gdx.graphics.getHeight()/2 - startButtonDownSprite.getHeight()/2);
    startButtonClickedSprite.setPosition(Gdx.graphics.getWidth()/2 - startButtonClickedSprite.getWidth()/2, Gdx.graphics.getHeight()/2 - startButtonClickedSprite.getHeight()/2);

    //Gdx.input.setInputProcessor(new InputAdapter() {
    //  @Override
    //  public boolean keyDown(int keyCode) {
    //    if (keyCode == Input.Keys.SPACE) {
    //      game.setScreen(new GameScreen(game));
    //    }
    //    return true;
    //  }
    //});

    /////if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) 
    /////&& Gdx.input.getX() > startButtonSprite.getX() 
    /////&& Gdx.input.getX() < startButtonSprite.getX() + startButtonSprite.getWidth() 
    /////&& Gdx.input.getY() < startButtonSprite.getY() + startButtonSprite.getHeight() 
    /////&& Gdx.input.getY() > startButtonSprite.getY()) {
    /////  System.out.println("CLOICKED");
    /////  game.setScreen(new GameScreen(game));
    /////}


  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    //if
    //if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
    //}
    //if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
    //}

    game.batch.begin();
    if(Gdx.input.isKeyJustPressed(Keys.Z)) {
      game.setScreen(new EditorScreen(game));
    }
    if(Gdx.input.isKeyJustPressed(Keys.X)) {
      game.setScreen(new CustomLevelScreen(game));
    }
    if(Gdx.input.getX() > startButtonSprite.getX() 
    && Gdx.input.getX() < startButtonSprite.getX() + startButtonSprite.getWidth() 
    && Gdx.input.getY() < startButtonSprite.getY() + startButtonSprite.getHeight() 
    && Gdx.input.getY() > startButtonSprite.getY()) {
      if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
        startButtonClickedSprite.draw(game.batch);
        game.setScreen(new LevelScreen(game));
      }
      else {
        startButtonDownSprite.draw(game.batch);
      }
    }
    else {
      startButtonSprite.draw(game.batch);
    }
    game.font.draw(game.batch, "Title Screen!", Gdx.graphics.getWidth() * .5f, Gdx.graphics.getHeight() - 100);
    game.batch.end();
  }

  @Override
  public void hide() {
    Gdx.input.setInputProcessor(null);
  }
}