class Moving extends Solid {
  private int speed;

  Moving(Point point, Sprite sprite, Body body, Speed speed) {
    super(point, sprite, body);
    this.speed = speed;
  }

  public int getSpeed() {
    return this.speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }
}