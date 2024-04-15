package com.hjss.enums;

/**
 * The Rating enum represents different levels of satisfaction ratings.
 */
public enum Rating {
    One(1, "Very dissatisfied"), // Rating 1: Very dissatisfied
    Two(2, "Dissatisfied"), // Rating 2: Dissatisfied
    Three(3, "Ok"), // Rating 3: Ok
    Four(4, "Satisfied"), // Rating 4: Satisfied
    Five(5, "Very Satisfied"); // Rating 5: Very Satisfied

    private final int value; // The numerical value associated with the rating
    private final String description; // The description of the rating

    /**
     * Constructs a Rating enum with the specified numerical value and description.
     *
     * @param value       The numerical value of the rating.
     * @param description The description of the rating.
     */
    Rating(int value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * Gets the numerical value of the rating.
     *
     * @return The numerical value of the rating.
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets the description of the rating.
     *
     * @return The description of the rating.
     */
    public String getDescription() {
        return description;
    }
}
