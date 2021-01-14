import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class Spikes extends Solid{
  
  Spikes(Point point, Sprite sprite, Body body) {
    super(point, sprite, body);
  }
}