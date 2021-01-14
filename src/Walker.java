import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class Walker extends Enemy implements WalkerAI {
  private Point[] points = new Point[2];

  Walker(Point point, Sprite sprite, Body body, int speed) {
    super(point, sprite, body, speed);
  }

  Walker(Point p1, Point p2, Sprite sprite, Body body, int speed) {
    super(p1, sprite, body, speed);
    this.points[0] = p1;
    this.points[1] = p2;
  }

  public void move() {

  }

  public void move(Point p1, Point p2) {

  }
}