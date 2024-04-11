package com.hjss.exceptions;

public class BookingAttendedException extends Exception {
    public BookingAttendedException() {
        super("This booking has been attended");
    }
}
