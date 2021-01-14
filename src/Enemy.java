import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class Enemy extends Moving {

  Enemy(Point point, Sprite sprite, Body body, int speed) {
    super(point, sprite, body, speed);
  }
}