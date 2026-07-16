package org.intensive.annotationsandreflection;

public interface Area {

    default int areaOfSquare() {
        int sideLength = ScanUtils.scanValue();
        return areaOfSquare(sideLength);
    }

    int areaOfSquare(Integer sideLength);

    @TraceArgs
    default double areaOfCircle() {
        int radius = ScanUtils.scanValue();
        return areaOfCircle(radius);
    }

    @TraceArgs
    double areaOfCircle(Integer radius);

    @Invocations(5)
    default double areaOfTriangle() {
        int sideLength = ScanUtils.scanValue();
        return areaOfTriangle(sideLength);
    }

    @Invocations(5)
    double areaOfTriangle(Integer sideLength);

    @MeasureTime
    default double calculateArea() {
        int value = ScanUtils.scanValue();
        return calculateArea(value);
    }

    @MeasureTime
    double calculateArea(Integer value);
}
