import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class ShooterBlock extends Solid {
  private int firerate;
  private int speed;

  ShooterBlock(Point point, Sprite sprite, Body body, int firerate, int speed) {
    super(point, sprite, body);
    this.firerate = firerate;
    this.speed = speed;
  }

  public int getFirerate() {
    return this.firerate;
  }

  public void setFirerate(int firerate) {
    this.firerate = firerate;
  }

  public int getSpeed() {
    return this.speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }
}