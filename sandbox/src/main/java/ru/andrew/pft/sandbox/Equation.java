package ru.andrew.pft.sandbox;

public class Equation {
  private int n;
  private double a;
  private double b;
  private double c;

  public Equation(double a, double b, double c) {
    this.a = a;
    this.b = b;
    this.c = c;

    double discriminant = b * b - 4 * a * c;

    if (a != 0) {
      if (discriminant > 0) {
        n = 2;
      } else if (discriminant == 0) {
        n = 1;
      } else {
        n = 0;
      }
    } else if (b != 0) {
      n = 1;
    } else if (c != 0) {
      n = 0;
    } else {
      n = -1;
    }
  }

  public int rootNumber() {
    return n;
  }
}