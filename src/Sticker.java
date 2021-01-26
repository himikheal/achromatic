import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class Sticker extends Enemy implements StickerAI {
  private int orientation;
  private boolean forward = true, below, beneath;
  private boolean clockwise = true;
  private int bodyX, bodyY, tempX, tempY;
  private Point index, front, down, under;

  
  Sticker(Point point, Sprite sprite, Body body, int speed, int orientation) {
    super(point, sprite, body, speed);
    this.orientation = orientation;
    if(point != null){
      index = new Point(this.getPoint().y, this.getPoint().x);
      front = new Point(this.getPoint().y, this.getPoint().x);
      down = new Point(this.getPoint().y, this.getPoint().x);
      under = new Point(this.getPoint().y, this.getPoint().x);
      updateIndeces();  
    }
    if(body != null){
      bodyX = Math.round(body.getPosition().x);
      bodyY = Math.round(body.getPosition().y);
      tempX = Math.round(bodyX + (front.x - index.x));
      tempY = Math.round(bodyY + (front.y - index.y));
      body.setTransform(bodyX, bodyY - 0.45f, 0f);
    }
  }

  public void move(Tile[][] levelMap) {
    if(clockwise){
      updateIndeces();

      try{
        Tile[] tiles = new Tile[3];
        tiles[0] = levelMap[index.y + (front.y - index.y)][index.x + (front.x - index.x)];
        tiles[1] = levelMap[index.y + (down.y - index.y)][index.x + (down.x - index.x)];
        tiles[2] = levelMap[index.y + (under.y - index.y)][index.x + (under.x - index.x)];
        forward = (tiles[0] != null && !(tiles[0] instanceof Moving)  && !(tiles[0] instanceof Air));
        below = (tiles[1] != null && !(tiles[1] instanceof Moving)  && !(tiles[1] instanceof Air));
        beneath = (tiles[2] != null && !(tiles[2] instanceof Moving)  && !(tiles[2] instanceof Air));
      }catch(IndexOutOfBoundsException e){
        if(this.orientation == 3){
          this.orientation = 0;
        }else{
          this.orientation++;
        }
        updateIndeces();
      }    
      
      if(!forward && (beneath || below)){
        this.getBody().setLinearVelocity(front.x - index.x, index.y - front.y);
      }else if(!forward && !beneath && !below){ 
        if(this.orientation == 3){
          this.orientation = 0;
        }else{
          this.orientation++;
        }
        updateIndeces();
        tempX = Math.round(bodyX + (front.x - index.x));
        tempY = Math.round(bodyY + (index.y - front.y)); 
      }else if(forward && beneath){ 
        if(this.orientation == 0){
          this.orientation = 3;
        }else{
          this.orientation--;
        }
        updateIndeces();
        tempX = Math.round(bodyX + (front.x - index.x));
        tempY = Math.round(bodyY + (index.y - front.y)); 
      }
      
      bodyX = Math.round(this.getBody().getPosition().x);
      bodyY = Math.round(this.getBody().getPosition().y);

      if(bodyX == tempX && bodyY == tempY){
        
        index.x = index.x + (front.x - index.x);
        index.y = index.y + (front.y - index.y);
        updateIndeces();
        tempX = Math.round(bodyX + (front.x - index.x));
        tempY = Math.round(bodyY + (index.y - front.y));
      }
    }
  }

  public void updateIndeces(){
    if(this.orientation == 0){
      front.x = index.x+1;
      front.y = index.y;
      down.x = index.x+1;
      down.y = index.y+1;
      under.x = index.x;
      under.y = index.y+1;
    }else if(this.orientation == 1){
      front.x = index.x;
      front.y = index.y+1;
      down.x = index.x-1;
      down.y = index.y+1;
      under.x = index.x-1;
      under.y = index.y;
    }else if(this.orientation == 2){
      front.x = index.x-1;
      front.y = index.y;
      down.x = index.x-1;
      down.y = index.y-1;
      under.x = index.x;
      under.y = index.y-1;
    }else if(this.orientation == 3){
      front.x = index.x;
      front.y = index.y-1;
      down.x = index.x+1;
      down.y = index.y-1;
      under.x = index.x+1;
      under.y = index.y;
    }
  }

  public void setClockwise(boolean clockwise){
    this.clockwise = clockwise;
  }

}
 // skipping blocks due to bodyX and Y cast to ints, cutting off decimal