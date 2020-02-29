package me.tlgm;

public enum Comands {
    HELP("/help", "помощь"),
    ENTER_FIO("/fio", "введи ФИО"),
    ENTER_PASSPORT("passport", "введи серию и номер паспорта");

    private final String description;

    Comands(String name, String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Comands value : values()) {
            result.append(String.format("%s1: %s2 \n", value.name(), value.description));
        }
        return result.toString();
    }
}
