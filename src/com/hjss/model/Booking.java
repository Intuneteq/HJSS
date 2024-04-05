package com.hjss.model;

import com.hjss.observers.LessonObserver;

public class Booking  {
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


        // Register this booking as an observer with the lesson
        lesson.addObserver((LessonObserver) this);
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
        this.attendanceStatus = true;
    }

    public boolean getCancellationStatus() {
        return cancellationStatus;
    }

    public void setCancellationStatus() {
        this.cancellationStatus = true;
    }

    public String toString() {
        return "Id: " + getId() + "\nLesson Id: " + getLesson().getId() + "\nBooked By: " + getLearner().getName() + "\nAttendance Status: " + getAttendanceStatus() + "Cancellation Status: " + getCancellationStatus();
    }
}
