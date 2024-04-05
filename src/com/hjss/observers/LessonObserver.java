package com.hjss.observers;

import com.hjss.model.Lesson;

public interface LessonObserver {
    void onBookingAttended(Lesson entity);
    void onBookingCancelled(Lesson entity);
}
