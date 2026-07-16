package org.intensive.annotationsandreflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Scanner;

import static java.time.LocalTime.now;

public class Main {
    static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Area area = new AreaImpl();

        invokeWithAnnotationCheck(area, "areaOfTriangle");

        invokeWithAnnotationCheck(area, "areaOfCircle", 3);

        invokeWithAnnotationCheck(area, "calculateArea");
    }

    private static void invokeWithAnnotationCheck(Area area, String methodName, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends Area> areaClass = Area.class;

        Method method;
        if (args.length > 0) {
            method = areaClass.getDeclaredMethod(methodName, Integer.class);
        } else {
            method  = areaClass.getDeclaredMethod(methodName);
        }
        invokeMethodWithAnnotation(area, method, args);
    }

    private static void invokeMethodWithAnnotation(Object object, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        if (method.isAnnotationPresent(TraceArgs.class)) {
            System.out.println("Method " + method.getName() + " has next value" + method.getParameters()[0]);

            method.setAccessible(true);
            method.invoke(object, args);
        }

        if (method.isAnnotationPresent(Invocations.class)) {
            Invocations invocations = method.getAnnotation(Invocations.class);
            System.out.println("Invoking method " + method.getName() + " " + invocations.value() + " times");

            for (int i = 0; i < invocations.value(); i++) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }

        if (method.isAnnotationPresent(MeasureTime.class)) {
            method.setAccessible(true);

            LocalTime timeToStart = now();
            Object calculatedArea = method.invoke(object);
            LocalTime timeToEnd = now();

            System.out.println("CalculatedArea: " + calculatedArea.toString() + ", execution time (nanoseconds): " + (timeToEnd.toNanoOfDay() - timeToStart.toNanoOfDay()));
        }
    }
}
