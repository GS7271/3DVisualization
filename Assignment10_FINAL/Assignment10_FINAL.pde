// MSDS 6390 Visualization of Information
// Assignment 10
// Oscar Padilla
// 2018-11-19
// US Balance of Trade & GDP by Country
// Data Source 1 US Trade in Goods by Country: United Census Bureau census.gov
// Data Source 2 GDP by Country: World Bank data.worldbank.org

ArrayList<CountryObject> countries;
Core usa;
PFont labelFont, titleFont;
float rotX = 0;
float rotY = 0;

void settings() {
  size(1920, 1200, P3D);
}

void setup() {
  countries = new ArrayList<CountryObject>();
  noStroke();
  labelFont = loadFont("Arial-Black-8.vlw");
  titleFont = loadFont("Arial-Black-12.vlw");
  usa = new Core();
  usa.updatePosition(new PVector(0, 0, 0));
  countries.add(usa);
  getData();
  loadCountries();
}

void draw() {
  background(20);
  pointLight(255, 255, 255, 0, -500, 1000);
  pointLight(255, 255, 255, width, 1500, -1000);
  textFont(titleFont);
  text("US Trade in Goods by Country", 10, 15, 0);
  text("Sphere Size is proportional to GDP", 10, 30, 0);
  fill(255, 0, 0);
  text("Imports = RED line", 10, 45, 0);
  fill(0, 255, 0);
  text("Exports = GREEN line", 10, 60, 0);
  fill(255);
  text("Distance from the Center ~ sqrt(GDP / Imports)", 10, 75, 0);
  //textFont(labelFont);
  text("Use UP - DOWN - LEFT -RIGHT keys to rotate graph and SPACE BAR to reset", 10, height-15, 0);
  noFill();
  translate(width/2, height/2, 0);
  rotateX(rotX * PI / 360);
  rotateY(rotY * PI / 360);
  for (CountryObject c : countries) {
    c.drawObject();
    if (c instanceof Foreign) {
      ((Foreign)c).connectCore();
    }
  }
}

void keyPressed() {
  if (key == CODED) {
    if (keyCode == LEFT) {
      rotY -= 45;
      println("clockwise " + rotY);
    } else if (keyCode == RIGHT) {
      rotY += 45;
      println("counter " + rotY);
    } else if (keyCode == UP) {
      rotX += 45;
      println("up " + rotX);
    } else if (keyCode == DOWN) {
      rotX -= 45;
      println("down " + rotX);
    }
  } else if (key == ' ') {
    rotX = 0;
    rotY = 0;
    println("reset");
  }
}
