package com.hjss.model;

/**
 * The Booking class represents a booking made by a learner for a swimming lesson.
 * It serves as a mediator between lessons and learners.
 * It includes information such as the booking ID, learner, lesson, attendance status, and cancellation status.
 */
public class Booking {
    static int count = 0;
    private final int id; // Booking ID
    private final Learner learner; // Learner who made the booking
    private Lesson lesson; // Lesson that was booked
    private boolean attendanceStatus; // Attendance status of the booking
    private boolean cancellationStatus; // Cancellation status of the booking

    /**
     * Constructs a Booking object with the specified learner and lesson.
     *
     * @param learner The learner who made the booking.
     * @param lesson  The lesson that was booked.
     */
    public Booking(Learner learner, Lesson lesson) {
        this.id = ++count;

        this.learner = learner;
        this.lesson = lesson;
        attendanceStatus = false;
        cancellationStatus = false;
    }

    /**
     * Gets the booking ID.
     *
     * @return The booking ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the learner associated with the booking.
     *
     * @return The learner associated with the booking.
     */
    public Learner getLearner() {
        return learner;
    }

    /**
     * Gets the lesson associated with the booking.
     *
     * @return The lesson associated with the booking.
     */
    public Lesson getLesson() {
        return lesson;
    }

    /**
     * Sets the lesson associated with the booking.
     *
     * @param lesson The lesson to set.
     */
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    /**
     * Gets the attendance status of the booking.
     *
     * @return true if the learner attended the lesson, false otherwise.
     */
    public boolean getAttendanceStatus() {
        return attendanceStatus;
    }

    /**
     * Sets the attendance status of the booking to true.
     * Also updates the learner's grade to match the lesson's grade.
     */
    public void setAttendanceStatus() {
        // Ensure that the learner's grade and lesson's grade are the same!
        learner.setGrade(lesson.getGrade());

        //set to true
        this.attendanceStatus = true;
    }

    /**
     * Gets the cancellation status of the booking.
     *
     * @return true if the booking was cancelled, false otherwise.
     */
    public boolean getCancellationStatus() {
        return cancellationStatus;
    }

    /**
     * Sets the cancellation status of the booking to true.
     * Also increases the vacancy of the lesson.
     */
    public void setCancellationStatus() {
        // Increase Vacancy
        this.getLesson().decrementBySize();

        // Cancel booking
        this.cancellationStatus = true;
    }

    /**
     * Returns a string representation of the Booking object.
     *
     * @return A string representation of the Booking object, including its ID, lesson details, learner, and status.
     */
    public String toString() {
        return "Id: " + getId() +
                "\nLesson Id: " + getLesson().getId() +
                "\nLesson Grade: " + getLesson().getGrade() +
                "\nBooked By: " + getLearner().getName() +
                "\nAttendance Status: " + getAttendanceStatus() +
                "\nCancellation Status: " + getCancellationStatus();
    }
}
