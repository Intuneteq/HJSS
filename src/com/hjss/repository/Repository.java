package com.hjss.repository;

import com.hjss.exceptions.DuplicateBookingException;
import com.hjss.exceptions.GradeMisMatchException;
import com.hjss.exceptions.InvalidAgeException;
import com.hjss.exceptions.NoVacancyException;

import java.util.List;

/**
 * The Repository interface provides a template for repository classes in the Hatfield Junior Swimming School (HJSS) application.
 * Repositories are responsible for managing data storage and retrieval operations.
 *
 * @param <T> The type of entity stored in the repository.
 * @param <K> The type of the entity's identifier.
 */
public interface Repository<T, K> {
    /**
     * Seeds the repository with initial data.
     */
    void seed();

    /**
     * Retrieves all entities from the repository.
     *
     * @return A list of all entities stored in the repository.
     */
    List<T> read();

    /**
     * Retrieves an entity by its unique identifier from the repository.
     *
     * @param id The unique identifier of the entity.
     * @return The entity corresponding to the given identifier, or null if not found.
     */
    T readById(K id);

    /**
     * Creates a new entity in the repository.
     *
     * @param entity The entity to create.
     * @return The created entity.
     * @throws GradeMisMatchException    If there is a grade mismatch during the creation process.
     * @throws DuplicateBookingException If a duplicate booking is detected during the creation process.
     * @throws NoVacancyException        If there is no vacancy available for the booking.
     * @throws InvalidAgeException       If the age provided for the entity is invalid.
     */
    T create(T entity) throws GradeMisMatchException, DuplicateBookingException, NoVacancyException, InvalidAgeException;

    /**
     * Removes all entities from the repository.
     */
    void removeAll();
}
