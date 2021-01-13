class Bullet extends Enemy implements BulletAI {
  private int direction;

  Bullet(Point point, Sprite sprite, Body body, int speed, int direction) {
    super(point, sprite, body, speed);
    this.direction = direction;
  }
}