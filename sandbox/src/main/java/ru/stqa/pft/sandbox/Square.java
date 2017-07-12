package ru.stqa.pft.sandbox;

/**
 * Created by Admin on 12.07.2017.
 */
public class Square {
  public double l;

  public Square(double l) {
    this.l = l;
  }
  public double area() {
    return this.l*this.l;
  }
}
