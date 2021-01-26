import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Moving
 * superclass for moving subclasses
 */
class Moving extends Solid {
  private int speed;

  /**
   * Moving
   * constructor
   * @param point point of the Moving tile
   * @param sprite sprite of the Moving tile
   * @param body body of the Moving tile
   * @param speed speed of the Moving tile
   */
  Moving(Point point, Sprite sprite, Body body, int speed) {
    super(point, sprite, body);
    this.speed = speed;
  }

  /**
   * getSpeed
   * returns the speed value of the mover
   * @return this.speed speed
   */
  public int getSpeed() {
    return this.speed;
  }

  /**
   * setSpeed
   * sets the speed value
   * @param speed the new value
   */
  public void setSpeed(int speed) {
    this.speed = speed;
  }
}