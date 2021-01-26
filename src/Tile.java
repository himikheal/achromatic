import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Tile
 * the big abstract class encompassing all tiles
 */
abstract class Tile {
  private Point point;
  private Sprite sprite;
  private Body body;

  /**
   * getPoint
   * returns point of tile
   * @return this.point the point
   */
  public Point getPoint() {
    return this.point;
  }

  /**
   * setPoint
   * sets the point of the tile
   * @param point new point
   */
  public void setPoint(Point point) {
    this.point = point;
  }

  /**
   * getSprite
   * gets the sprite of the tile
   * @return this.sprite the sprite
   */
  public Sprite getSprite() {
    return this.sprite;
  }

  /**
   * setSprite
   * sets the sprite of the tile
   * @param sprite the new sprite
   */
  public void setSprite(Sprite sprite) {
    this.sprite = sprite;
  }

  /**
   * getBody
   * gets the body of the tile
   * @return this.body the body
   */
  public Body getBody() {
    return this.body;
  }

  /**
   * setBody
   * sets the body of the tile
   * @param body the new body
   */
  public void setBody(Body body) {
    this.body = body;
  }
}