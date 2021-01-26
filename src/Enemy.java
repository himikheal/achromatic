import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Enemy
 * main enemy superclass, acts as a parent for all the actual enemies
 */
class Enemy extends Moving {

  /**
   * Enemy
   * constructor
   * @param point point of the enemy
   * @param sprite sprite of the enemy
   * @param body body of the enemy
   * @param speed speed of the enemy
   */
  Enemy(Point point, Sprite sprite, Body body, int speed) {
    super(point, sprite, body, speed);
  }
}