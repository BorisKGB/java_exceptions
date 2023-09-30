package ru.study.seminar3;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

class UserData {
    private String[] userInputParts;
    private String surname;
    private String name;
    private String patronymic;
    private Date birthday;
    private Long phone;
    private Gender gender;

    private String inputDelimiter = " ";
    private String birthdayFormat = "dd.MM.yyyy";
    private String nameValidatePattern = "^[a-zA-Zа-яА-Я]+$";
    private String noDataMessage = "No data";



    public UserData(String userInput) {
        this.userInputParts = userInput.split(inputDelimiter);
        surname = null;
        name = null;
        patronymic = null;
        birthday = null;
        phone = null;
        gender = null;
    }

    /**
     * Validate input size
     *
     * @return data.length or (negative) error code
     * -1 if el size is too low
     * -2 if el size is too big
     */
    public int inputLengthValid() {
        int size = userInputParts.length;
        if (size < 6) size = -1;
        else if (size > 6) size = -2;
        return size;
    }

    // Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры.
    // Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы.
    // Можно использовать встроенные типы java и создать свои.
    // Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.

    /**
     * parse and validate userInputParts against rules
     *  1. Gender must contain only one symbol
     *  2. Phone number must contain only numbers and its length may be in [5 ... 12] digits
     *  3. Birthday must be date in this.birthdayFormat
     *  4. Name parts must match this.nameValidatePattern (letters only)
     * @throws ParseException if not all data was parsed
     */
    public void parseUserInput() throws ParseException {
        List<String> failedToParseParts = new LinkedList<>();
        for (String element : userInputParts) {
            if (element.length() == 1 && this.gender == null) {
                this.gender = Gender.fromString(element);
            } else if (isNumeric(element)
                    && element.length() > 5 && element.length() < 12
                    && this.phone == null) {
                this.phone = Long.parseLong(element);
            } else if (this.birthday == null && element.length() == birthdayFormat.length()) {
                this.birthday = parseDateOrNull(element);
            } else if (this.surname == null && canBeName(element)) {
                this.surname = element;
            } else if (this.name == null && canBeName(element)) {
                this.name = element;
            } else if (this.patronymic == null && canBeName(element)) {
                this.patronymic = element;
            } else {
                failedToParseParts.add(element);
            }
        }
        if (!failedToParseParts.isEmpty()) {
            String errorMsg = "Unable to parse fields [" + String.join(",", failedToParseParts) + "].\n" +
                    "Current parsed state is " + this;
            // TODO: Use custom exception? (errorOffset parameter used as error fields count)
            throw new ParseException(errorMsg, failedToParseParts.size());
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private boolean canBeName(String str) {
        return str.matches(nameValidatePattern);
    }

    /**
     * Parse date against internal birthdayFormat pattern
     * @param str String contain date
     * @return Parsed Date obj or null on parse error
     */
    private Date parseDateOrNull(String str) {
        DateFormat df = new SimpleDateFormat(birthdayFormat);
        Date result;
        try {
            result = df.parse(str);
        } catch (ParseException e) {
            result = null;
        }
        return result;
    }

    public String getSurnameString() {
        return (surname == null) ? this.noDataMessage : surname;
    }

    public String getNameString() {
        return (name == null) ? this.noDataMessage : name;
    }

    public String getPatronymicString() {
        return (patronymic == null) ? this.noDataMessage : patronymic;
    }

    public String getBirthdayString() {
        if (birthday == null) return this.noDataMessage;
        else {
            DateFormat df = new SimpleDateFormat(this.birthdayFormat);
            return df.format(birthday);
        }
    }

    public String getPhoneString() {
        return (phone == null) ? this.noDataMessage : phone.toString();
    }

    public String getGenderString() {
        return (gender == null) ? this.noDataMessage : gender.toString();
    }
    @Override
    public String toString() {
        return "surname='" + getSurnameString() + "'" +
                ", name='" + getNameString() + "'" +
                ", patronymic='" + getPatronymicString() + "'" +
                ", birthday='" + getBirthdayString() + "'" +
                ", phone='" + getPhoneString() + "'" +
                ", gender='" + getGenderString() + "'";
    }
}