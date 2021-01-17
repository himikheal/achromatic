import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class ColourGiver extends Air {
  private int colour;

  ColourGiver(Point point, Sprite sprite, Body body, int colour) {
    super(point, sprite, body);
    this.colour = colour;
  }

  public int getColour() {
    return this.colour;
  }

  public void setColour(int colour) {
    this.colour = colour;
  }
}