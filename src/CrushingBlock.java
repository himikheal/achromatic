class CrushingBlock extends Block implements CrusherAI {
  private Point start;
  private Point end;

  CrushingBlock(Point point, Sprite sprite, Body body, Speed speed, Point start, Point end) {
    super(point, sprite, body, speed);
    this.start = start;
    this.end = end;
  }
}