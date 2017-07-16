package ru.stqa.pft.sandbox;

/**
 * Created by Admin on 04.06.2017.
 */

    public class PointMain {

        public static void main(String[] args) {

            Point p1 = new Point(306, 300);
            Point p2 = new Point(50, 80);

            System.out.println("Расстояние между двумя точками" + " = " + p1.distance(p2));

        }


    }



