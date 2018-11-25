void loadCountries() {
  for (int i = 0; i < n; i++) {
    countries.add(new Foreign(PVector.random3D().mult(distance[i]*300), gdp[i], imports[i], exports[i], flag[i], label[i]));
  }
}
