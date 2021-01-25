import java.awt.Point;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public class Goal extends Air{
  private int colour;
  private boolean coloured;
  Goal(Point point, Sprite sprite, Body body, int colour) {
    super(point, sprite, body);
    this.colour = colour;
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
}
