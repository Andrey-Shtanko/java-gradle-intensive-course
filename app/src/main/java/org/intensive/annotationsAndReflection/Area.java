package org.intensive.annotationsAndReflection;

import java.util.Scanner;

public class Area {
    public static double areaOfSquare (int side) {
        double result = side * side;
        System.out.println("The area of the square is: " + result);
        return result;
    }

    @TraceArgs
    private double areaOfCircle (int side) {
        double result = side * side * Math.PI;
        System.out.println("The area of the circle is: " + result);
        return result;
    }
    @Invocations(5)
    protected double areaOfTriangle (int side) {
        double result = (Math.sqrt(3) / 4) * side * side;
        System.out.println("The area of the triangle is: " + (Math.sqrt(3) / 4) * side * side);
        return result;
    }

    @MeasureTime
    public double calculateArea (int number) {
        double result;

        if (number % 2 == 0) {
            result = areaOfSquare(number);
        }
        else if (number % 3 == 0) {
            result = areaOfCircle(number);
        }
        else {
            result = areaOfTriangle(number);
        }
        return result;
    }
}
