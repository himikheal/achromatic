import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class Solid extends Tile{
  private boolean coloured = false;
  private int colour;
  private boolean toggled;
  private boolean harmful;

  Solid(Point point, Sprite sprite, Body body) {
    this.setPoint(point);
    this.setSprite(sprite);
    this.setBody(body);
  }

  public boolean checkColour(int colour) {
    return this.colour == colour;
  }

  public int getColour(){
    return this.colour;
  }

  public boolean isColoured() {
    return this.coloured;
  }

  public void setColour(int colour){
    this.colour = colour;
  }

  public void setColoured(boolean coloured){
    this.coloured = coloured;
  }

  public boolean isHarmful() {
    return this.harmful;
  }
  
  public void setHarmful(boolean harmful) {
    this.harmful = harmful;
  }
}