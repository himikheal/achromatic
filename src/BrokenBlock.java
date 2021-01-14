import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class BrokenBlock extends Solid {
  private int timer;

  BrokenBlock(Point point, Sprite sprite, Body body, int time) {
    super(point, sprite, body);
    this.timer = time;
  }

  public int getTimer() {
    return this.timer;
  }

  public void setTimer(int time) {
    this.timer = time;
  }
}