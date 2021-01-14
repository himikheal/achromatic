import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class Bullet extends Enemy implements BulletAI {
  private int direction;

  Bullet(Point point, Sprite sprite, Body body, int speed, int direction) {
    super(point, sprite, body, speed);
    this.direction = direction;
  }

  public void move(int direction) {
    
  }
}