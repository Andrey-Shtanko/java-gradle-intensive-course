package org.intensive.annotationsandreflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static java.time.LocalTime.now;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> area = Class.forName("org.intensive.annotationsAndReflection.Area");

        Method[] methods = area.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(TraceArgs.class)) {
                System.out.println("Method " + method.getName() + " has next args" + Arrays.toString(method.getParameters()));
            }
            if (method.isAnnotationPresent(Invocations.class)) {
                Invocations invocations = method.getAnnotation(Invocations.class);
                for (int i = 0; i < invocations.value(); i++) {
                    method.setAccessible(true);
                    method.invoke(area.getDeclaredConstructor().newInstance(), 1);
                }
                System.out.println("Method " + method.getName() + " has been invoked by " + invocations.value() + " times");
            }
            if (method.isAnnotationPresent(MeasureTime.class)) {
                Scanner input = new Scanner(System.in);

                System.out.print("Enter the number: ");
                int number = input.nextInt();

                method.setAccessible(true);

                LocalTime timeToStart = now();
                Object calculatedArea = method.invoke(area.getDeclaredConstructor().newInstance(), number);
                LocalTime timeToEnd = now();

                System.out.println("CalculatedArea: " + calculatedArea.toString() + ", execution time (nanoseconds): " + (timeToEnd.toNanoOfDay() - timeToStart.toNanoOfDay()));
            }
        }
    }
}
