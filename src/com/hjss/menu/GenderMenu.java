package com.hjss.menu;

public class GenderMenu extends Menu{
    /**
     * Displays the options to the user.
     */
    @Override
    protected void print() {
        System.out.println();
        System.out.println("Select Gender: ");
        System.out.println("[1]: Male");
        System.out.println("[2]: Female");
    }

    /**
     * Checks if the input is a valid option.
     *
     * @param input The input to validate.
     * @return True if the input is valid, otherwise false.
     */
    @Override
    protected boolean isValidOption(int input) {
        return input >= 1 && input <= 2;
    }
}
