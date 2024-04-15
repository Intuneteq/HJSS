package com.hjss.model;

import com.hjss.enums.Gender;
import com.hjss.enums.Grade;

/**
 * The Learner class represents a learner in the swimming school.
 * It contains information such as the learner's ID, name, gender, age, emergency contact number, and grade.
 */
public class Learner {
    static int count = 0;

    /**
     * Learner's ID
     */
    private final int id;

    /**
     * Learner's Name
     */
    private final String name;

    /**
     * Lender's gender
     */
    private final Gender gender;

    /**
     * Learner's age
     */
    private final int age;

    private final String emergencyContactNumber;

    private Grade grade;

    /**
     * Constructs a Learner object with the specified name, gender, age, contact number, and grade.
     *
     * @param name          The learner's name.
     * @param gender        The learner's gender.
     * @param age           The learner's age.
     * @param contactNumber The learner's emergency contact phone number (e.g. parent's phone number).
     * @param grade         The learner's current grade.
     */
    public Learner(String name, Gender gender, int age, String contactNumber, Grade grade) {
        this.id = ++count;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.emergencyContactNumber = contactNumber;
        this.grade = grade;
    }

    /**
     * Retrieves the learner's gender.
     *
     * @return The learner's gender.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Retrieves the learner's name.
     *
     * @return The learner's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the learner's ID.
     *
     * @return The learner's ID.
     */
    public int getId() {
        return id;
    }


    /**
     * Retrieves the learner's age.
     *
     * @return The learner's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Retrieves the learner's emergency contact phone number.
     *
     * @return The learner's emergency contact phone number.
     */
    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    /**
     * Retrieves the learner's current grade.
     *
     * @return The learner's current grade.
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Sets the learner's grade.
     *
     * @param grade The new grade to set for the learner.
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    /**
     * Returns a string representation of the Learner object.
     *
     * @return A string representation of the Learner object.
     */
    public String toString() {
        return "id: " + getId() +
                "\nname: " + getName() +
                "\nGender: " + getGender().name() +
                "\nEmergency Contact Number: " + getEmergencyContactNumber() +
                "\nAge: " + getAge() +
                "\nGrade: " + getGrade().name();
    }
}