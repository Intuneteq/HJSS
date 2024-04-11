package com.hjss.repository;

import com.hjss.enums.Grade;
import com.hjss.exceptions.*;
import com.hjss.model.Booking;
import com.hjss.model.Learner;
import com.hjss.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class BookingRepository implements Repository<Booking, Integer> {
    private final List<Booking> db = new ArrayList<>();

    @Override
    public void seed() {

    }

    @Override
    public List<Booking> read() {
        return db;
    }

    public List<Booking> read(Learner learner) {
        List<Booking> bookings = new ArrayList<>();
        for (Booking booking : db) {
            if (booking.getLearner().equals(learner)) {
                bookings.add(booking);
            }
        }

        return bookings;
    }

    @Override
    public Booking readById(Integer id) {
        for (Booking booking : db) {
            if (booking.getId() == id) {
                return booking;
            }
        }
        return null;
    }

    @Override
    public Booking create(Booking entity) throws GradeMisMatchException, DuplicateBookingException, NoVacancyException {
        // check lesson-student grade
        if (validateGradeMatch(entity)) {
            throw new GradeMisMatchException();
        }

        // check lesson vacancy;
        if (validateVacancy(entity.getLesson())) {
            throw new NoVacancyException();
        }

        // check for a duplicate booking
        if (validateDuplicateBooking(entity)) {
            throw new DuplicateBookingException();
        }

        // Reduce lesson vacancy
        entity.getLesson().incrementBySize();

        db.add(entity);

        return entity;
    }

    public Booking attend(Booking entity) throws BookingCancelledException, GradeMisMatchException {
        if (entity.getAttendanceStatus()) {
            return entity;
        }

        if (entity.getCancellationStatus()) {
            throw new BookingCancelledException();
        }

        if (validateGradeMatch(entity)) {
            // Learner's grade has been updated since they last booked the lesson
            // So we cancel the booking and free up lesson vacancy
            entity.setCancellationStatus();

            // Then throw a grade mismatch error
            throw new GradeMisMatchException();
        }

        // Ensure learner's grade is still in range of lesson grade
        entity.setAttendanceStatus();

        return entity;
    }

    public Booking cancel(Booking entity) throws BookingAttendedException {
        // Booking Attended?
        if (entity.getAttendanceStatus()) {
            // Throw Booking attended exception
            throw new BookingAttendedException();
        }

        // Booking previously cancelled?
        if (entity.getCancellationStatus()) {
            return entity;
        }

        // Cancel the booking
        entity.setCancellationStatus();

        return entity;
    }

    public Booking change(Booking entity, Lesson newLesson) throws BookingAttendedException, BookingCancelledException, NoVacancyException, GradeMisMatchException, DuplicateBookingException {
        // Booking Attended?
        if (entity.getAttendanceStatus()) {
            throw new BookingAttendedException();
        }

        // Booking cancelled?
        if (entity.getCancellationStatus()) {
            throw new BookingCancelledException();
        }

        // Vacancy in the new lesson?
        if (validateVacancy(newLesson)) {
            throw new NoVacancyException();
        }

        // Learner's grade match new lesson's grade?
        if (!validateGradeMatch(entity, newLesson)) {
            throw new GradeMisMatchException();
        }

        // Check for duplicates
        if (validateDuplicateBooking(entity.getLearner(), newLesson)) {
            throw new DuplicateBookingException();
        }

        // Change the lesson
        entity.setLesson(newLesson);
        return entity;
    }

    private boolean validateGradeMatch(Booking entity) {
        Grade lessonGrade = entity.getLesson().getGrade();
        Grade learnerGrade = entity.getLearner().getGrade();

        // learner grade == lesson grade or lesson grade > learner grade by 1
        return lessonGrade != learnerGrade && (learnerGrade.getValue() + 1 != lessonGrade.getValue());
    }

    private boolean validateGradeMatch(Booking entity, Lesson newLesson) {
        Grade lessonGrade = newLesson.getGrade();
        Grade learnerGrade = entity.getLearner().getGrade();

        // learner grade == lesson grade or lesson grade > learner grade by 1
        return lessonGrade == learnerGrade || (learnerGrade.getValue() + 1 == lessonGrade.getValue());
    }

    private boolean validateDuplicateBooking(Booking entity) {
        for (Booking booking : db) {
            if (booking.getLesson().equals(entity.getLesson()) && booking.getLearner().equals(entity.getLearner())) {
                return true;
            }
        }
        return false;
    }

    private boolean validateDuplicateBooking(Learner lnr, Lesson ls) {
        for (Booking booking : db) {
            if (booking.getLesson().equals(ls) && booking.getLearner().equals(lnr)) {
                return true;
            }
        }
        return false;
    }

    private boolean validateVacancy(Lesson ls) {
        return ls.getVacancy() < 1;
    }
}

// Implement an observer
// to cancel all bookings lower than their current grade when a learner attends a lesson with a higher grade