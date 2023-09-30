package ru.study.seminar3;

/*
Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом:
Фамилия Имя Отчество датарождения номертелефона пол

Форматы данных:
фамилия, имя, отчество - строки
датарождения - строка формата dd.mm.yyyy
номертелефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.

Приложение должно проверить введенные данные по количеству.
Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.

Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.

Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида

<Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>

Однофамильцы должны записаться в один и тот же файл, в отдельные строки.

Не забудьте закрыть соединение с файлом.

При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом
        System.out.println("Введите данные разделённые пробелом (Фамилия Имя Отчество датарождения номертелефона пол)");
        Scanner scanner = new Scanner(System.in);

        UserData data = new UserData(scanner.nextLine());

        // Приложение должно проверить введенные данные по количеству.
        // Если количество не совпадает с требуемым, вернуть код ошибки
        int elementsSizeValid = data.inputLengthValid();

        // обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
        if (elementsSizeValid == -1) {
            System.out.println("Вы ввели недостаточное количество полей");
            System.exit(1);
        } else if (elementsSizeValid == -2) {
            System.out.println("Вы ввели слишком много полей");
            System.exit(1);
        }

        // Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры.

        // Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы.
        // Можно использовать встроенные типы java и создать свои.
        // Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.
    }
}
