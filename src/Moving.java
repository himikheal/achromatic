import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class Moving extends Solid {
  private int speed;

  Moving(Point point, Sprite sprite, Body body, int speed) {
    super(point, sprite, body);
    this.speed = speed;
  }

  public int getSpeed() {
    return this.speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }
}