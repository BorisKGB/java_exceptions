package ru.study.seminar3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
    private String defaultFilePath = String.join(File.separator, Arrays.asList("data", "seminar3"));



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
            } else if (this.surname == null && canBeName(element)) {
                this.surname = element;
            } else if (this.name == null && canBeName(element)) {
                this.name = element;
            } else if (this.patronymic == null && canBeName(element)) {
                this.patronymic = element;
            } else if (this.birthday == null && element.length() == birthdayFormat.length()) {
                // TODO: too weak value check (need more precise condition)
                //   now lower condition to last place via it conflicts with name parts because checks only string length
                this.birthday = parseDateOrNull(element);
            } else {
                // TODO: mark all name parts as errors if catch any empty name field after parsing attempt?
                //    'correct_surname, incorrect_name, correct_patronymic' not produces 'correct_name, incorrect_patronymic_as_name, empty_patronymic'
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
            // TODO: validate Date value against current date? Now can input 10.10.1000 for example
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

    private String toLine() {
        return getSurnameString() + " " + getNameString() + " " + getPatronymicString() + " " +
                getBirthdayString() + " " + getPhoneString() + " " + getGenderString();
    }

    public void toFile() throws IOException {
        this.toFile(this.defaultFilePath + File.separator + this.surname);
    }

    public void toFile(String path) throws IOException {
        // TODO: fail if exec before parseUserInput()
        try (FileWriter fw = new FileWriter(path, true)) {
            fw.write(this.toLine() + "\n");
        } catch (IOException e) {
            // let's assume that there will be something at this place
            throw new IOException(e);
        }
    }
}
