package com.hjss.enums;

public enum Rating {
    One(1, "Very dissatisfied"),
    Two(2, "Dissatisfied"),
    Three(3, "Ok"),
    Four(4, "Satisfied"),
    Five(5, "Very Satisfied");

    private final int value;
    private final String description;

    Rating(int value, String description) {
        this.value = value;
        this.description = description;
    }


    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
