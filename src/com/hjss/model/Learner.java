package com.hjss.model;

import com.hjss.enums.Gender;
import com.hjss.enums.Grade;

public class Learner {
    static int count = 0;

    /**
     * Learner's id.
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

    public Learner(String name, Gender gender, int age, String contactNumber, Grade grade) {
        this.id = ++count;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.emergencyContactNumber = contactNumber;
        this.grade = grade;
    }

    /**
     * @return Gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * @return Learner's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Learner's ID
     */
    public int getId() {
        return id;
    }

    /**
     * @return Learner's age
     */
    public int getAge() {
        return age;
    }

    /**
     * @return Learner's emergency contact phone number. E.g. Parent's phone number
     */
    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    /**
     * @return Learner's current grade.
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * @param grade Promote Learner
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }



    public String toString() {
        return "id: " + getId() + "\nname: " + getName() + "\nGender: " + getGender().name() + "\nEmergency Contact Number: " + getEmergencyContactNumber() + "\nAge: " + getAge() + "\nGrade: " + getGrade().name();
    }
}