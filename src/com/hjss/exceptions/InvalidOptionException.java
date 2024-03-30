package com.hjss.exceptions;

public class InvalidOptionException extends Exception {
    public InvalidOptionException() {
        super("Invalid menu option. Please enter a valid menu ID");
    }
}
