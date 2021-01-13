class ColourGiver extends Air {
  private String colour;

  ColourGiver(Point point, Sprite sprite, Body body, String colour) {
    super(point, sprite, body);
    this.colour = colour;
  }

  public String getColour() {
    return this.colour;
  }

  public void setColour(String colour) {
    this.colour = colour;
  }
}