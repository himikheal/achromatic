import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Spikes
 * static enemy
 */
class Spikes extends Solid{
  
  /**
   * Spikes
   * constructor
   * @param point point of the spikes
   * @param sprite sprite of the spikes
   * @param body body of the spikes
   */
  Spikes(Point point, Sprite sprite, Body body) {
    super(point, sprite, body);
  }
}