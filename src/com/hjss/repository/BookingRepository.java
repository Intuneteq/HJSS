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
        if (!validateGradeMatch(entity)) {
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

        db.add(entity);

        return entity;
    }

    public Booking attendLesson(Booking entity) {
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
        Grade learnerGradeOneHigher = Grade.values()[learnerGrade.getValue() + 1 + 1];

        return lessonGrade == learnerGrade || lessonGrade == learnerGradeOneHigher;
    }

    private boolean validateGradeMatch(Booking entity, Lesson newLesson) {
        Grade lessonGrade = newLesson.getGrade();
        Grade learnerGrade = entity.getLearner().getGrade();
        Grade learnerGradeOneHigher = Grade.values()[learnerGrade.getValue() + 1 + 1];

        return lessonGrade == learnerGrade || lessonGrade == learnerGradeOneHigher;
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
