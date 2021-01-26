import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Spawn
 * the spawnpoint of the player
 */
class Spawn extends Checkpoint {

  /**
   * Spawn
   * constructor
   * @param point point of the spawnpoint
   * @param sprite sprite of the spawnpoint
   * @param body body of the spawnpoint
   */
  Spawn(Point point, Sprite sprite, Body body) {
    super(point, sprite, body);
  }
}