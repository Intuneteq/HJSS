package com.hjss.menu;

import com.hjss.enums.Rating;

/**
 * Menu to choose booking rating
 */
public class RatingMenu extends Menu {
    protected void print() {
        System.out.println();
        System.out.println("************** Kindly Rate Your Lesson **************");
        System.out.println("[1]: " + Rating.One.getDescription());
        System.out.println("[2]: " + Rating.Two.getDescription());
        System.out.println("[3]: " + Rating.Three.getDescription());
        System.out.println("[4]: " + Rating.Four.getDescription());
        System.out.println("[5]: " + Rating.Five.getDescription());
    }

    protected boolean isValidOption(int input) {
        return input >= 0 && input <= 5;
    }
}
