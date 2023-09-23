package ru.study.seminar2;

/*
Разработайте программу, которая
  выбросит Exception, когда пользователь вводит пустую строку.
Пользователю должно показаться сообщение, что пустые строки вводить нельзя.
 */

import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = "";
        while (text.equals("")) {
            System.out.print("Input something > ");
            text = scanner.nextLine();
            try {
                if (text.equals("")) throw new EmptyStringException();
            } catch (EmptyStringException e) {
                System.out.println("Empty input is not allowed, try again");
            }
        }
        System.out.printf("Your input is '%s'", text);
    }
}

class EmptyStringException extends Exception {
    public EmptyStringException() {
        super();
    }
}
