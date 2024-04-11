package com.hjss.model;

public class Booking {
    static int count = 0;
    private final int id;
    private final Learner learner;
    private Lesson lesson;
    private boolean attendanceStatus;
    private boolean cancellationStatus;

    public Booking(Learner learner, Lesson lesson) {
        this.id = ++count;

        this.learner = learner;
        this.lesson = lesson;
        attendanceStatus = false;
        cancellationStatus = false;
    }

    public int getId() {
        return id;
    }

    public Learner getLearner() {
        return learner;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public boolean getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus() {
        // Ensure that the learner's grade and lesson's grade are the same!
        learner.setGrade(lesson.getGrade());

        //set to true
        this.attendanceStatus = true;
    }

    public boolean getCancellationStatus() {
        return cancellationStatus;
    }

    public void setCancellationStatus() {
        // Increase Vacancy
        this.getLesson().decrementBySize();

        // Cancel booking
        this.cancellationStatus = true;
    }

    public String toString() {
        return "Id: " + getId() + "\nLesson Id: " + getLesson().getId() + "\nLesson Grade: " + getLesson().getGrade() + "\nBooked By: " + getLearner().getName() + "\nAttendance Status: " + getAttendanceStatus() + "\nCancellation Status: " + getCancellationStatus();
    }
}
