package com.hjss.repository;

import com.hjss.model.Coach;
import com.hjss.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewRepository implements Repository<Review, Integer> {
    private final List<Review> db = new ArrayList<>();

    public void seed() {

    }

    public List<Review> read() {
        return db;
    }
    public List<Review> read(Coach coach) {
        List<Review> reviews = new ArrayList<>();

        for (Review review: db) {
            if(review.getBooking().getLesson().getCoach().equals(coach)) {
                reviews.add(review);
            }
        }

        return reviews;
    }

    public Review readById(Integer id) {
        return null;
    }

    public Review create(Review entity) {
        db.add(entity);
        return entity;
    }

    public float getAvgRating(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0; // Return 0 if there are no reviews
        }

        int sum = 0;
        for (Review rv : reviews) {
            sum += rv.getRating().getValue();
        }

        return (float) sum / reviews.size();
    }
}
