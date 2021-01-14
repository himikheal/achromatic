import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class Air extends Tile {

  Air(Point point, Sprite sprite, Body body) {
    this.setPoint(point);
    this.setSprite(sprite);
    this.setBody(body);
  }
}