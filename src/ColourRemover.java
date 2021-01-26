import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * ColourRemover
 * removes colour from the player
 */
class ColourRemover extends Air {

  /**
   * ColourRemover
   * constructor
   * @param point point of the remover
   * @param sprite sprite of the remover
   * @param body body of the remover
   */
  ColourRemover(Point point, Sprite sprite, Body body) {
    super(point, sprite, body);
  }
}