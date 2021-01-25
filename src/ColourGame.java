import com.badlogic.gdx.Game;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ColourGame extends Game { //implements ApplicationListener, ContactListener{
	SpriteBatch batch;
  ShapeRenderer shapeRenderer;
  BitmapFont font;

	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		setScreen(new TitleScreen(this));
	}

	public void dispose () {
		batch.dispose();
    shapeRenderer.dispose();
    font.dispose();
	}

}