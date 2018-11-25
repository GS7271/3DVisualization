abstract class CountryObject {

  protected PVector position;

  public CountryObject() {
    position = new PVector(0, 0, 0);
  }
  
  public CountryObject(PVector initialPosition) {
    position = initialPosition;
  }

  public void updatePosition(PVector newPosition) {
    position = newPosition;
  }

  //public PVector getCurrentPosition() {
  //  return position;
  //}

  public void drawObject() {
    pushMatrix();
    translate(position.x, position.y, position.z);
    buildObject();
    popMatrix();
  }

  abstract void buildObject();
}
