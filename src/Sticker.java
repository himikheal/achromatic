class Sticker extends Enemy implements StickerAI {
  private int direction;
  
  Sticker(Point point, Sprite sprite, Body body, int speed, int direction) {
    super(point, sprite, body, speed);
    this.direction = direction;
  }
}