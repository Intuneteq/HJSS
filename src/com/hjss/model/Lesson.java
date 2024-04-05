package com.hjss.model;

import com.hjss.App;
import com.hjss.enums.Grade;
import com.hjss.observers.LessonObserver;

import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private final List<LessonObserver> observers = new ArrayList<>();
    static int count = 0;

    /**
     * Lesson Id
     */
    private final int id;

    /**
     * Mapped Day Time slot
     */
    private final TimeSlot timeSlot;

    /**
     * Lesson Coach
     */
    private final Coach coach;

    /**
     * Lesson grade
     */
    private final Grade grade;

    private final int size;


    public Lesson(Grade grade, TimeSlot timeSlot, Coach coach) {
        this.id = ++count;
        this.grade = grade;
        this.coach = coach;
        this.timeSlot = timeSlot;
        size = 0;
    }

    /**
     * @return Lesson Id
     */
    public int getId() {
        return id;
    }

    /**
     * @return Current Lesson Capacity
     */
    public int getSize() {
        return size;
    }

    public int getVacancy() {
        return 4 - getSize();
    }

    /**
     * @return Lesson grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * @return Lesson Time slot
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * @return Lesson coach
     */
    public Coach getCoach() {
        return coach;
    }

    public void addObserver(LessonObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(LessonObserver observer) {
        observers.remove(observer);
    }

    public void notifyBookingAttended() {
        for (LessonObserver observer : observers) {
            observer.onBookingAttended();
        }
    }

    public void notifyBookingCancelled() {
        for (LessonObserver observer : observers) {
            observer.onBookingCancelled();
        }
    }

    public String toString() {
        return "Id: " + App.padToTwoDigits(getId()) + " | " + "Day: " + getTimeSlot().day() + " | " + "Time: " + getTimeSlot().time().getValue() + " | " + "Grade: " + getGrade() + " | " + "Coach: " + getCoach().getName() + " | " + "Vacancy: " + getVacancy();
    }
}
