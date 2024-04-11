package com.hjss.model;

public class Coach {
    static int count = 0;
    /**
     * Coach Id
     */
    private final int id;

    /**
     * Coach Name
     */
    private final String name;

    /**
     * @param name Coach Name
     */
    public Coach(String name) {
        this.id = ++count;
        this.name = name;
    }

    /**
     * @return Coach's Id
     */
    public int getId() {
        return id;
    }

    /**
     * @return Coach's name
     */
    public String getName() {
        return name;
    }

    public String toString() {
        return "id: " + id + "\nname: " + name;
    }
}
