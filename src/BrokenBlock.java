import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * BrokenBlock
 * extends normal ground, breaks after a timer once touched
 */
class BrokenBlock extends Solid {
  private float timer;
  private float elapsed = 0;
  private boolean activated = false;
  private boolean broken = false;

  /**
   * BrokenBlock
   * constructor for broken blocks
   * @param point point in map array
   * @param sprite sprite connected to the block
   * @param body body of the block in the physics world
   * @param time amount of time in seconds before a block breaks
   */
  BrokenBlock(Point point, Sprite sprite, Body body, int time) {
    super(point, sprite, body);
    this.timer = time;
  }

  /**
   * getTimer
   * returns the amount of time before breaking
   * @return this.timer the amount of time
   */
  public float getTimer() {
    return this.timer;
  }

  /**
   * setTimer
   * sets time limit
   * @param time new time limit
   */
  public void setTimer(float time) {
    this.timer = time;
  }
  /**
   * getElapsed
   * returns time elapsed since timer was started
   * @return this.elapsed the elapsed time
   */
  public float getElapsed() {
    return this.elapsed;
  }
  /**
   * setElapsed
   * method to reset elapsed, to affect other values
   * @param time new elapsed
   */
  public void setElapsed(float time) {
    this.elapsed = time;
  }

  /**
   * getBroken
   * returns the state of the block
   * @return this.broken if it's broken
   */
  public boolean getBroken(){
    return this.broken;
  }

  /**
   * setBroken
   * sets the state of the block, used for reset
   * @param broken state of the block
   */
  public void setBroken(boolean broken){
    this.broken = broken;
  }

  /**
   * getActive
   * returns whether the block was touched
   * @return this.activated if the block was touched
   */
  public boolean getActive(){
    return this.activated;
  }

  /**
   * setActive
   * set whether the block was touched
   * @param activated if the block was touched
   */
  public void setActive(boolean activated){
    this.activated = activated;
  }
}