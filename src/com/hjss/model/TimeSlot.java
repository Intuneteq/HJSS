package com.hjss.model;


import com.hjss.enums.Day;
import com.hjss.enums.Time;

/**
 * The TimeSlot record represents a time slot for a swimming lesson.
 * It includes the day of the week and the time of the lesson.
 */
public record TimeSlot(Day day, Time time) {

    /**
     * Constructs a TimeSlot object with the specified day and time.
     *
     * @param day  The day of the week.
     * @param time The time of the lesson.
     */
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
    }

    /**
     * Checks if the specified day and time combination is valid for weekdays (Monday, Wednesday, or Friday).
     *
     * @param day  The day of the week.
     * @param time The time of the lesson.
     * @return true if the combination is valid for weekdays, otherwise false.
     */
    public static boolean isValidWeekday(Day day, Time time) {
        return (time == Time.FOUR || time == Time.FIVE || time == Time.SIX)
                &&
                (day == Day.MONDAY || day == Day.WEDNESDAY || day == Day.FRIDAY);
    }

    /**
     * Checks if the specified day and time combination is valid for weekends (Saturday).
     *
     * @param day  The day of the week.
     * @param time The time of the lesson.
     * @return true if the combination is valid for weekends, otherwise false.
     */
    public static boolean isValidWeekend(Day day, Time time) {
        return day == Day.SATURDAY && (time == Time.TWO || time == Time.THREE);
    }

    @Override
    public String toString() {
        return "Day: " +
                day.toString() +
                ", Time: " + time.getValue();
    }
}
