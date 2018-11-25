class Foreign extends CountryObject {

  private float gdp;
  private float imports;
  private float exports;
  private String iso;
  private String label;

  public Foreign(PVector initialPosition, float _gdp, float _imports, float _exports, String _iso, String _label) {
    super(initialPosition);
    gdp = _gdp;
    imports = _imports;
    exports = _exports;
    iso = _iso;
    label = _label;
  }

  void buildObject() {
    PImage flag = loadImage(iso);
    //PShape countrySphere = createShape(SPHERE, map(pow(3*gdp/(4*PI), 1/3), 0, pow(3*maxGDP/(4*PI), 1/3), 0, 32));
    //PShape countrySphere = createShape(SPHERE, map(gdp, 0, maxGDP, 0, 100));
    PShape countrySphere = createShape(SPHERE, log(gdp));
    //noStroke();
    countrySphere.setTexture(flag);
    shape(countrySphere);
    fill(255);
    pushMatrix();
    translate(log(gdp), log(gdp), 0);
    textFont(labelFont);
    text(label, 0, 0, 0);
    popMatrix();
  }

  void connectCore() {
    strokeWeight(map(imports, 0, maxImports, 0, 10));
    //stroke(map(imports, 0, maxImports, 0, 255), 0, 0);
    stroke(255, 0, 0);
    line(0, 0, 0, position.x, position.y, position.z);
    
    strokeWeight(map(exports, 0, maxImports, 0, 10));
    stroke(0, 255, 0);
    noFill();
    beginShape();
    curveVertex(0, 0, 0);
    curveVertex(0, 0, 0);
    curveVertex(position.x/2, pow(position.x/2, 1/2), position.z/2);
    curveVertex(position.x, position.y, position.z);
    curveVertex(position.x, position.y, position.z);
    endShape();
    
    noStroke();
  }
}
