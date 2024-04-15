package com.hjss.repository;

import com.hjss.enums.Day;
import com.hjss.enums.Grade;
import com.hjss.enums.Time;
import com.hjss.model.Coach;
import com.hjss.model.Lesson;
import com.hjss.model.TimeSlot;

import java.util.ArrayList;
import java.util.List;

/**
 * The LessonRepository class manages the persistence of lesson data in the Hatfield Junior Swimming School
 * (HJSS) application.
 * It implements the Repository interface for CRUD operations on lessons.
 */
public class LessonRepository implements Repository<Lesson, Integer> {
    private final List<Lesson> db = new ArrayList<>();

    private final List<TimeSlot> timeSlots = new ArrayList<>();

    private final CoachRepository coachRepository;

    /**
     * Initializes the LessonRepository with a reference to the CoachRepository and seeds it with initial lesson data.
     *
     * @param coachRepository The CoachRepository instance to be used for retrieving coaches.
     */
    public LessonRepository(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
        seed();
    }

    /**
     * Seeds the repository with initial lesson data.
     */
    @Override
    public void seed() {
        createTimeSlots();

        createLessons();
    }

    /**
     * Retrieves all lessons from the repository.
     *
     * @return A list of all lessons stored in the repository.
     */
    @Override
    public List<Lesson> read() {
        return db;
    }

    /**
     * Retrieves lessons scheduled for a specific day.
     *
     * @param day The day for which lessons are to be retrieved.
     * @return A list of lessons scheduled for the specified day.
     */
    public List<Lesson> read(Day day) {
        List<Lesson> lessons = new ArrayList<>();
        for (Lesson ls : db) {
            if (ls.getTimeSlot().day().equals(day)) {
                lessons.add(ls);
            }
        }

        return lessons;
    }

    /**
     * Retrieves lessons for a specific grade.
     *
     * @param grade The grade for which lessons are to be retrieved.
     * @return A list of lessons scheduled for the specified grade.
     */
    public List<Lesson> read(Grade grade) {
        List<Lesson> lessons = new ArrayList<>();
        for (Lesson ls : db) {
            if (ls.getGrade().equals(grade)) {
                lessons.add(ls);
            }
        }

        return lessons;
    }

    /**
     * Retrieves lessons coached by a specific coach.
     *
     * @param coach The coach for which lessons are to be retrieved.
     * @return A list of lessons coached by the specified coach.
     */
    public List<Lesson> read(Coach coach) {
        List<Lesson> lessons = new ArrayList<>();
        for (Lesson ls : db) {
            if (ls.getCoach().equals(coach)) {
                lessons.add(ls);
            }
        }

        return lessons;
    }

    /**
     * Retrieves a lesson by its unique identifier from the repository.
     *
     * @param id The unique identifier of the lesson.
     * @return The lesson corresponding to the given identifier, or null if not found.
     */
    @Override
    public Lesson readById(Integer id) {
        for (Lesson ls : db) {
            if (ls.getId() == id) {
                return ls;
            }
        }
        return null;
    }

    /**
     * Creates a new lesson in the repository.
     *
     * @param entity The lesson to create.
     * @return The created lesson.
     */
    @Override
    public Lesson create(Lesson entity) {
        db.add(entity);

        return entity;
    }

    /**
     * Removes all lessons from the repository.
     */
    @Override
    public void removeAll() {
        db.clear();
        timeSlots.clear();
    }

    /**
     * Creates time slots for each day and time combination.
     */
    private void createTimeSlots() {
        Day[] days = Day.values();
        Time[] lessonTime = Time.values();

        for (Day day : days) {
            for (Time time : lessonTime) {
                // Create Time Slot
                TimeSlot timeSlot = new TimeSlot(day, time);

                // Validate day and time match are correctly created
                if (timeSlot.day() != null || timeSlot.time() != null) {
                    timeSlots.add(timeSlot);
                }
            }
        }
    }

    /**
     * Creates lessons based on available time slots and coaches.
     * 44 Lessons are created and added to the lesson list.
     */
    private void createLessons() {
        // Get application coaches
        List<Coach> coaches = coachRepository.read();

        // Get all grades
        Grade[] grades = Grade.values();

        // Total number of HJSS grades
        int numGrades = grades.length;

        // Total number of coaches
        int numCoaches = coaches.size();

        // Initialize enum index for grade and Time
        int gradeIndex = 0, coachIndex = 0;


        // Loop 4 times to create 44 lessons - 11 a week totalling 4 weeks.
        for (int j = 0; j < 4; j++) {
            // Create a lesson for each time slot
            for (TimeSlot timeSlot : timeSlots) {
                Grade grade = grades[gradeIndex];
                Coach coach = coaches.get(coachIndex);

                // Create and Add new Lesson
                db.add(new Lesson(grade, timeSlot, coach));

                // Increment indices using modular arithmetic
                gradeIndex = (gradeIndex + 1) % numGrades;
                coachIndex = (coachIndex + 1) % numCoaches;
            }
        }

    }

    /**
     * Generates a formatted timetable string based on the provided list of lessons.
     *
     * @param lessons The list of lessons to generate the timetable from.
     * @return The formatted timetable string.
     */
    public String showTimeTable(List<Lesson> lessons) {
        StringBuilder s = new StringBuilder();

        int currentWeek = -1;

        for (Lesson ls : lessons) {
            // Calculate the week number based on the lesson ID
            // Since each week consists of 11 lessons,
            // Divide the lesson ID by 11 and add 1 to get the week number.
            int week = (ls.getId() - 1) / 11 + 1;

            // Check if the calculated week number is different from the current week
            if (week != currentWeek) {
                // If it is, append the week header to the StringBuilder and update the current week number.
                s.append("Week ").append(week).append(":\n");
                currentWeek = week;
            }

            // Append the lesson details as usual.
            s.append(ls).append("\n\n");
        }

        return s.toString();
    }
}
