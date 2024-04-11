package com.hjss.tests;

import com.hjss.model.Coach;
import com.hjss.repository.CoachRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoachRepositoryTest {

    private CoachRepository coachRepository;
    private Coach testCoach;

    @BeforeEach
    void setUp() {
        coachRepository = new CoachRepository();
        testCoach = new Coach("Test Coach");
    }

    @AfterEach
    void tearDown() {
        coachRepository.removeAll();
    }

    @Test
    void seed() {
        coachRepository.seed();

        List<Coach> coaches = coachRepository.read();

        // 4 more coaches should be seeded - so, 4 + 4 = 8
        assertEquals(8, coaches.size());
    }

    @Test
    void read() {
        List<Coach> coaches = coachRepository.read();

        // Should return the 4 seeded coaches
        assertEquals(4, coaches.size());

        // The first coach name should be cheng
        assertEquals("Cheng", coaches.getFirst().getName());

        // The last coach name should be Badoo
        assertEquals("Badoo", coaches.getLast().getName());
    }

    @Test
    void readById() {
        Coach coach = coachRepository.readById(coachRepository.create(testCoach).getId());

        // Read coach should be the test coach
        assertNotNull(coach);
        assertEquals(testCoach, coach);
    }

    @Test
    void create() {
        Coach coach = coachRepository.create(testCoach);

        // Assert coach not null
        assertNotNull(coach);

        // Assert coach created match testCoach
        assertEquals(testCoach, coach);

        // Assert testCoach was added to the db
        assertEquals(testCoach, coachRepository.read().getLast());
    }
}