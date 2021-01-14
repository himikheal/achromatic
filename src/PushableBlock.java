import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class PushableBlock extends Block {

  PushableBlock(Point point, Sprite sprite, Body body, int speed) {
    super(point, sprite, body, speed);
  }
}