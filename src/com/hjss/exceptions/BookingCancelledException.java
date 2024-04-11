package com.hjss.exceptions;

public class BookingCancelledException extends Exception {
    public BookingCancelledException() {
        super("This Booking has been cancelled");
    }
}
