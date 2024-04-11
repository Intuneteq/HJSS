package com.hjss.tests;

import com.hjss.enums.Gender;
import com.hjss.enums.Grade;
import com.hjss.exceptions.InvalidAgeException;
import com.hjss.model.Learner;

import com.hjss.repository.LearnerRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LearnerRepositoryTest {
    private LearnerRepository learnerRepository;

    private Learner testLearner;

    @BeforeEach
    void setUp() {
        learnerRepository = new LearnerRepository();
        testLearner = new Learner("Test", Gender.Female, 10, "2098766282", Grade.TWO);
    }

    @AfterEach
    void tearDown() {
        learnerRepository.removeAll();
    }

    @Test
    void testSeed() {
        List<Learner> learners = learnerRepository.read();

        // Assert 15 Learners are created when the repository is instantiated.
        assertEquals(15, learners.size());
    }

    @Test
    void testRead() {
        Learner learner = null;
        try {
            learner = learnerRepository.create(testLearner);
        } catch (InvalidAgeException e) {
            fail("Unexpected Error occurred when creating a learner to test read " + e.getMessage());
        }

        List<Learner> learners = learnerRepository.read();

        // Assert 15 Learners are created when the repository is instantiated.
        assertEquals(16, learners.size());

        assertEquals(learner, learners.getLast());
    }

    @Test
    void readById() {
        Learner learner = null;
        try {
            learner = learnerRepository.create(testLearner);
        } catch (InvalidAgeException e) {
            fail("Unexpected Error occurred when creating a learner to test read by id " + e.getMessage());
        }

        Learner getLearner = learnerRepository.readById(learner.getId());

        assertNotNull(getLearner);
        assertEquals(learner.getName(), getLearner.getName());
    }

    @Test
    void create() {
        Learner newLearner = null;
        try {
            newLearner = learnerRepository.create(testLearner);
        } catch (InvalidAgeException e) {
            fail("Unexpected Error occurred when creating a learner to test create " + e.getMessage());
        }
        List<Learner> learners = learnerRepository.read();

        // Assert db is updated
        assertTrue(learners.contains(newLearner));

        // Assert it was updated last
        assertEquals(newLearner, learners.getLast());
    }

    @Test
    void isValidAge() {
        assertTrue(learnerRepository.isValidAge(5));
        assertFalse(learnerRepository.isValidAge(3));
        assertFalse(learnerRepository.isValidAge(12));
    }
}