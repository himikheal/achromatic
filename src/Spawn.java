import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class Spawn extends Checkpoint {

  Spawn(Point point, Sprite sprite, Body body) {
    super(point, sprite, body);
  }
}