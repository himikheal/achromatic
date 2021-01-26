import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * SlipperyBlock
 * slippery subclass of tile, lower friction coefficient
 */
class SlipperyBlock extends Solid {

  /**
   * SlipperyBlock
   * constructor
   * @param point index of slippery block
   * @param sprite sprite of slippery block
   * @param body body of slippery block
   */
  SlipperyBlock(Point point, Sprite sprite, Body body) {
    super(point, sprite, body);
  }
}