package ru.study.seminar3;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом
        System.out.println("Please input your data separated by spaces (Фамилия Имя Отчество датарождения номертелефона пол)");
        Scanner scanner = new Scanner(System.in);

        UserData data = new UserData(scanner.nextLine());

        // Приложение должно проверить введенные данные по количеству.
        // Если количество не совпадает с требуемым, вернуть код ошибки
        int elementsSizeValid = data.inputLengthValid();

        // обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
        if (elementsSizeValid == -1) {
            System.out.println("Insufficient fields have been entered");
            System.exit(1);
        } else if (elementsSizeValid == -2) {
            System.out.println("Too many fields have been entered");
            System.exit(1);
        }

        // Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры.
        // Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы.
        // Можно использовать встроенные типы java и создать свои.
        // Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.
        try {
            data.parseUserInput();
            // Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии,
            // * Текущий реализация явно проверяет, что в имени содержатся только буквы.
            //     Если этого нет надо будет поймать исключение т.к. файловая система мажет не позволить создать файл со специфичным именем
            //   в него в одну строку должны записаться полученные данные, вида
            //<Фамилия> <Имя> <Отчество> <датарождения> <номертелефона> <пол>
            // * Позволю себе поправить формат файла добавив пробел как разделитель для каждого поля, а не только для даты и телефона
            //
            //Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
            // Не забудьте закрыть соединение с файлом.
            try {
                data.toFile();
            } catch (IOException e) {
                //При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано,
                // пользователь должен увидеть стектрейс ошибки.
                System.out.println("Unable to save data to file, cause" + e.getMessage() + "\n" +
                        "Probably you forgot to create directory for file\n" + "You can see StackTrace below");
                System.out.println();
                e.printStackTrace();
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
