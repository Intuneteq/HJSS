package com.hjss.model;

import com.hjss.enums.Rating;

/**
 * The Review class represents a review provided by a learner after attending a swimming lesson.
 * It contains information such as the review ID, rating, feedback, and associated booking.
 */
public class Review {
    static int count = 0;

    /**
     * Review ID
     */
    private final int id;

    /**
     * Rating given in the review
     */
    private final Rating rating;

    /**
     * Feedback provided in the review
     */
    private final String feedback;

    /**
     * Booking associated with the review
     */
    private final Booking booking;

    /**
     * Constructs a Review object with the specified rating, feedback, and associated booking.
     *
     * @param rating   The rating given in the review.
     * @param feedback The feedback provided in the review.
     * @param booking  The booking associated with the review.
     */
    public Review(Rating rating, String feedback, Booking booking) {
        id = ++count;
        this.rating = rating;
        this.feedback = feedback;
        this.booking = booking;
    }

    /**
     * Retrieves the review ID.
     *
     * @return The review ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the rating given in the review.
     *
     * @return The rating given in the review.
     */
    public Rating getRating() {
        return rating;
    }

    /**
     * Retrieves the feedback provided in the review.
     *
     * @return The feedback provided in the review.
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Retrieves the booking associated with the review.
     *
     * @return The booking associated with the review.
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Returns a string representation of the Review object.
     *
     * @return A string representation of the Review object.
     */
    @Override
    public String toString() {
        return "Id: " + getId() +
                " | Rating Score: " + getRating().getValue() +
                " | Rating description: " + getRating().getDescription() +
                " | Feedback: " + getFeedback() +
                " | Booking: \n";
    }
}

