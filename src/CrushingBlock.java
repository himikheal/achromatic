import java.awt.Point;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * CrushingBlock
 * block that turns into an enemy and falls on you crushing you
 */
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

  /**
   * CrushingBlock
   * constructor for crusher
   * @param point point of crusher
   * @param sprite sprite of crusher
   * @param body body of crusher
   * @param speed speed of falling
   * @param gameWorld world crusher is in
   */
  CrushingBlock(Point point, Sprite sprite, Body body, int speed, World gameWorld) {
    super(point, sprite, body, speed);
    this.gameWorld = gameWorld;
    if(body != null){
      initial = new Vector2(body.getPosition());
      float x = body.getPosition().x;
      float y = body.getPosition().y;
      p1 = new Vector2(x, y);
      p2 = new Vector2(x, y - 100);
    }
    callback = new CrusherRayCastCallback();
  }

  /**
   * update
   * method for raycast callback for checking if player is under crusher
   */
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

  /**
   * move
   * called to update movement
   */
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

  /**
   * isMoving
   * returns if its moving
   * @return this.moving if its moving
   */
  public boolean isMoving(){
    return this.moving;
  }

  /**
   * isAggro
   * returns if its aggro
   * @return this.aggro if its aggro
   */
  public boolean isAggro(){
    return this.aggro;
  }

  /**
   * setMoving
   * sets moving boolean
   * @param moving new moving boolean
   */
  public void setMoving(boolean moving){
    this.moving = moving;
  }

  /**
   * getP1
   * returns p1
   * @return the p1 of the block
   */
  public Vector2 getP1(){
    return this.p1;
  }

  /**
   * setP1
   * sets p1 value
   * @param p1 new value
   */
  public void setP1(Vector2 p1){
    this.p1 = p1;
  }

  /**
   * getP2
   * returns p2
   * @return the p2 of the block
   */
  public Vector2 getP2(){
    return this.p2;
  }

  /**
   * setP2
   * sets p2 value
   * @param p2 new value
   */
  public void setP2(Vector2 p2){
    this.p2 = p2;
  }

  /**
   * getCollide
   * returns if collided
   * @return this.collide if it collided
   */
  public Vector2 getCollide(){
    return this.collide;
  }

  /**
   * setCollide
   * sets if its collided
   * @param collide new collide value
   */
  public void setCollide(Vector2 collide){
    this.collide = collide;
  }

  /**
   * getBound
   * gets the boundary at which it will collide
   * @return this.bound.getBody().getPosition();
   */
  public Vector2 getBound(){
    return this.bound.getBody().getPosition();
  }

  /**
   * setBound
   * sets bound value
   * @param bound new value
   */
  public void setBound(Fixture bound){
    this.bound = bound;
  }

}