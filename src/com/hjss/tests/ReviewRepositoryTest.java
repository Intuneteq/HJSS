package com.hjss.tests;

import com.hjss.enums.*;
import com.hjss.model.*;

import com.hjss.repository.ReviewRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReviewRepositoryTest {
    private ReviewRepository reviewRepository;
    private Coach testCoach;
    private Review testReview;

    @BeforeEach
    void setUp() {
        testCoach = new Coach("Badoo");
        Lesson testLesson = new Lesson(Grade.FOUR, new TimeSlot(Day.MONDAY, Time.FOUR), testCoach);
        Learner testLearner = new Learner("seeder 1", Gender.Male, 10, "1234567890", Grade.FOUR);
        Booking testBooking = new Booking(testLearner, testLesson);

        testReview = new Review(Rating.Four, "Thank You!", testBooking);
        reviewRepository = new ReviewRepository();
    }

    @AfterEach
    void tearDown() {
        reviewRepository.removeAll();
    }

    @Test
    void testCreate() {
        Review review = reviewRepository.create(testReview);

        assertNotNull(review);

        List<Review> reviews = reviewRepository.read();

        assertEquals(review, reviews.getFirst());
    }

    @Test
    void testReadByCoach() {
        // create a review
        Review review = reviewRepository.create(testReview);

        List<Review> reviews = reviewRepository.read(testCoach);

        assertEquals(1, reviews.size());

        assertEquals(testReview, reviews.getFirst());
    }

    @Test
    void testGetAvgRating_WithNoReviews() {
        // Given
        List<Review> emptyReviews = new ArrayList<>();

        // When
        float avgRating = reviewRepository.getAvgRating(emptyReviews);

        // Then
        assertEquals(0, avgRating);
    }

    @Test
    void testGetAvgRating_WithSingleReview() {
        // Given
        List<Review> reviews = new ArrayList<>();

        reviews.add(testReview);

        // When
        float avgRating = reviewRepository.getAvgRating(reviews);

        // Then
        assertEquals(4.0f, avgRating);
    }

    @Test
    void testGetAvgRating_WithMultipleReviews() {
        Learner learner = new Learner("New Learner", Gender.Female, 8, "9876543210", Grade.ONE);

        // Create a lesson
        Lesson lesson = new Lesson(Grade.TWO, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Watkins"));
//
        // Given
        List<Review> reviews = new ArrayList<>();

        reviews.add(new Review(Rating.One, "Thank You 1!", new Booking(learner, lesson)));
        reviews.add(new Review(Rating.Two, "Thank You 2!", new Booking(learner, lesson)));
        reviews.add(new Review(Rating.Three, "Thank You 3!", new Booking(learner, lesson)));
        reviews.add(testReview);
        reviews.add(new Review(Rating.Five, "Thank You 4!", new Booking(learner, lesson)));

        // When
        float avgRating = reviewRepository.getAvgRating(reviews);

        // Then
        assertEquals(3.0f, avgRating);
    }

}