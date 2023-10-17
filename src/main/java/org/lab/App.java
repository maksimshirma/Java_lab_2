package org.lab;

import java.util.Objects;
import java.util.Scanner;

public class App {
    /**
     * The main method.
     * @param args - arguments of the main method.
     */
    public static void main( String[] args ) {
        String expression;
        String action;
        boolean isValid = false;
        Scanner scanner = new Scanner(System.in);

        Calculator calc = new Calculator();

        do {
            System.out.print("Введите выражение: ");
            expression = scanner.nextLine();

            isValid = calc.enterExpression(expression);

            if (isValid) {
                System.out.println(expression + " = " + calc.calculate());
            } else {
                System.out.println("Выражение некорректное.");
            }

            System.out.println("Чтобы повторить, напишите \"r\"");
            System.out.println("Чтобы выйти, напишите \"e\"");
            System.out.print("Действие: ");

            for (action = scanner.nextLine(); !(Objects.equals(action, "r") || Objects.equals(action, "e")); action = scanner.nextLine()) {
                System.out.println(action);
                System.out.print("Ошибка, попробуйте снова: ");
            }
        } while (!Objects.equals(action, "e"));
    }
}
