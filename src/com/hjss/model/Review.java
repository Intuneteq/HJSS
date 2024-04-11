package com.hjss.model;

import com.hjss.enums.Rating;

public class Review {
    static int count = 0;
    private final int id;
    private final Rating rating;
    private final String feedback;
    private final Booking booking;

    public Review(Rating rating, String feedback, Booking booking) {
        id = ++count;
        this.rating = rating;
        this.feedback = feedback;
        this.booking = booking;
    }

    public int getId() {
        return id;
    }

    public Rating getRating() {
        return rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public Booking getBooking() {
        return booking;
    }

    @Override
    public String toString() {
        return "Id: " + getId() + " | Rating Score: " + getRating().getValue() + " | Rating description: " + getRating().getDescription() + " | Feedback: " + getFeedback() + " | Booking: \n";
    }
}
