package org.intensive.annotationsandreflection;

public class AreaImpl implements Area {

    public AreaImpl() {
        System.out.println("AreaImpl is initialized");
    }

    public int areaOfSquare(Integer sideLength) {
        int result = sideLength * sideLength;
        System.out.println("The area of the square is: " + result);

        return result;
    }

    public double areaOfCircle(Integer radius) {
        double result = Math.PI * radius * radius;
        System.out.println("The area of the circle is: " + result);

        return result;
    }

    public double areaOfTriangle(Integer sideLength) {
        double result = (Math.sqrt(3) / 4) * sideLength * sideLength;
        System.out.println("The area of the triangle is: " + result);

        return result;
    }

    public double calculateArea(Integer value) {
        if (value % 2 == 0) {
            return areaOfSquare(value);
        }
        else if (value % 3 == 0) {
            return areaOfCircle(value);
        }
        else {
            return areaOfTriangle(value);
        }
    }
}
