import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * ColourGiver
 * object to give colour to the player upon collision
 * holds a colour value defined by an int
 */
class ColourGiver extends Air {
  private int colour;

  /**
   * ColourGiver
   * constructor which calls superconstructor and defines colour
   * @param point used as point in super call, represents the index of the Giver in the map array
   * @param sprite used as sprite in super call, represents the sprite of the Giver
   * @param body body in super call, represents the box2d body of the Giver
   * @param colour colour held in the giver
   */
  ColourGiver(Point point, Sprite sprite, Body body, int colour) {
    super(point, sprite, body);
    this.colour = colour;
  }

  /**
   * getColour
   * method to return held colour
   * @return this.colour returns the colour held
   */
  public int getColour() {
    return this.colour;
  }

  /**
   * setColour
   * sets the colour of the current giver
   * @param colour the newly wanted colour
   */
  public void setColour(int colour) {
    this.colour = colour;
  }
}