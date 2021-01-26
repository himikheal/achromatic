import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Solid
 * the main tile, used as walls and floor
 */
class Solid extends Tile{
  private boolean coloured = false;
  private int colour;
  private boolean toggled;
  private boolean harmful;

  /**
   * Solid
   * constructor
   * @param point point of the solid
   * @param sprite sprite of the solid
   * @param body body of the solid
   */
  Solid(Point point, Sprite sprite, Body body) {
    this.setPoint(point);
    this.setSprite(sprite);
    this.setBody(body);
  }

  /**
   * checkColour
   * compares colour passed to colour held
   * @param colour colour passed
   * @return this.colour == colour if they are the same
   */
  public boolean checkColour(int colour) {
    return this.colour == colour;
  }

  /**
   * getColour
   * returns the held colour
   * @return this.colour
   */
  public int getColour(){
    return this.colour;
  }

  /**
   * isColoured
   * returns if the tile is coloured
   * @return this.coloured if the tile is coloured
   */
  public boolean isColoured() {
    return this.coloured;
  }

  /**
   * setColour
   * sets the colour value
   * @param colour the new colour
   */
  public void setColour(int colour){
    this.colour = colour;
  }

  /**
   * setColoured
   * sets coloured boolean
   * @param coloured new boolean value
   */
  public void setColoured(boolean coloured){
    this.coloured = coloured;
  }

  /**
   * isHarmful
   * returns if the tile is harmful
   * @return this.harmful if its harmful
   */
  public boolean isHarmful() {
    return this.harmful;
  }
  
  /**
   * setHarmful
   * sets the harmful value
   * @param harmful the new value
   */
  public void setHarmful(boolean harmful) {
    this.harmful = harmful;
  }
}