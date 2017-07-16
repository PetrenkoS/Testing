package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Admin on 16.07.2017.
 */
public class PointTest {

  @Test

  public static void testArea() {
    Point p1 = new Point(2, 5);
    Point p2 = new Point(10, 5);
    Assert.assertEquals(p1.distance(p2), 8.0);
  }

  @Test

  public static void testArea1() {
    Point p1 = new Point(4, 2);
    Point p2 = new Point(0, 6);
    Assert.assertEquals(p1.distance(p2), 5.656854249492381);
  }

  @Test
  public static void testArea2() {
    Point p1 = new Point(306, 300);
    Point p2 = new Point(50, 80);
    Assert.assertEquals(p1.distance(p2), 337.5440711966365);
  }
}
