import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

class Solid extends Tile{
  private boolean coloured;
  private String colour;
  private boolean toggled;
  private boolean harmful;

  Solid(Point point, Sprite sprite, Body body) {
    super(point, sprite, body);
  }

  public boolean checkColour(String colour) {
    return this.colour.equals(colour);
  }

  public boolean isHarmful() {
    return this.harmful;
  }
  
  public void setHarmful(boolean harmful) {
    this.harmful = harmful;
  }
}