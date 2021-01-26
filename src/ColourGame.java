import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

/**
 * ColourGame
 * Custom class to hold libgdx game object
 * holds spritebatch reference to draw all sprites across program
 * Music for background, lovely Lena Raine's composition played throughout the game
 */
public class ColourGame extends Game { 
	SpriteBatch batch;
  private ShapeRenderer shapeRenderer;
	private BitmapFont font;
	private Music background;

	/**
	 * create
	 * called on creation of a ColourGame instance
	 * initialization of all class variables, as well as music settings such as volume and looping
	 * calls setScreen method, creates new title screen
	 */
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		setScreen(new TitleScreen(this));
		background = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/background.mp3"));
		background.setVolume(0.1f);
		background.setLooping(true);
		background.play();
	}

	/**
	 * dispose
	 * method to dispose all of the class variables and then itself
	 */
	public void dispose () {
		batch.dispose();
    shapeRenderer.dispose();
    font.dispose();
	}
}