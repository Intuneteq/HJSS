package com.hjss.repository;

import com.hjss.model.Coach;

import java.util.ArrayList;
import java.util.List;

/**
 * The CoachRepository class manages the persistence of coach data in the Hatfield Junior Swimming School
 * (HJSS) application.
 * It implements the Repository interface for CRUD operations on coaches.
 */
public class CoachRepository implements Repository<Coach, Integer> {
    private final List<Coach> db = new ArrayList<>();

    /**
     * Initializes the CoachRepository and seeds it with initial coach data.
     */
    public CoachRepository() {
        seed();
    }

    /**
     * Seeds the repository with initial coach data.
     */
    @Override
    public void seed() {
        // Add initial coach data to the repository
        db.add(new Coach("Cheng"));
        db.add(new Coach("Yar"));
        db.add(new Coach("Watkins"));
        db.add(new Coach("Badoo"));
    }

    /**
     * Retrieves all coaches from the repository.
     *
     * @return A list of all coaches stored in the repository.
     */
    @Override
    public List<Coach> read() {
        return db;
    }

    /**
     * Retrieves a coach by their unique identifier from the repository.
     *
     * @param id The unique identifier of the coach.
     * @return The coach corresponding to the given identifier, or null if not found.
     */
    @Override
    public Coach readById(Integer id) {
        for (Coach ch : db) {
            if (ch.getId() == id) {
                return ch;
            }
        }
        return null;
    }

    /**
     * Creates a new coach in the repository.
     *
     * @param entity The coach to create.
     * @return The created coach.
     */
    @Override
    public Coach create(Coach entity) {
        db.add(entity);

        return entity;
    }

    /**
     * Removes all coaches from the repository.
     */
    @Override
    public void removeAll() {
        db.clear();
    }
}
