package ru.andrew.pft.sandbox;

public class Distance {
  public static void main(String[] args){
    Point p = new Point(2, 7);
    System.out.println("Расстояние между двумя точками (согласно функции) " + p.a + " и "+ p.b + " равно " + distance(p.a, p.b));
    System.out.println("Расстояние между двумя точками (согласно методу) " + p.a + " и "+ p.b + " равно " + p.pointDistance());
  }

  public static double distance(double a, double b){
    return Math.sqrt(Math.pow(a - b, 2));
  }
}
