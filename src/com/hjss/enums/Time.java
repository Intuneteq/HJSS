package com.hjss.enums;

/**
 * The Time enum represents different time slots in the Hatfield Junior Swimming School (HJSS).
 * Each time slot has a specific time range.
 */
public enum Time {
    TWO("2-3pm"), // Time slot from 2pm to 3pm
    THREE("3-4pm"), // Time slot from 3pm to 4pm
    FOUR("4-5pm"), // Time slot from 4pm to 5pm
    FIVE("5-6pm"), // Time slot from 5pm to 6pm
    SIX("6-7pm"); // Time slot from 6pm to 7pm

    private final String value; // The value representing the time slot

    /**
     * Constructs a Time enum with the specified time slot value.
     *
     * @param value The value representing the time slot.
     */
    Time(String value) {
        this.value = value;
    }

    /**
     * Gets the value representing the time slot.
     *
     * @return The value representing the time slot.
     */
    public String getValue() {
        return value;
    }
}
