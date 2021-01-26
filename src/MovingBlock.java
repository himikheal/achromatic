import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * MovingBlock
 * platform that moves between given points
 */
class MovingBlock extends Moving implements FloaterAI {
  private int[] points;
  private float initX, initY;
  private boolean direction; //true for right/up, false for left/down
  private boolean axis; //true for horizontal, false for vertical

  /**
   * MovingBlock
   * constructor
   * @param point point of the moving block
   * @param sprite sprite of the moving block
   * @param body body of the moving block
   * @param points patrol points of the moving block
   */
  MovingBlock(Point point, Sprite sprite, Body body, int[] points) {
    super(point, sprite, body, 1);
    this.points = points;
  }
  
  /**
   * move
   * method called periodically to update position of platform
   */
  public void move(int[] points) {
    if(axis){
      if(direction){
        if(this.getBody().getPosition().x < initX + points[1]){
          this.getBody().setLinearVelocity(2, 0);
        }else{
          direction = false;
        }
      }else{
        if(this.getBody().getPosition().x > initX - points[0]){
          this.getBody().setLinearVelocity(-2, 0);
        }else{
          direction = true;
        }
      }
    }else{
      if(direction){
        if(this.getBody().getPosition().y < initY + points[1]){
          this.getBody().setLinearVelocity(0, 2);
        }else{
          direction = false;
        }
      }else{
        if(this.getBody().getPosition().y > initY - points[0]){
          this.getBody().setLinearVelocity(0, -2);
        }else{
          direction = true;
        }
      }
    }
  }

  /**
   * setInit
   * sets the initial positions for reference
   */
  public void setInit(){
    initX = this.getBody().getPosition().x;
    initY = this.getBody().getPosition().y;
  }

  /**
   * getPoints
   * returns the patrol points of the platform
   * @return this.points the patrol points
   */
  public int[] getPoints(){
    return this.points;
  }

  /**
   * setPoints
   * sets the patrol points
   * @param points the new patrol points
   */
  public void setPoints(int[] points){
    this.points = points;
  }

  /**
   * getDirection
   * gets the direction of the movement currently
   * @return this.direction the current direction
   */
  public boolean getDirection(){
    return this.direction;
  }

  /**
   * setDirection 
   * set the current moving direction
   * @param direction the new direction
   */
  public void setDirection(boolean direction){
    this.direction = direction;
  }

  /**
   * getAxis
   * gets the axis of movement
   * @return this.axis the axis
   */
  public boolean getAxis(){
    return this.axis;
  }

  /**
   * setAxis
   * sets the axis of movement
   * @param axis the new axis
   */
  public void setAxis(boolean axis){
    this.axis = axis;
  }
}