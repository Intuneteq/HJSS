package com.hjss.repository;

import com.hjss.model.Booking;
import com.hjss.model.Lesson;
import com.hjss.observers.LessonObserver;

import java.util.ArrayList;
import java.util.List;

public class BookingRepository implements Repository<Booking, Integer>, LessonObserver {
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

    public void attendLesson(Booking entity) {
        entity.setAttendanceStatus();
        entity.getLesson().notifyBookingAttended();
    }

    public void cancelLesson(Booking entity) {

    }

    public void changeLesson(Booking entity, Lesson lesson) {

    }

    @Override
    public void onBookingAttended(Lesson entity) {
        // Increase lesson size when a booking is attended
        entity.incrementBySize();
    }

    @Override
    public void onBookingCancelled(Lesson entity) {
        entity.decrementBySize();
    }
}
