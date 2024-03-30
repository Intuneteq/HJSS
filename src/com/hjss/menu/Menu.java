package com.hjss.menu;

import com.hjss.exceptions.InvalidOptionException;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Implemented using the Template design pattern
 */
public abstract class Menu {
    /**
     * Scanner object for reading user input.
     */
    Scanner console = new Scanner(System.in);

    /**
     * Displays the options to the user.
     */
    protected abstract void print();

    /**
     * Checks if the input is a valid option.
     *
     * @param input The input to validate.
     * @return True if the input is valid, otherwise false.
     */
    protected abstract boolean isValidOption(int input);

    /**
     * Prompts the user for input and returns the selected option.
     *
     * @return The user's selected option.
     */
    public int execute() {
        // Print options to the user
        print();

        // Get and validate user input
        int input = getUserInput();

        while (!isValidOption(input)) {
            System.out.println("\u001B[31mInvalid input. Please try again.\u001B[0m");
            input = getUserInput();
        }

        return input;
    }

    /**
     * Gets input from the user and validates it.
     */
    private int getUserInput() {
        int input = 0;

        try {
            System.out.print("Enter Menu Option: ");
            input = console.nextInt(); // Read user input
        } catch (InputMismatchException e) {
            System.out.println("\u001B[31mInvalid input. Please enter a valid integer.\u001B[0m");
            console.nextLine(); // Clear buffer
        }
        return input;
    }

    /**
     * Pads a number to two digits.
     *
     * @param number The number to pad.
     * @return The padded number.
     */
    public String padToTwoDigits(double number) {
        DecimalFormat df = new DecimalFormat("00");
        return df.format(number);
    }
}
