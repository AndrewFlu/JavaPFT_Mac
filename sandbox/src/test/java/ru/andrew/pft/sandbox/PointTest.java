package ru.andrew.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTest {

  @Test
  public void correctDistanceCalculation(){
    Point p1 = new Point(5, 12);
    Assert.assertEquals(p1.pointDistance(), 5.0);
  }

  @Test
  public void correctDistanceCalculationViaDouble(){
    Point p2 = new Point(-1.5, 11.2);
    Assert.assertEquals(p2.pointDistance(), 12.5);
  }

  @Test
  public void distanceBtwPointsMustBePositive(){
    Point p3 = new Point(-17, -42);
    Assert.assertTrue(p3.pointDistance() >= 0);
    Assert.assertEquals(p3.pointDistance(), 25);
  }
}
