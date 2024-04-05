package com.hjss.model;


import com.hjss.enums.Day;
import com.hjss.enums.Time;

public record TimeSlot(Day day, Time time) {
    public TimeSlot(Day day, Time time) {
        // Check if the time is "3-4pm" or "4-5pm" or "6-7pm" and the day is Monday, Wednesday, or Friday
        if (isValidWeekday(day, time)) {
            this.day = day;
            this.time = time;
        }

        // Check if time is "2-3pm" and day is Saturday
        else if (isValidWeekend(day, time)) {
            this.day = day;
            this.time = time;
        } else {
            // Initialize day and time to null
            this.day = null;
            this.time = null;
        }
        // For any other combination, do nothing (implicitly creates a null TimeSlot)
    }

    public static boolean isValidWeekend(Day day, Time time) {
        return day == Day.SATURDAY && (time == Time.TWO || time == Time.THREE);
    }

    public static boolean isValidWeekday(Day day, Time time) {
        return (time == Time.FOUR || time == Time.FIVE || time == Time.SIX) && (day == Day.MONDAY || day == Day.WEDNESDAY || day == Day.FRIDAY);
    }

    @Override
    public String toString() {
        return "Day: " + day.toString() + ", Time: " + time.getValue();
    }
}
