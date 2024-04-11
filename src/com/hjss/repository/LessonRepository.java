package com.hjss.repository;

import com.hjss.enums.Day;
import com.hjss.enums.Grade;
import com.hjss.enums.Time;
import com.hjss.model.Coach;
import com.hjss.model.Lesson;
import com.hjss.model.TimeSlot;

import java.util.ArrayList;
import java.util.List;

public class LessonRepository implements Repository<Lesson, Integer> {
    private final List<Lesson> db = new ArrayList<>();

    private final List<TimeSlot> timeSlots = new ArrayList<>();

    private final CoachRepository coachRepository;

    public LessonRepository(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
        seed();
    }

    @Override
    public void seed() {
        createTimeSlots();

        createLessons();
    }

    @Override
    public List<Lesson> read() {
        return db;
    }

    public List<Lesson> read(Day day) {
        List<Lesson> lessons = new ArrayList<>();
        for (Lesson ls : db) {
            if (ls.getTimeSlot().day().equals(day)) {
                lessons.add(ls);
            }
        }

        return lessons;
    }

    public List<Lesson> read(Grade grade) {
        List<Lesson> lessons = new ArrayList<>();
        for (Lesson ls : db) {
            if (ls.getGrade().equals(grade)) {
                lessons.add(ls);
            }
        }

        return lessons;
    }

    public List<Lesson> read(Coach coach) {
        List<Lesson> lessons = new ArrayList<>();
        for (Lesson ls : db) {
            if (ls.getCoach().equals(coach)) {
                lessons.add(ls);
            }
        }

        return lessons;
    }

    @Override
    public Lesson readById(Integer id) {
        for (Lesson ls : db) {
            if (ls.getId() == id) {
                return ls;
            }
        }
        return null;
    }

    @Override
    public Lesson create(Lesson entity) {
        db.add(entity);

        return entity;
    }

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
     * Create 44 Lessons and add them to the lesson list.
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

    public String showTimeTable(List<Lesson> lessons) {
        StringBuilder s = new StringBuilder();

        int currentWeek = -1;

        for (Lesson ls : lessons) {
            // Calculate the week number based on the lesson ID
            // Since each week consists of 11 lessons,
            // We divide the lesson ID by 11 and add 1 to get the week number.
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
