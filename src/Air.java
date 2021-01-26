import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Air
 * superclass for things like checkpoints and givers, passable tiles
 */
class Air extends Tile {

  /**
   * Air
   * constructor
   * @param point point of the air tile
   * @param sprite sprite of the air tile
   * @param body body of the air tile
   */
  Air(Point point, Sprite sprite, Body body) {
    this.setPoint(point);
    this.setSprite(sprite);
    this.setBody(body);
  }
}