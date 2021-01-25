import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class Walker extends Enemy implements WalkerAI {
private int[] patrol;
private float initX;
boolean direction = true;

  Walker(Point point, Sprite sprite, Body body, int[] patrol) {
    super(point, sprite, body, 1);
    this.patrol = patrol;
  }

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

  public void setInit(){
    initX = this.getBody().getPosition().x;
  }

  public int[] getPatrol(){
    return this.patrol;
  }

  public void setPatrol(int[] patrol){
    this.patrol = patrol;
  }

  public boolean getDirection(){
    return this.direction;
  }

  public void setDirection(boolean direction){
    this.direction = direction;
  }

}