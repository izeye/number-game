package com.izeye.app.numbergame;

import java.util.Scanner;

/**
 * Main class.
 *
 * @author Johnny Lim
 */
public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int end = 2;
            for (int i = 2; i <= end; i++) {
                for (int j = 1; j <= 9; j++) {
                    int answer = i * j;
                    System.out.printf("%d x %d = ", i, j);
                    int input = scanner.nextInt();
                    if (input == answer) {
                        System.out.println("Correct!");
                    }
                    else {
                        System.out.printf("Wrong! The answer is %d.%n", answer);
                    }
                }
            }
        }
    }
}
