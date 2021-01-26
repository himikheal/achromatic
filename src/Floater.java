import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import java.awt.Point;

/**
 * Floater
 * floating enemy, moves back and forth a given distance
 */
class Floater extends Enemy implements FloaterAI {
  private int[] points;
  private float initX, initY;
  private boolean direction; //true for right/up, false for left/down
  private boolean axis; //true for horizontal, false for vertical

  /**
   * Floater
   * constructor for floater
   * @param point index of floater
   * @param sprite sprite of floater
   * @param body body of floater
   * @param points points floater moves between
   */
  Floater(Point point, Sprite sprite, Body body, int[] points) {
    super(point, sprite, body, 1);
    this.points = points;
  }

  /**
   * move
   * method called to update the position of the floater
   * @param points the points the floater moves between
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
   * sets the initial position for reference
   */
  public void setInit(){
    initX = this.getBody().getPosition().x;
    initY = this.getBody().getPosition().y;
  }

  /**
   * getPoints
   * returns patrol points
   * @return this.points the points of patrol
   */
  public int[] getPoints(){
    return this.points;
  }

  /**
   * setPoints
   * set the patrol points
   * @param points the patrol points
   */
  public void setPoints(int[] points){
    this.points = points;
  }

  /**
   * getDirection
   * returns the current direction as a boolean
   * @return this.direction the direction
   */
  public boolean getDirection(){
    return this.direction;
  }

  /**
   * setDirection
   * sets new direction
   * @param direction the new direction
   */
  public void setDirection(boolean direction){
    this.direction = direction;
  }

  /**
   * getAxis
   * returns if horizontal for vertical
   * @return this.axis the axis
   */
  public boolean getAxis(){
    return this.axis;
  }

  /**
   * setAxis
   * sets the axis of movement
   * @param axis the axis of movement
   */
  public void setAxis(boolean axis){
    this.axis = axis;
  }
}