package com.hjss.repository;

import com.hjss.model.Review;

import java.util.ArrayList;
import java.util.List;

public class reviewRepository implements Repository<Review, Integer> {
    private final List<Review> db = new ArrayList<>();

    public void seed() {

    }

    public List<Review> read() {
        return db;
    }

    public Review readById(Integer id) {
        return null;
    }

    public Review create(Review entity) {
        db.add(entity);
        return entity;
    }
}
