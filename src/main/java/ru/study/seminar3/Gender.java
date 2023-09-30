package ru.study.seminar3;

enum Gender {
    MALE("m"),
    FEMALE("f");

    private final String name;

    Gender(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Gender fromString(String name) {
        for (Gender gender : Gender.values()) {
            if (name.equals(gender.name)) return gender;
        }
        return null;
    }
}
