import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import java.awt.Point;
class Floater extends Enemy implements FloaterAI {
  private Vector2[] points;

  Floater(Point point, Sprite sprite, Body body, int speed) {
    super(point, sprite, body, speed);
  }

  public void move(Vector2[] points) {
    
  }
}