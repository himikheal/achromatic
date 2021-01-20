import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class BrokenBlock extends Solid {
  private float timer;
  private float elapsed = 0;
  private boolean activated = false;
  private boolean broken = false;

  BrokenBlock(Point point, Sprite sprite, Body body, int time) {
    super(point, sprite, body);
    this.timer = time;
  }

  public float getTimer() {
    return this.timer;
  }

  public void setTimer(float time) {
    this.timer = time;
  }

  public float getElapsed() {
    return this.elapsed;
  }

  public void setElapsed(float time) {
    this.elapsed = time;
  }

  public boolean getBroken(){
    return this.broken;
  }

  public void setBroken(boolean broken){
    this.broken = broken;
  }

  public boolean getActive(){
    return this.activated;
  }

  public void setActive(boolean activated){
    this.activated = activated;
  }
}