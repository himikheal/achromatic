import java.awt.Point;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import javafx.collections.SetChangeListener;

import com.badlogic.gdx.physics.box2d.Fixture;

class CrushingBlock extends Block implements CrusherAI {
  private Vector2 p1;
  private Vector2 p2;
  private Vector2 collide;
  private Fixture bound;
  private Fixture collided;
  private Vector2 initial;
  private World gameWorld;
  private boolean aggro = false;
  private boolean reset = false;
  private boolean moving = false;
  private boolean cooldown = false;
  private CrusherRayCastCallback callback;

  CrushingBlock(Point point, Sprite sprite, Body body, int speed, World gameWorld) {
    super(point, sprite, body, speed);
    this.gameWorld = gameWorld;
    initial = new Vector2(body.getPosition());
    float x = body.getPosition().x;
    float y = body.getPosition().y;
    p1 = new Vector2(x, y);
    p2 = new Vector2(x, y - 100);
    callback = new CrusherRayCastCallback();
  }

  public void update(){
    gameWorld.rayCast(callback, p1, p2);
    this.bound = callback.getBound();
    if(callback.getCollided() != null && callback.getCollided().getFilterData().categoryBits == 0x0002 && this.getBody().getType() == BodyType.KinematicBody){
      aggro = true;
      moving = true;
      this.collided = callback.getCollided();
      callback.setCollided(null);
    }
  }

  public void move() {
    if(aggro){
      if(this.getBody().getPosition().y > collided.getBody().getPosition().y && !cooldown){
        this.getBody().setLinearVelocity(0, -this.getSpeed());
      }else{
        cooldown = true;
        aggro = false;
        reset = true;
      }
    }else if(reset){
      if(this.getBody().getPosition().y < initial.y){
        this.getBody().setLinearVelocity(0, 1);
      }else{
        this.getBody().setLinearVelocity(0, 0);
        this.getBody().setTransform(initial.x, initial.y, 0f);
        reset = false;
        moving = false;
        cooldown = false;
      }
    }
  }

  public boolean isMoving(){
    return this.moving;
  }

  public boolean isAggro(){
    return this.aggro;
  }

  public void setMoving(boolean moving){
    this.moving = moving;
  }

  public Vector2 getP1(){
    return this.p1;
  }

  public void setP1(Vector2 p1){
    this.p1 = p1;
  }

  public Vector2 getP2(){
    return this.p2;
  }

  public void setP2(Vector2 p2){
    this.p2 = p2;
  }

  public Vector2 getCollide(){
    return this.collide;
  }

  public void setCollide(Vector2 collide){
    this.collide = collide;
  }

  public Vector2 getBound(){
    return this.bound.getBody().getPosition();
  }

  public void setBound(Fixture bound){
    this.bound = bound;
  }

}