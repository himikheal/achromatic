import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Walker
 * walking enemy, moves back and forth a given distance
 */
class Walker extends Enemy implements WalkerAI {
private int[] patrol;
private float initX;
private boolean direction = true;

  /**
   * Walker
   * walker constructor
   * @param point point of the walker
   * @param sprite sprite of the walker
   * @param body body of the walker
   * @param patrol patrol points of the walker
   */
  Walker(Point point, Sprite sprite, Body body, int[] patrol) {
    super(point, sprite, body, 1);
    this.patrol = patrol;
  }

  /**
   * move
   * called to update movement
   */
  public void move() {
    if(direction){
      if(this.getBody().getPosition().x < initX + patrol[1]){
        this.getBody().setLinearVelocity(1, 0);
      }else{
        direction = false;
      }
    }else{
      if(this.getBody().getPosition().x > initX - patrol[0]){
        this.getBody().setLinearVelocity(-1, 0);
      }else{
        direction = true;
      }
    }
  }

  /**
   * setInit
   * sets the initial position for reference
   */
  public void setInit(){
    initX = this.getBody().getPosition().x;
  }

  /**
   * getPatrol
   * returns patrol points
   * @return this.patrol the patrol points
   */
  public int[] getPatrol(){
    return this.patrol;
  }

  /**
   * setPatrol
   * sets the patrol points
   * @param patrol new patrol points
   */
  public void setPatrol(int[] patrol){
    this.patrol = patrol;
  }

  /**
   * getDirection
   * gets the direction
   * @return this.direction the new direction
   */
  public boolean getDirection(){
    return this.direction;
  }

  /**
   * setDirection
   * sets the direction
   * @param direction the new direction
   */
  public void setDirection(boolean direction){
    this.direction = direction;
  }
}