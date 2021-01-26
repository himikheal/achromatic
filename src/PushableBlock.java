import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * PushableBlock
 * a pushable tile
 */
class PushableBlock extends Block {

  /**
   * PushableBlock
   * constructor
   * @param point point of the pushable block
   * @param sprite sprite of the pushable block
   * @param body body of the pushable block
   * @param speed speed of the pushable block
   */
  PushableBlock(Point point, Sprite sprite, Body body, int speed) {
    super(point, sprite, body, speed);
  }
}