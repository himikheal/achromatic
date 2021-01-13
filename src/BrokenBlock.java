class BrokenBlock extends Solid {
  private int timer;

  BrokenBlock(Point point, Sprite sprite, Body body, int time) {
    super(point, sprite, body);
    this.timer = time;
  }

  public int getTimer() {
    return this.timer;
  }

  public void setTimer(int time) {
    this.timer = time;
  }
}