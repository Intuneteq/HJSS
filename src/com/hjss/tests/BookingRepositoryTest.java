package com.hjss.tests;

import com.hjss.enums.Day;
import com.hjss.enums.Gender;
import com.hjss.enums.Grade;
import com.hjss.enums.Time;
import com.hjss.exceptions.*;
import com.hjss.model.*;

import com.hjss.repository.BookingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingRepositoryTest {
    private BookingRepository bookingRepository;
    private Booking testBooking1;
    private Learner testLearner;

    private Lesson testLesson;

    @BeforeEach
    void setUp() {
        bookingRepository = new BookingRepository();
        // Seed repository
        bookingRepository.seed();

        testBooking1 = bookingRepository.read().getFirst();

        testLearner = bookingRepository.read().getFirst().getLearner();

        testLesson = bookingRepository.read().getFirst().getLesson();
    }

    @AfterEach
    void tearDown() {
        bookingRepository.removeAll();
    }

    @Test
    void testRead() {

        // Read bookings
        List<Booking> bookings = bookingRepository.read();

        // Assert not null
        assertNotNull(bookings);

        // Assert not empty
        assertFalse(bookings.isEmpty());

        // Assert there are two bookings in db - The 2 are added when the db is seeded
        assertEquals(2, bookings.size());
    }

    @Test
    void testReadByLearner() {
        List<Booking> bookings = bookingRepository.read(testLearner);

        // Assert result is not null
        assertNotNull(bookings);

        // Assert the list is not empty
        assertFalse(bookings.isEmpty());

        // Assert there is only 1 booking for testLearner1
        assertEquals(1, bookings.size());

        // Assert it belongs to testLearner1
        for (Booking booking : bookings) {
            assertEquals(testLearner, booking.getLearner());
        }

    }

    @Test
    void testReadByLearnerAndFilter() {
        List<Booking> attendedBookings = bookingRepository.read(testLearner, "attended");

        assertNotNull(attendedBookings);

        // No bookings attended in seed data
        assertTrue(attendedBookings.isEmpty());
    }

    @Test
    void testReadByLearnerAndFilterWithAttendedBookings() {
        // Attend the booking;
        testBooking1.setAttendanceStatus();

        List<Booking> attendedBookings = bookingRepository.read(testLearner, "attended");

        // Assert that a booking is retrieved
        assertFalse(attendedBookings.isEmpty());

        // Assert it belongs to testLearner1
        for (Booking booking : attendedBookings) {
            assertEquals(testLearner, booking.getLearner());
            assertEquals(testLearner.getGrade(), booking.getLesson().getGrade());

            assertTrue(booking.getAttendanceStatus());
        }
    }

    @Test
    void testReadByLearnerAndFilterWithCancelledBookings() {
        // Attend the booking;
        testBooking1.setCancellationStatus();

        List<Booking> cancelledBookings = bookingRepository.read(testLearner, "cancelled");

        // Assert that a booking is retrieved
        assertFalse(cancelledBookings.isEmpty());

        // Assert the lesson vacancy is decremented
        assertEquals(4, testBooking1.getLesson().getVacancy());

        // Assert it belongs to testLearner1
        for (Booking booking : cancelledBookings) {
            assertEquals(testLearner, booking.getLearner());
            assertTrue(booking.getCancellationStatus());
        }
    }

    @Test
    void testReadById() {
        Booking foundBooking = bookingRepository.readById(testBooking1.getId());
        assertNotNull(foundBooking);
        assertEquals(testBooking1, foundBooking);
    }

    @Test
    void testCreateThrowGradMismatchException() {
        // Create a new learner
        Learner learner = new Learner("New Learner", Gender.Female, 8, "9876543210", Grade.ONE);

        // Create a lesson
        Lesson lesson = new Lesson(Grade.FIVE, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));

        assertThrows(GradeMisMatchException.class, () -> bookingRepository.create(new Booking(learner, lesson)));
    }

    @Test
    void testCreateThrowNoVacancyException() {
        // Create a new learner
        Learner learner = new Learner("New Learner", Gender.Female, 8, "9876543210", Grade.FIVE);

        // Create a lesson
        Lesson lesson = new Lesson(Grade.FIVE, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));

        lesson.incrementBySize();
        lesson.incrementBySize();
        lesson.incrementBySize();
        lesson.incrementBySize();

        assertThrows(NoVacancyException.class, () -> bookingRepository.create(new Booking(learner, lesson)));
    }

    @Test
    void testCreateThrowNoDuplicateBookingException() {
        assertThrows(DuplicateBookingException.class, () -> bookingRepository.create(new Booking(testBooking1.getLearner(), testBooking1.getLesson())));
    }

    @Test
    void testCreate() {
        // Create a new learner
        Learner learner = new Learner("New Learner", Gender.Female, 8, "9876543210", Grade.FIVE);

        // Create a lesson
        Lesson lesson = new Lesson(Grade.FIVE, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));

        try {
            // Create a booking
            Booking newBooking = bookingRepository.create(new Booking(learner, lesson));

            // Check if booking is created
            assertNotNull(newBooking);

            // Check if the booking is added to the repository
            List<Booking> bookings = bookingRepository.read();
            assertTrue(bookings.contains(newBooking));

            // Ensure lesson size was incremented
            assertEquals(3, newBooking.getLesson().getVacancy());
        } catch (Exception e) {
            fail("Unexpected Error occurred when creating booking: " + e.getMessage());
        }
    }

    @Test
    void testCreateOneGradeHigher() {
        // Create a new learner
        Learner learner = new Learner("New Learner", Gender.Female, 8, "9876543210", Grade.FOUR);

        // Create a lesson
        Lesson lesson = new Lesson(Grade.FIVE, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));

        try {
            // Create a booking
            Booking newBooking = bookingRepository.create(new Booking(learner, lesson));

            // Check if booking is created
            assertNotNull(newBooking);

            // Check if the booking is added to the repository
            List<Booking> bookings = bookingRepository.read();
            assertTrue(bookings.contains(newBooking));

            // Ensure lesson size was incremented
            assertEquals(3, newBooking.getLesson().getVacancy());
        } catch (Exception e) {
            fail("Unexpected Error occurred when creating booking: " + e.getMessage());
        }
    }

    @Test
    void testAttend() {

        try {
            Booking booking = bookingRepository.attend(testBooking1);

            // Assert true
            assertTrue(booking.getAttendanceStatus());

            assertEquals(booking.getLesson().getGrade(), testLearner.getGrade());

        } catch (GradeMisMatchException | BookingCancelledException e) {
            fail("Unexpected Error occurred when attending booking: " + e.getMessage());
        }
    }

    @Test
    void testAttendThrowGradeMismatchException() {
        // Create a new learner
        Learner learner = new Learner("New Learner", Gender.Female, 8, "9876543210", Grade.ONE);

        // Create a lesson
        Lesson lesson1 = new Lesson(Grade.ONE, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));
        Lesson lesson2 = new Lesson(Grade.TWO, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));

        try {
            // Create a booking with grade one
            Booking newBooking1 = bookingRepository.create(new Booking(learner, lesson1));

            // Create a booking with grade two
            Booking newBooking2 = bookingRepository.create(new Booking(learner, lesson2));

            // Attend grade two booking. Student grade will be upgraded to two
            bookingRepository.attend(newBooking2);

            // Attempt to attend grade one booking should throw and error
            assertThrows(GradeMisMatchException.class, () -> bookingRepository.attend(newBooking1));

        } catch (Exception e) {
            fail("Unexpected Error occurred when creating booking: " + e.getMessage());
        }
    }

    @Test
    void testAttendThrowBookingCancelledException() {
        // Create a new learner
        Learner learner = new Learner("New Learner", Gender.Female, 8, "9876543210", Grade.ONE);

        // Create a lesson
        Lesson lesson1 = new Lesson(Grade.ONE, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));

        try {
            // Create a booking with grade one
            Booking newBooking1 = bookingRepository.create(new Booking(learner, lesson1));

            // Cancel the booking
            newBooking1.setCancellationStatus();

            // Attempt to attend cancelled
            assertThrows(BookingCancelledException.class, () -> bookingRepository.attend(newBooking1));

        } catch (Exception e) {
            fail("Unexpected Error occurred when creating booking: " + e.getMessage());
        }
    }

    @Test
    void testCancel() {
        try {
            Booking cancelledBooking = bookingRepository.cancel(testBooking1);

            assertNotNull(cancelledBooking);

            assertTrue(cancelledBooking.getCancellationStatus());

            // Assert lesson vacancy is freed up by one
            assertEquals(4, cancelledBooking.getLesson().getVacancy());

        } catch (Exception e) {
            fail("Unexpected Error occurred when cancelling booking: " + e.getMessage());
        }
    }

    @Test
    void testCancelThrowBookingAttendedException() {
        try {
            // Attend the booking
            testBooking1.setAttendanceStatus();

            // Attempt to cancel the booking
            assertThrows(BookingAttendedException.class, () -> bookingRepository.cancel(testBooking1));

        } catch (Exception e) {
            fail("Unexpected Error occurred when cancelling booking with booking attended exception: " + e.getMessage());
        }
    }

    @Test
    void testChange() {
        Lesson newLesson = new Lesson(Grade.FOUR, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));

        try {
            Lesson oldLesson = testBooking1.getLesson(); // Hold reference to the previous lesson

            Booking changedBooking = bookingRepository.change(testBooking1, newLesson);

            assertEquals(newLesson, changedBooking.getLesson());

            // Assert new lesson vacancy decremented
            assertEquals(3, newLesson.getVacancy());

            // Assert old lesson vacancy incremented
            assertEquals(4, oldLesson.getVacancy());

        } catch (Exception e) {
            fail("Unexpected Error occurred when change booking: " + e.getMessage());
        }
    }

    @Test
    void testChangeThrowABookingAttendedException() {
        Lesson newLesson = new Lesson(Grade.FOUR, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));

        try {
            // Attend lesson
            testBooking1.setAttendanceStatus();

            // Attempt to change the booking
            assertThrows(BookingAttendedException.class, () -> bookingRepository.change(testBooking1, newLesson));

        } catch (Exception e) {
            fail("Unexpected Error occurred when change booking throw booking attended exception: " + e.getMessage());
        }
    }

    @Test
    void testChangeThrowBookingCancelledException() {
        Lesson newLesson = new Lesson(Grade.FOUR, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));

        try {
            // Attend lesson
            testBooking1.setCancellationStatus();

            // Attempt to change the booking
            assertThrows(BookingCancelledException.class, () -> bookingRepository.change(testBooking1, newLesson));

        } catch (Exception e) {
            fail("Unexpected Error occurred when change booking throw booking cancelled exception: " + e.getMessage());
        }
    }

    @Test
    void testChangeThrowNoVacancyException() {
        Lesson newLesson = new Lesson(Grade.FOUR, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));

        try {
            // Increment new Lesson to empty vacancy
            newLesson.incrementBySize();
            newLesson.incrementBySize();
            newLesson.incrementBySize();
            newLesson.incrementBySize();

            // Attempt to change the booking
            assertThrows(NoVacancyException.class, () -> bookingRepository.change(testBooking1, newLesson));

        } catch (Exception e) {
            fail("Unexpected Error occurred when change booking throw no vacancy exception: " + e.getMessage());
        }
    }

    @Test
    void testChangeThrowGradeMismatchException() {
        Lesson newLesson = new Lesson(Grade.ONE, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));

        try {
            // Attempt to change the booking
            assertThrows(GradeMisMatchException.class, () -> bookingRepository.change(testBooking1, newLesson));

        } catch (Exception e) {
            fail("Unexpected Error occurred when change booking throw grade mismatch exception: " + e.getMessage());
        }
    }

    @Test
    void testChangeThrowDuplicateBookingException() {
        try {
            // Attempt to change the booking
            assertThrows(DuplicateBookingException.class, () -> bookingRepository.change(testBooking1, testBooking1.getLesson()));

        } catch (Exception e) {
            fail("Unexpected Error occurred when change booking throw grade mismatch exception: " + e.getMessage());
        }
    }

    @Test
    void testValidateGradeMatch() {
        try {

            // Create a new learner
            Learner falseLearner = new Learner("New Learner", Gender.Female, 8, "9876543210", Grade.ONE);

            // Create a lesson
            Lesson falseLesson = new Lesson(Grade.THREE, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));

            Method inValidGradeMatch = BookingRepository.class.getDeclaredMethod("inValidGradeMatch", Booking.class);
            inValidGradeMatch.setAccessible(true);

            // Invoke the private method with a test booking
            boolean isValid = (boolean) inValidGradeMatch.invoke(bookingRepository, testBooking1);
            boolean isInValid = (boolean) inValidGradeMatch.invoke(bookingRepository, new Booking(falseLearner, falseLesson));

            // Assert the results
            assertTrue(isInValid); // Method will return a true if it is inValid
            assertFalse(isValid); // Method will return a false if it is valid
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail("Unexpected Error occurred when invoking validateGradeMatch method: " + e.getMessage());
        }
    }

    @Test
    void testValidateGradeMatchWithNewLesson() {
        try {

            // Create a new learner
            Learner falseLearner = new Learner("New Learner", Gender.Female, 8, "9876543210", Grade.ONE);

            // Create a lesson
            Lesson falseLesson = new Lesson(Grade.THREE, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));

            Lesson newLesson = new Lesson(Grade.FOUR, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));

            Method inValidGradeMatch = BookingRepository.class.getDeclaredMethod("inValidGradeMatch", Booking.class, Lesson.class);
            inValidGradeMatch.setAccessible(true);

            // Invoke the private method with a test booking
            boolean isValid = (boolean) inValidGradeMatch.invoke(bookingRepository, testBooking1, newLesson);

            boolean isInValid = (boolean) inValidGradeMatch.invoke(bookingRepository, new Booking(falseLearner, falseLesson), newLesson);

            // Assert the results
            assertTrue(isInValid); // Method will return a true if it is inValid
            assertFalse(isValid); // Method will return a false if it is valid
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail("Unexpected Error occurred when invoking validateGradeMatch method: " + e.getMessage());
        }
    }

    @Test
    void testValidateDuplicateBooking() {
        try {
            Method validateDuplicateBookingMethod = BookingRepository.class.getDeclaredMethod("validateDuplicateBooking", Booking.class);
            validateDuplicateBookingMethod.setAccessible(true);

            // Create a test booking with the same learner and lesson as one of the existing bookings in db
            Booking testBooking = new Booking(testLearner, testLesson);

            // Invoke the private method with the test booking
            boolean isDuplicate = (boolean) validateDuplicateBookingMethod.invoke(bookingRepository, testBooking);

            // Assert the result
            assertTrue(isDuplicate);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail("Unexpected Error occurred when invoking validateDuplicateBooking method: " + e.getMessage());
        }
    }

    @Test
    void testValidateVacancy() {
        try {
            Method validateVacancyMethod = BookingRepository.class.getDeclaredMethod("validateVacancy", Lesson.class);
            validateVacancyMethod.setAccessible(true);

            // Create a test lesson with vacancy less than 1
            Lesson testLesson = new Lesson(Grade.FOUR, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Coach"));

            // Decrement vacancy by increasing the size
            testLesson.incrementBySize();
            testLesson.incrementBySize();
            testLesson.incrementBySize();
            testLesson.incrementBySize();

            // Invoke the private method with the test lesson
            boolean isVacant = (boolean) validateVacancyMethod.invoke(bookingRepository, testLesson);

            // Assert the result
            assertTrue(isVacant);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail("Unexpected Error occurred when invoking validateVacancy method: " + e.getMessage());
        }
    }
}