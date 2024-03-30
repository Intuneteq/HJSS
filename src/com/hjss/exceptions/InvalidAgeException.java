package com.hjss.exceptions;

public class InvalidAgeException extends Exception{
    public InvalidAgeException() {
        super("Error: Age must be between 4 and 11");
    }
}
