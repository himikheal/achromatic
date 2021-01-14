import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class CrushingBlock extends Block implements CrusherAI {
  private Point start;
  private Point end;

  CrushingBlock(Point point, Sprite sprite, Body body, int speed, Point start, Point end) {
    super(point, sprite, body, speed);
    this.start = start;
    this.end = end;
  }

  public void move(Point start, Point end) {

  }
}