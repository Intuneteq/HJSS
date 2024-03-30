package com.hjss.menu;

public class MainMenu extends Menu {
    /**
     * Displays the options to the user.
     */
    @Override
    protected void print() {
        System.out.println(" ");
        System.out.println("************** App Menu **************");
        System.out.println("[1]: Book a swimming lesson");
        System.out.println("[2]: Change/Cancel a booking");
        System.out.println("[3]: Attend a swimming lesson");
        System.out.println("[4]: Monthly learner report");
        System.out.println("[5]: Monthly coach report");
        System.out.println("[6]: Register a new learner");
        System.out.println("[0]: Exit");
    }

    /**
     * Checks if the input is a valid option.
     *
     * @param input The input to validate.
     * @return True if the input is valid, otherwise false.
     */
    @Override
    protected boolean isValidOption(int input) {
        return input >= 0 && input <= 6;
    }
}
