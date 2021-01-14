import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class MovingBlock extends Moving implements FloaterAI {
  private Point[] points;

  MovingBlock(Point point, Sprite sprite, Body body, int speed, Point[] points) {
    super(point, sprite, body, speed);
    this.points = points;
  }
  
  public void move(Point[] points) {

  }
}