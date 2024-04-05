package com.hjss.repository;

import com.hjss.model.Coach;

import java.util.ArrayList;
import java.util.List;

public class CoachRepository implements Repository<Coach, Integer> {
    private final List<Coach> db = new ArrayList<>();

    public CoachRepository() {
        seed();
    }

    @Override
    public void seed() {
        db.add(new Coach("Cheng"));
        db.add(new Coach("Yar"));
        db.add(new Coach("Watkins"));
        db.add(new Coach("Badoo"));
    }

    @Override
    public List<Coach> read() {
        return db;
    }

    @Override
    public Coach readById(Integer id) {
        for (Coach ch : db) {
            if (ch.getId() == id) {
                return ch;
            }
        }
        return null;
    }

    @Override
    public Coach create(Coach entity) {
        db.add(entity);

        return entity;
    }
}
