import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Assignment10_FINAL extends PApplet {

// MSDS 6390 Visualization of Information
// Assignment 10
// Oscar Padilla
// 2018-11-19
// US Balance of Trade & GDP by Country

ArrayList<CountryObject> countries;
Core usa;
PFont labelFont, titleFont;
float rotX = 0;
float rotY = 0;

public void settings() {
  size(1920, 1200, P3D);
}

public void setup() {
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

public void draw() {
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

public void keyPressed() {
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
class Core extends CountryObject {
  
  public void buildObject() {
    PImage coreFlag = loadImage("us.png");
    PShape coreSphere = createShape(SPHERE, 25);
    coreSphere.setTexture(coreFlag);
    shape(coreSphere);
  }
}
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

  public abstract void buildObject();
}
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

  public void buildObject() {
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

  public void connectCore() {
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
Table table, export;
int n = 177;
String[] label = new String[n];
String[] flag = new String[n];
String[] cName = new String[n];
float[] gdp = new float[n];
float[] imports = new float[n];
float[] exports = new float[n];
float[] distance = new float[n];
float maxGDP, maxImports;

public void getData() {

  table = loadTable("GDP_XM.csv", "header");
  n = table.getRowCount();
  println(n + " total rows in table");
  int i = 0;

  for (TableRow row : table.rows()) {
    label[i] = row.getString("alpha2");
    flag[i] = row.getString("flag");
    cName[i] = row.getString("cName");
    gdp[i] = row.getFloat("GDP");
    imports[i] = row.getFloat("IYR");
    exports[i] = row.getFloat("EYR");
    distance[i] = row.getFloat("lnd");
    i++;
  }
  
  maxGDP = max(gdp);
  maxImports = max(imports);
  
  println("Max GDP: " + maxGDP + "-   Max Imp: " + maxImports);
}
public void loadCountries() {
  for (int i = 0; i < n; i++) {
    countries.add(new Foreign(PVector.random3D().mult(distance[i]*300), gdp[i], imports[i], exports[i], flag[i], label[i]));
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Assignment10_FINAL" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
