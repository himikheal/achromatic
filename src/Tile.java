import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

abstract class Tile {
  private Point point;
  private Sprite sprite;
  private Body body;

  public Point getPoint() {
    return this.point;
  }

  public void setPoint(Point point) {
    this.point = point;
  }

  public Sprite getSprite() {
    return this.sprite;
  }

  public void setSprite(Sprite sprite) {
    this.sprite = sprite;
  }

  public Body getBody() {
    return this.body;
  }

  public void setBody(Body body) {
    this.body = body;
  }
  
}