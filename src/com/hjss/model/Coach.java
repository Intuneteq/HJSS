package com.hjss.model;

/**
 * The Coach class represents a coach in the swimming school.
 * It contains information such as the coach's ID and name.
 */
public class Coach {
    static int count = 0;

    /** Coach's ID */
    private final int id;

    /** Coach's name */
    private final String name;

    /**
     * Constructs a Coach object with the specified name.
     *
     * @param name The coach's name.
     */
    public Coach(String name) {
        this.id = ++count;
        this.name = name;
    }

    /**
     * Retrieves the coach's ID.
     *
     * @return The coach's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the coach's name.
     *
     * @return The coach's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a string representation of the Coach object.
     *
     * @return A string representation of the Coach object.
     */
    public String toString() {
        return "id: " + id +
                "\nname: " + name;
    }
}

