package com.hjss.enums;

/**
 * The Grade enum represents the different grades for learners.
 */
public enum Grade {
    ONE(1), // Grade 1
    TWO(2), // Grade 2
    THREE(3), // Grade 3
    FOUR(4), // Grade 4
    FIVE(5); // Grade 5

    private final int value; // The numerical value associated with the grade

    /**
     * Constructs a Grade enum with the specified numerical value.
     *
     * @param value The numerical value of the grade.
     */
    Grade(int value) {
        this.value = value;
    }

    /**
     * Gets the numerical value of the grade.
     *
     * @return The numerical value of the grade.
     */
    public int getValue() {
        return value;
    }
}
