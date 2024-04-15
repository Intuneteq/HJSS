package com.hjss.menu;

/**
 * Menu to choose how the user views the timetable
 */
public class BookLessonMenu extends Menu {

    /**
     * Displays the options to the user.
     */
    @Override
    protected void print() {
        System.out.println();
        System.out.println("************** Book A Lesson **************");
        System.out.println("[1]: By Day");
        System.out.println("[2]: By Coach");
        System.out.println("[3]: By Grade");
    }

    /**
     * Checks if the input is a valid option.
     *
     * @param input The input to validate.
     * @return True if the input is valid, otherwise false.
     */
    @Override
    protected boolean isValidOption(int input) {
        return input >= 1 && input <= 3;
    }
}
