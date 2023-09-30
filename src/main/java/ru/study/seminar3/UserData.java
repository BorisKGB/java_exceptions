package ru.study.seminar3;

class UserData {
    private String[] userInputParts;

    public UserData(String userInput) {
        this.userInputParts = userInput.split(" ");
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
}
