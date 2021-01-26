import com.badlogic.gdx.Game;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

public class ColourGame extends Game { //implements ApplicationListener, ContactListener{
	SpriteBatch batch;
  ShapeRenderer shapeRenderer;
	BitmapFont font;
	Music background;

	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		setScreen(new TitleScreen(this));
		background = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/background.mp3"));
		background.setVolume(0.2f);
		background.setLooping(true);
		background.play();
	}

	public void dispose () {
		batch.dispose();
    shapeRenderer.dispose();
    font.dispose();
	}

}