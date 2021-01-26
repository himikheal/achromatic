import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * ColourGameDesktop
 * class to define java project as desktop application
 * contains main method, which creates a new ColourGame instance with given dimensions
 */
public class ColourGameDesktop {
	public static void main (String[] args) {
		new LwjglApplication(new ColourGame(), "Colour Game", 1440, 810);
	}
}