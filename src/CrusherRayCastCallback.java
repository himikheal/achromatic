import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * CrusherRayCastCallback
 * custom callback class for crusher block raycast
 */
class CrusherRayCastCallback implements RayCastCallback{

  private Fixture collided;
  private Fixture bound;

  /**
   * reportRayFixture
   * method that returns when the raycast is broken
   * @return 0f keep the ray casting
   * @return -1f stop the raycast
   */
  public float reportRayFixture(Fixture fix, Vector2 point, Vector2 normal, float fraction){
    if(fix.getFilterData().categoryBits == 0x0002){
      this.collided = fix;
      return 0f;
    }else if(fix.getFilterData().categoryBits == 0x0004){
      this.bound = fix;
      this.collided = null;
      return 0f;
    }else{
      return -1f;
    }
  }

  /**
   * getBound
   * get the fixture it collided with for the boundary
   * @return this.bound the boundary fixture
   */
  public Fixture getBound(){
    return this.bound;
  }

  /**
   * getCollided
   * gets the collided fixture
   * @return this.collided the collided fixture
   */
  public Fixture getCollided(){
    return this.collided;
  }

  /**
   * setCollided 
   * sets the collided fixture
   * @param collided new collided fixture
   */
  public void setCollided(Fixture collided){
    this.collided = collided;
  }
}
