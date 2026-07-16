package org.intensive.annotationsandreflection;

import java.util.Scanner;

public class ScanUtils {

    private ScanUtils() {
    }

    public static int scanValue() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the measurement value: ");
        return scanner.nextInt();
    }
}
