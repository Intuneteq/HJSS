package com.hjss.menu;

public class GradeMenu extends Menu{
    /**
     * Displays the options to the user.
     */
    @Override
    protected void print() {
        System.out.println("Select Grade: ");
        System.out.println("[1]: One");
        System.out.println("[2]: Two");
        System.out.println("[3]: Three");
        System.out.println("[4]: Four");
        System.out.println("[5]: Five");
    }

    /**
     * Checks if the input is a valid option.
     *
     * @param input The input to validate.
     * @return True if the input is valid, otherwise false.
     */
    @Override
    protected boolean isValidOption(int input) {
        return input >= 1 && input <= 5;
    }
}
