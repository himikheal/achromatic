import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class ColourGameDesktop {
	public static void main (String[] args) {
		new LwjglApplication(new ColourGame(), "Colour Game", 1280, 720);
	}
}