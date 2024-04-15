package com.hjss.repository;

import com.hjss.enums.Day;
import com.hjss.enums.Gender;
import com.hjss.enums.Grade;
import com.hjss.enums.Time;

import com.hjss.exceptions.*;
import com.hjss.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The BookingRepository class manages the persistence of booking data in the Hatfield Junior Swimming School
 * * (HJSS) application.
 * It implements the Repository interface for CRUD operations on bookings.
 */
public class BookingRepository implements Repository<Booking, Integer> {
    private final List<Booking> db = new ArrayList<>();

    /**
     * Seeds the repository with initial booking data.
     */
    @Override
    public void seed() {
        Lesson lesson1 = new Lesson(Grade.FOUR, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Badoo"));
        Lesson lesson2 = new Lesson(Grade.FIVE, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("watkins"));

        Learner learner1 = new Learner("seeder 1", Gender.Male, 10, "1234567890", Grade.FOUR);
        Learner learner2 = new Learner("seeder 2", Gender.Female, 11, "1234567890", Grade.FIVE);


        Booking booking1 = new Booking(learner1, lesson1);
        Booking booking2 = new Booking(learner2, lesson2);

        booking1.getLesson().incrementBySize();
        booking2.getLesson().incrementBySize();

        db.add(booking1);
        db.add(booking2);
    }

    /**
     * Retrieves all bookings from the repository.
     *
     * @return A list of all bookings stored in the repository.
     */
    @Override
    public List<Booking> read() {
        return db;
    }


    /**
     * Retrieves bookings associated with a specific learner.
     *
     * @param learner The learner for which bookings are to be retrieved.
     * @return A list of bookings associated with the specified learner.
     */
    public List<Booking> read(Learner learner) {
        List<Booking> bookings = new ArrayList<>();
        for (Booking booking : db) {
            if (booking.getLearner().equals(learner)) {
                bookings.add(booking);
            }
        }

        return bookings;
    }

    /**
     * Retrieves filtered bookings associated with a specific learner.
     *
     * @param learner The learner for which bookings are to be retrieved.
     * @param filter  The filter to apply to the bookings (e.g. "cancelled", "attended").
     * @return A list of filtered bookings associated with the specified learner.
     */
    public List<Booking> read(Learner learner, String filter) {
        List<Booking> bookings = new ArrayList<>();

        for (Booking booking : db) {
            if (booking.getLearner().equals(learner) && filter.equals("cancelled") && booking.getCancellationStatus()) {
                bookings.add(booking);
            } else if (booking.getLearner().equals(learner) && filter.equals("attended") && booking.getAttendanceStatus()) {
                bookings.add(booking);
            }
        }

        return bookings;
    }

    /**
     * Retrieves a booking by its unique identifier from the repository.
     *
     * @param id The unique identifier of the booking.
     * @return The booking corresponding to the given identifier, or null if not found.
     */
    @Override
    public Booking readById(Integer id) {
        for (Booking booking : db) {
            if (booking.getId() == id) {
                return booking;
            }
        }
        return null;
    }

    /**
     * Creates a new booking in the repository.
     *
     * @param entity The booking to create.
     * @return The created booking.
     * @throws GradeMisMatchException    If the grade of the learner does not match the grade of the lesson.
     * @throws DuplicateBookingException If a duplicate booking already exists.
     * @throws NoVacancyException        If there is no vacancy available for the lesson.
     */
    @Override
    public Booking create(Booking entity) throws GradeMisMatchException, DuplicateBookingException, NoVacancyException {
        // check lesson-student grade
        if (inValidGradeMatch(entity)) {
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


    /**
     * Removes all bookings from the repository.
     */
    @Override
    public void removeAll() {
        db.clear();
    }

    /**
     * Updates the attendance status of a booking to indicate that the learner has attended the lesson.
     *
     * @param entity The booking to mark as attended.
     * @return The updated booking.
     * @throws BookingCancelledException If the booking has already been cancelled.
     * @throws GradeMisMatchException    If the grade of the learner does not match the grade of the lesson.
     */
    public Booking attend(Booking entity) throws BookingCancelledException, GradeMisMatchException {
        if (entity.getAttendanceStatus()) {
            return entity;
        }

        if (entity.getCancellationStatus()) {
            throw new BookingCancelledException();
        }

        if (inValidGradeMatch(entity)) {
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

    /**
     * Cancels a booking, marking it as cancelled in the repository.
     *
     * @param entity The booking to cancel.
     * @return The updated booking.
     * @throws BookingAttendedException If the booking has already been attended.
     */
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

    /**
     * Changes a booking to a new lesson.
     *
     * @param entity    The booking to change.
     * @param newLesson The new lesson to book.
     * @return The updated booking.
     * @throws BookingAttendedException  If the booking has already been attended.
     * @throws BookingCancelledException If the booking has already been cancelled.
     * @throws NoVacancyException        If there is no vacancy available for the new lesson.
     * @throws GradeMisMatchException    If the grade of the learner does not match the grade of the new lesson.
     * @throws DuplicateBookingException If a duplicate booking already exists for the new lesson.
     */
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
        if (inValidGradeMatch(entity, newLesson)) {
            throw new GradeMisMatchException();
        }

        // Check for duplicates
        if (validateDuplicateBooking(entity.getLearner(), newLesson)) {
            throw new DuplicateBookingException();
        }

        // Decrement old lesson size
        entity.getLesson().decrementBySize();

        // Increment new lesson size
        newLesson.incrementBySize();

        // Change the lesson
        entity.setLesson(newLesson);
        return entity;
    }

    /**
     * Checks if the grade match between the lesson and the learner are invalid.
     *
     * @param entity The booking entity to validate.
     * @return True if the grade match is invalid, otherwise false.
     */
    private boolean inValidGradeMatch(Booking entity) {
        Grade lessonGrade = entity.getLesson().getGrade();
        Grade learnerGrade = entity.getLearner().getGrade();

        return lessonGrade != learnerGrade && (learnerGrade.getValue() + 1 != lessonGrade.getValue());
    }

    /**
     * Checks if the grade match between the lesson and the learner for a new lesson is invalid.
     *
     * @param entity    The booking entity to validate.
     * @param newLesson The new lesson to validate against.
     * @return True if the grade match is invalid, otherwise false.
     */
    private boolean inValidGradeMatch(Booking entity, Lesson newLesson) {
        Grade lessonGrade = newLesson.getGrade();
        Grade learnerGrade = entity.getLearner().getGrade();

        return lessonGrade != learnerGrade && (learnerGrade.getValue() + 1 != lessonGrade.getValue());
    }

    /**
     * Validates if there is a duplicate booking in the repository for the given booking entity.
     *
     * @param entity The booking entity to validate.
     * @return True if there is a duplicate booking, otherwise false.
     */
    private boolean validateDuplicateBooking(Booking entity) {
        for (Booking booking : db) {
            if (booking.getLesson().equals(entity.getLesson()) && booking.getLearner().equals(entity.getLearner())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates if there is a duplicate booking in the repository for the given learner and lesson.
     *
     * @param lnr The learner associated with the booking.
     * @param ls  The lesson associated with the booking.
     * @return True if there is a duplicate booking, otherwise false.
     */
    private boolean validateDuplicateBooking(Learner lnr, Lesson ls) {
        for (Booking booking : db) {
            if (booking.getLesson().equals(ls) && booking.getLearner().equals(lnr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is vacancy available for the given lesson.
     *
     * @param ls The lesson to check for vacancy.
     * @return True, if there is no vacancy available, otherwise false.
     */
    private boolean validateVacancy(Lesson ls) {
        return ls.getVacancy() < 1;
    }
}