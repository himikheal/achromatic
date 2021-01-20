import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;

class CrusherRayCastCallback implements RayCastCallback{

  private Fixture collided;
  private Fixture bound;

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

  public Fixture getBound(){
    return this.bound;
  }

  public Fixture getCollided(){
    return this.collided;
  }

  public void setCollided(Fixture collided){
    this.collided = collided;
  }
}
