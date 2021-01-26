import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Block
 * superclass for multiple moving subclasses
 */
class Block extends Moving {

  /**
   * Block
   * constructor
   * @param point point of the block
   * @param sprite sprite of the block
   * @param body body of the block
   * @param speed speed of the block
   */
  Block(Point point, Sprite sprite, Body body, int speed) {
    super(point, sprite, body, speed);
  }
}