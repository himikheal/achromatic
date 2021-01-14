import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class Floater extends Enemy implements FloaterAI {
  private Point[] points;

  Floater(Point point, Sprite sprite, Body body, int speed) {
    super(point, sprite, body, speed);
  }

  public void move(Point[] points) {
    
  }
}