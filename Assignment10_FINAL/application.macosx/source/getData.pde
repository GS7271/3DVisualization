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

void getData() {

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
