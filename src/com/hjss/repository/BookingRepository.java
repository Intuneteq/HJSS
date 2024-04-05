package com.hjss.repository;

import com.hjss.model.Booking;
import com.hjss.model.Coach;

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
    public Booking create(Booking entity) {
        // check lesson-student grade
        // check lesson vacancy;
        // check for a duplicate booking

        db.add(entity);

        return entity;
    }


}
