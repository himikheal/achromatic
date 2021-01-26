import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Checkpoint
 * checkpoint to set the players spawn
 */
class Checkpoint extends Air {
  
  /**
   * Checkpoint
   * constructor
   * @param point point of the checkpoint
   * @param sprite sprite of the checkpoint
   * @param body body of the checkpoint
   */
  Checkpoint(Point point, Sprite sprite, Body body) {
    super(point, sprite, body);
  }
}