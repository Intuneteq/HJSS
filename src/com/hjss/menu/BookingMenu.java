package com.hjss.menu;

import com.hjss.model.Booking;
import com.hjss.model.Lesson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookingMenu extends Menu {
    List<Booking> bookings;
    private final Set<Integer> ids = new HashSet<>();

    public BookingMenu(List<Booking> bookings) {
        this.bookings = bookings;

        for (Booking booking : bookings) {
            ids.add(booking.getId());
        }
    }

    protected void print() {
        System.out.println();
        System.out.println("************** Select a Booking **************");
        for (Booking booking : bookings) {
            System.out.println(booking);
            System.out.println();
        }
    }

    protected boolean isValidOption(int input) {
        return ids.contains(input);
    }
}
