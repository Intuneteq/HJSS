package com.hjss.menu;

public class DayMenu extends Menu{
    /**
     * Displays the options to the user.
     */
    @Override
    protected void print() {
        System.out.println();
        System.out.println("************** Choose Day **************");
        System.out.println("[1]: Monday");
        System.out.println("[2]: Wednesday");
        System.out.println("[3]: Friday");
        System.out.println("[4]: Saturday");
    }

    /**
     * Checks if the input is a valid option.
     *
     * @param input The input to validate.
     * @return True if the input is valid, otherwise false.
     */
    @Override
    protected boolean isValidOption(int input) {
        return input >= 1 && input <= 4;
    }
}
