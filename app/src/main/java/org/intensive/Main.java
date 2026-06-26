package org.intensive;

import java.util.Scanner;

public class Main {
    static void main(){
        Scanner scanner = new Scanner(System.in);

        String inputText = scanner.nextLine();

        WordWrap wrappedContent = new WordWrap(20, inputText);

        wrappedContent.wrap();
    }
}
