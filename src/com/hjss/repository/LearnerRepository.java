package com.hjss.repository;

import com.hjss.enums.*;

import com.hjss.exceptions.InvalidAgeException;

import com.hjss.model.Learner;

import java.util.ArrayList;
import java.util.List;

/**
 * The LearnerRepository class manages the persistence of learner data in the Hatfield Junior Swimming School
 * (HJSS) application.
 * It implements the Repository interface for CRUD operations on learners.
 */
public class LearnerRepository implements Repository<Learner, Integer> {
    private final List<Learner> db = new ArrayList<>();

    /**
     * Initializes the LearnerRepository and seeds it with initial learner data.
     */
    public LearnerRepository() {
        seed();
    }

    /**
     * Seeds the repository with initial learner data.
     */
    @Override
    public void seed() {
        // Add initial learner data to the repository
        db.add(new Learner("Tobi", Gender.Male, 5, "08148809628", Grade.ONE));
        db.add(new Learner("Emma", Gender.Female, 6, "08148809629", Grade.TWO));
        db.add(new Learner("Noah", Gender.Male, 7, "08148809630", Grade.THREE));
        db.add(new Learner("Olivia", Gender.Female, 8, "08148809631", Grade.FOUR));
        db.add(new Learner("Liam", Gender.Male, 9, "08148809632", Grade.FIVE));
        db.add(new Learner("Sophia", Gender.Female, 10, "08148809633", Grade.ONE));
        db.add(new Learner("Jackson", Gender.Male, 6, "08148809634", Grade.TWO));
        db.add(new Learner("Ava", Gender.Female, 7, "08148809635", Grade.THREE));
        db.add(new Learner("Lucas", Gender.Male, 8, "08148809636", Grade.FOUR));
        db.add(new Learner("Isabella", Gender.Female, 9, "08148809637", Grade.FIVE));
        db.add(new Learner("James", Gender.Male, 4, "08148809638", Grade.ONE));
        db.add(new Learner("Mia", Gender.Female, 5, "08148809639", Grade.TWO));
        db.add(new Learner("Ethan", Gender.Male, 6, "08148809640", Grade.THREE));
        db.add(new Learner("Charlotte", Gender.Female, 7, "08148809641", Grade.FOUR));
        db.add(new Learner("Logan", Gender.Male, 8, "08148809642", Grade.FIVE));
    }

    /**
     * Retrieves all learners from the repository.
     *
     * @return A list of all learners stored in the repository.
     */
    @Override
    public List<Learner> read() {
        return db;
    }

    /**
     * Retrieves a learner by their unique identifier from the repository.
     *
     * @param id The unique identifier of the learner.
     * @return The learner corresponding to the given identifier, or null if not found.
     */
    @Override
    public Learner readById(Integer id) {
        for (Learner lnr : db) {
            if (lnr.getId() == id) {
                return lnr;
            }
        }
        return null;
    }

    /**
     * Creates a new learner in the repository.
     *
     * @param entity The learner to create.
     * @return The created learner.
     * @throws InvalidAgeException If the age of the learner is invalid (not between 4 and 11).
     */
    @Override
    public Learner create(Learner entity) throws InvalidAgeException {
        if (isValidAge(entity.getAge())) {
            db.add(entity);
            return entity;
        }
        throw new InvalidAgeException();
    }

    /**
     * Removes all learners from the repository.
     */
    public void removeAll() {
        db.clear();
    }

    /**
     * Checks if a given age is within the valid range for learners (4 to 11 years old).
     *
     * @param age The age to validate.
     * @return True if the age is within the valid range, false otherwise.
     */
    public boolean isValidAge(int age) {
        return age >= 4 && age <= 11;
    }
}
