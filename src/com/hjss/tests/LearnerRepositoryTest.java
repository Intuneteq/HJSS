package com.hjss.tests;

import com.hjss.enums.Gender;
import com.hjss.enums.Grade;
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
        Learner learner = learnerRepository.create(testLearner);

        List<Learner> learners = learnerRepository.read();

        // Assert 15 Learners are created when the repository is instantiated.
        assertEquals(16, learners.size());

        assertEquals(learner, learners.getLast());
    }

    @Test
    void readById() {
        Learner learner = learnerRepository.create(testLearner);

        Learner getLearner = learnerRepository.readById(learner.getId());

        assertNotNull(getLearner);
        assertEquals(learner.getName(), getLearner.getName());
    }

    @Test
    void create() {
        Learner newLearner = learnerRepository.create(testLearner);
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