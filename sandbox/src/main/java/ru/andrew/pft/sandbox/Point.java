package ru.andrew.pft.sandbox;

public class Point {
  double a;
  double b;

  public Point(double a, double b){
    this.a = a;
    this.b = b;
  }

  public double pointDistance(){
    return Math.sqrt(Math.pow(this.a - this.b, 2));
  }

}
