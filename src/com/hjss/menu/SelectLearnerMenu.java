package com.hjss.menu;


import com.hjss.App;
import com.hjss.model.Learner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The SelectLearnerMenu class represents a menu for selecting a learner for login.
 * It displays a list of learners and allows the user to choose one for login.
 */
public class SelectLearnerMenu extends Menu {

    private final List<Learner> learners; // List of all available learners
    private final Set<Integer> learnerIds; // Set of learner IDs

    /**
     * Constructs a SelectLearnerMenu object.
     * Initializes the list of learners and their IDs.
     */
    public SelectLearnerMenu() {
        // Get app instance
        App app = App.getInstance();

        // Retrieve all learners registered to the application
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
            System.out.println("[" + App.padToTwoDigits(lnr.getId()) + "]: " + lnr.getName());
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
