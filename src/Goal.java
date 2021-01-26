import java.awt.Point;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Goal
 * the end goal of a level
 */
public class Goal extends Air{
  private int colour;
  private boolean coloured;

  /**
   * Goal
   * constructor
   * @param point point of the goal
   * @param sprite sprite of the goal
   * @param body body of the goal
   * @param colour colour of the goal
   */
  Goal(Point point, Sprite sprite, Body body, int colour) {
    super(point, sprite, body);
    this.colour = colour;
  }

  /**
   * checkColour
   * checks the goal's colour
   * @param colour passed colour
   * @return this.colour == colour if the colour matches
   */
  public boolean checkColour(int colour) {
    return this.colour == colour;
  }

  /**
   * getColour
   * gets the colour
   * @return this.colour the colour
   */
  public int getColour(){
    return this.colour;
  }

  /**
   * isColoured
   * returns if the tile is coloured
   * @return this.coloured if it is coloured
   */
  public boolean isColoured() {
    return this.coloured;
  }

  /**
   * setColour
   * sets colour to passed value
   * @param colour passed value
   */
  public void setColour(int colour){
    this.colour = colour;
  }

  /**
   * setColoured
   * sets if tile is coloured
   * @param coloured if tile is coloured
   */
  public void setColoured(boolean coloured){
    this.coloured = coloured;
  }
}
