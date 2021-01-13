class Floater extends Enemy implements FloaterAI {
  private Point[] points;

  Floater(Point point, Sprite sprite, Body body, Speed speed) {
    super(point, sprite, body, speed);
  }

  public void move(Point[] points) {
    
  }
}