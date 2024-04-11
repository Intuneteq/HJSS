package com.hjss.menu;


import com.hjss.App;
import com.hjss.model.Learner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectLearnerMenu extends Menu {

    private final List<Learner> learners;
    private final Set<Integer> learnerIds;

    public SelectLearnerMenu() {
        App app = App.getInstance();
        learners = app.getAppLearners();

        // Initialize the learnerIds set and populate it with learner IDs
        learnerIds = new HashSet<>();
        for (Learner learner : learners) {
            learnerIds.add(learner.getId());
        }
    }

    /**
     * Displays the options to the user.
     */
    @Override
    protected void print() {
        System.out.println();
        System.out.println("************** Login A Learner **************");
        for (Learner lnr : learners) {
            System.out.println("[" + padToTwoDigits(lnr.getId()) + "]: " + lnr.getName());
        }
        System.out.println("[0]: Exit");
    }

    /**
     * Checks if the input is a valid option.
     *
     * @param input The input to validate.
     * @return True if the input is valid, otherwise false.
     */
    @Override
    protected boolean isValidOption(int input) {
        return input == 0 || learnerIds.contains(input);
    }
}
