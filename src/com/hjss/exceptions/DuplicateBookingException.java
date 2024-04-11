package com.hjss.exceptions;

public class DuplicateBookingException extends Exception {
    public DuplicateBookingException() {
        super("Duplicate Booking Exist");
    }
}
