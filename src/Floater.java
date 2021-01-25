import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import java.awt.Point;
class Floater extends Enemy implements FloaterAI {
  private int[] points;
  private float initX, initY;
  private boolean direction; //true for right/up, false for left/down
  private boolean axis; //true for horizontal, false for vertical

  Floater(Point point, Sprite sprite, Body body, int[] points) {
    super(point, sprite, body, 1);
    this.points = points;
  }

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

  public void setInit(){
    initX = this.getBody().getPosition().x;
    initY = this.getBody().getPosition().y;
  }

  public int[] getPoints(){
    return this.points;
  }

  public void setPoints(int[] points){
    this.points = points;
  }

  public boolean getDirection(){
    return this.direction;
  }

  public void setDirection(boolean direction){
    this.direction = direction;
  }

  public boolean getAxis(){
    return this.axis;
  }

  public void setAxis(boolean axis){
    this.axis = axis;
  }
}