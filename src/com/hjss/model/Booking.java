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

    public void setAttendanceStatus(boolean attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public boolean getCancellationStatus() {
        return cancellationStatus;
    }

    public void setCancellationStatus(boolean cancellationStatus) {
        this.cancellationStatus = cancellationStatus;
    }

    public String toString() {
        return "Id: " + getId() + "\nLesson Id: " + getLesson().getId() + "\nBooked By: " + getLearner().getName() + "\nAttendance Status: " + getAttendanceStatus() + "Cancellation Status: " + getCancellationStatus();
    }
}
