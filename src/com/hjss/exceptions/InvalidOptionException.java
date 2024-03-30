package com.hjss.exceptions;

public class InvalidOptionException extends Exception {
    public InvalidOptionException() {
        super("Error: Please enter a valid menu ID");
    }
}
