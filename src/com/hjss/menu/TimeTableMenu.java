package com.hjss.menu;

import java.util.Set;

/**
 * The TimeTableMenu class represents a menu for displaying the available lesson timetable.
 * It allows users to select a specific lesson from the timetable.
 */
public class TimeTableMenu extends Menu{

    private final String timeTable; // The timetable to display
    private final Set<Integer> lessonIds; // Set of lesson IDs

    /**
     * Constructs a TimeTableMenu object with the provided timetable and lesson IDs.
     *
     * @param timeTable The timetable to display.
     * @param lessonIds The set of lesson IDs.
     */
    public TimeTableMenu(String timeTable, Set<Integer> lessonIds) {
        this.timeTable = timeTable;
        this.lessonIds = lessonIds;
    }
    /**
     * Displays the options to the user.
     */
    @Override
    protected void print() {
        System.out.println();
        System.out.println("************************************ Book A Lesson ************************************");
        System.out.println(timeTable);
    }

    /**
     * Checks if the input is a valid option.
     *
     * @param input The input to validate.
     * @return True if the input is valid, otherwise false.
     */
    @Override
    protected boolean isValidOption(int input) {
        return lessonIds.contains(input);
    }
}
