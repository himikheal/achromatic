import java.util.ArrayList;
import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Player
 * player object to interact with physics world, held outside of the map
 */
class Player {
  private Checkpoint spawn;
  private ArrayList<Integer> colours = new ArrayList<Integer>();
  private Point point;
  private Sprite sprite;
  private Body body;

  /**
   * Player
   * constructor for the player
   * @param spawn spawnpoint of the player
   * @param point point of the player
   * @param sprite sprite of the player
   * @param body physics body of the player
   */
  Player(Checkpoint spawn, Point point, Sprite sprite, Body body) {
    this.spawn = spawn;
    this.point = point;
    this.sprite = sprite;
    this.body = body;
  }

  /**
   * respawn
   * method to set the player back to the spawnpoint
   */
  public void respawn() {
    this.body.setTransform(this.spawn.getBody().getPosition().x, this.spawn.getBody().getPosition().y, 0f);
  }

  /**
   * clearColour
   * clears colours of the player
   */
  public void clearColour() {
    this.colours.clear();
  }

  /**
   * addColour
   * adds specified colour to player
   * @param colour wanted colour
   */
  public void addColour(int colour) {
    this.colours.add(colour);
  }

  /**
   * getColours
   * returns all colours player holds
   * @return this.colours list of all colours
   */
  public ArrayList<Integer> getColours() {
    return this.colours;
  }

  /**
   * getPoint
   * returns point of the player
   * @return this.point point of the player
   */
  public Point getPoint() {
    return this.point;
  }

  /**
   * setPoint
   * sets point of the player
   * @param point wanted point
   */
  public void setPoint(Point point) {
    this.point = point;
  }

  /**
   * getSprite
   * returns the sprite of the player
   * @return this.sprite sprite of the player
   */
  public Sprite getSprite() {
    return this.sprite;
  }

  /**
   * setSprite
   * sets sprite of the player
   * @param sprite wanted sprite
   */
  public void setSprite(Sprite sprite) {
    this.sprite = sprite;
  }

  /**
   * setSpawn
   * sets spawn of the player
   * @param spawn wanted spawn
   */
  public void setSpawn(Checkpoint spawn){
    this.spawn = spawn;
  }

  /**
   * getSpawn
   * returns the spawn of the player
   * @return this.spawn spawn of the player
   */
  public Checkpoint getSpawn(){
    return this.spawn;
  }

  /**
   * getBody
   * returns the physics body of the player
   * @return this.body player body
   */
  public Body getBody() {
    return this.body;
  }

  /**
   * setBody
   * sets body of the player
   * @param body new body
   */
  public void setBody(Body body) {
    this.body = body;
  }
}