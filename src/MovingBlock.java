import java.awt.Point;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class MovingBlock extends Moving implements FloaterAI {
  private Vector2[] points;
  private boolean direction; //true for right/up, false for left/down
  private boolean axis; //true for horizontal, false for vertical

  MovingBlock(Point point, Sprite sprite, Body body, Vector2[] points) {
    super(point, sprite, body, 1);
    this.points = points;
  }
  
  public void move(Vector2[] points) {
    if(axis){
      if(direction){
        if(this.getBody().getPosition().x < points[1].x){
          this.getBody().setLinearVelocity(2, 0);
        }else{
          direction = false;
        }
      }else{
        if(this.getBody().getPosition().x > points[0].x){
          this.getBody().setLinearVelocity(-2, 0);
        }else{
          direction = true;
        }
      }
    }else{
      if(direction){
        if(this.getBody().getPosition().y < points[1].y){
          this.getBody().setLinearVelocity(0, 2);
        }else{
          direction = false;
        }
      }else{
        if(this.getBody().getPosition().y > points[0].y){
          this.getBody().setLinearVelocity(0, -2);
        }else{
          direction = true;
        }
      }
    }
  }

  public Vector2[] getPoints(){
    return this.points;
  }

  public void setPoints(Vector2[] points){
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