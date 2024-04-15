package com.hjss.model;

import com.hjss.App;
import com.hjss.enums.Grade;

/**
 * The Lesson class represents a swimming lesson.
 * It contains information such as the lesson ID, grade, time slot, coach, and current capacity.
 */
public class Lesson {
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

    /**
     * Total number booked
     */
    private int size;

    /**
     * Constructs a Lesson object with the specified grade, time slot, and coach.
     *
     * @param grade    The grade level of the lesson.
     * @param timeSlot The time slot for the lesson.
     * @param coach    The coach assigned to the lesson.
     */
    public Lesson(Grade grade, TimeSlot timeSlot, Coach coach) {
        this.id = ++count;
        this.grade = grade;
        this.coach = coach;
        this.timeSlot = timeSlot;
        size = 0;
    }


    /**
     * Retrieves the lesson ID.
     *
     * @return The lesson ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the current lesson capacity.
     *
     * @return The current lesson capacity.
     */
    public int getSize() {
        return size;
    }

    /**
     * Calculates and retrieves the current vacancy for the lesson.
     *
     * @return The current vacancy for the lesson.
     */
    public int getVacancy() {
        return 4 - getSize();
    }

    /**
     * Retrieves the grade level of the lesson.
     *
     * @return The grade level of the lesson.
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Retrieves the time slot for the lesson.
     *
     * @return The time slot for the lesson.
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Retrieves the coach assigned to the lesson.
     *
     * @return The coach assigned to the lesson.
     */
    public Coach getCoach() {
        return coach;
    }

    /**
     * Decrements the current capacity of the lesson by one.
     */
    public void decrementBySize() {
        if (size > 0) {
            size -= 1;
        }
    }

    /**
     * Increments the current capacity of the lesson by one.
     */
    public void incrementBySize() {
        if (size < 4) {
            size += 1;
        }
    }

    public String toString() {
        return "Id: " + App.padToTwoDigits(getId()) +
                " | " +
                "Day: " + getTimeSlot().day() +
                " | " +
                "Time: " + getTimeSlot().time().getValue() +
                " | " +
                "Grade: " + getGrade() +
                " | " +
                "Coach: " + getCoach().getName() +
                " | " +
                "Vacancy: " + getVacancy();
    }
}
