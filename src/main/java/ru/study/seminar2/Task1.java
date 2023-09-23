package ru.study.seminar2;

/*
Реализуйте метод, который
  запрашивает у пользователя ввод дробного числа (типа float),
   и возвращает введенное значение.
  Ввод текста вместо числа не должно приводить к падению приложения,
    вместо этого, необходимо повторно запросить у пользователя ввод данных.
 */

import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        float number = 0;
        boolean correctInput = false;
        while (! correctInput) {
            System.out.print("Input float number > ");
            String input = scanner.nextLine();
            try {
                number = Float.parseFloat(input);
                correctInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input, try again");
            }
        }
        System.out.printf("Your input is '%s'", number);
    }
}
