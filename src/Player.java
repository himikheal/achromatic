import java.util.ArrayList;
import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class Player {
  private Checkpoint spawn;
  private ArrayList<Integer> colours = new ArrayList<Integer>();
  private Point point;
  private Sprite sprite;
  private Body body;

  Player(Checkpoint spawn, Point point, Sprite sprite, Body body) {
    this.spawn = spawn;
    this.point = point;
    this.sprite = sprite;
    this.body = body;
  }

  public void respawn() {

  }

  public void clearColour() {
    this.colours.clear();
  }

  public void addColour(int colour) {
    this.colours.add(colour);
  }

  public ArrayList<Integer> getColours() {
    return this.colours;
  }

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