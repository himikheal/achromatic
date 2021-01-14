import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class Sticker extends Enemy implements StickerAI {
  private int direction;
  
  Sticker(Point point, Sprite sprite, Body body, int speed, int direction) {
    super(point, sprite, body, speed);
    this.direction = direction;
  }

  public void move(int direction) {
    
  }
}