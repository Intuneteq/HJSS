package com.hjss.exceptions;

public class DuplicateBookingException extends Exception {
    public DuplicateBookingException() {
        super("A Duplicate Booking Exist");
    }
}
