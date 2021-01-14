import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class Solid extends Tile{
  private boolean coloured;
  private String colour;
  private boolean toggled;
  private boolean harmful;

  Solid(Point point, Sprite sprite, Body body) {
    this.setPoint(point);
    this.setSprite(sprite);
    this.setBody(body);
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