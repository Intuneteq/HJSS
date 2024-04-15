package com.hjss.menu;

import com.hjss.App;
import com.hjss.model.Coach;
import com.hjss.model.Learner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CoachMenu extends Menu {
    private final List<Coach> coaches;
    private final Set<Integer> coachIds;

    public CoachMenu() {
        App app = App.getInstance();
        coaches = app.getAppCoaches();

        // Initialize the learnerIds set and populate it with learner IDs
        coachIds = new HashSet<>();
        for (Coach coach : coaches) {
            coachIds.add(coach.getId());
        }

    }

    /**
     * Displays the options to the user.
     */
    @Override
    protected void print() {
        System.out.println();
        System.out.println("************** Choose Coach **************");
        for (Coach coach : coaches) {
            System.out.println("[" + padToTwoDigits(coach.getId()) + "]: " + coach.getName());
        }
    }

    /**
     * Checks if the input is a valid option.
     *
     * @param input The input to validate.
     * @return True if the input is valid, otherwise false.
     */
    @Override
    protected boolean isValidOption(int input) {
        return coachIds.contains(input);
    }
}
