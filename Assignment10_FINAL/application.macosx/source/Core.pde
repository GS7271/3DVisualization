class Core extends CountryObject {
  
  void buildObject() {
    PImage coreFlag = loadImage("us.png");
    PShape coreSphere = createShape(SPHERE, 25);
    coreSphere.setTexture(coreFlag);
    shape(coreSphere);
  }
}
