package com.hjss.repository;

import com.hjss.exceptions.DuplicateBookingException;
import com.hjss.exceptions.GradeMisMatchException;
import com.hjss.exceptions.NoVacancyException;
import com.hjss.model.Learner;
import com.hjss.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public interface Repository<T, K> {
    void seed();
    List<T> read();
    T readById(K id);
    T create(T entity) throws GradeMisMatchException, DuplicateBookingException, NoVacancyException;
}
