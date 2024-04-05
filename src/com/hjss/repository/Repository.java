package com.hjss.repository;

import com.hjss.model.Learner;
import com.hjss.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public interface Repository<T, K> {
    void seed();
    List<T> read();
    T readById(K id);
    T create(T entity);

    void onBookingAttended(Lesson entity);
}
