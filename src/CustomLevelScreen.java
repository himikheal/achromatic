import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class CustomLevelScreen extends ScreenAdapter {

  ColourGame game;
  ShapeRenderer sr;

  public CustomLevelScreen(ColourGame game) {
    this.game = game;
  }

  @Override
  public void show() {
    sr = new ShapeRenderer();
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

  @Override
  public void hide() {
    
  }
}