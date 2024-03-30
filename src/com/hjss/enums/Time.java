package com.hjss.enums;

/**
 * HJSS Time in Enum
 */
public enum Time {
    TWO("2-3pm"), THREE("3-4pm"), FOUR("4-5pm"),
    FIVE("5-6pm"), SIX("6-7pm");

    private final String value;

    Time(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
