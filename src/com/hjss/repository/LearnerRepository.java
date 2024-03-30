package com.hjss.repository;

import com.hjss.enums.*;
import com.hjss.model.Learner;

import java.util.ArrayList;
import java.util.List;

public class LearnerRepository implements Repository<Learner, Integer> {
    private final List<Learner> db = new ArrayList<>();

    public LearnerRepository() {
        seed();
    }
    @Override
    public void seed() {
        db.add(new Learner("Tobi", Gender.Male, 5, "08148809628", Grade.ONE));
        db.add(new Learner("Emma", Gender.Female, 6, "08148809629", Grade.TWO));
        db.add(new Learner("Noah", Gender.Male, 7, "08148809630", Grade.THREE));
        db.add(new Learner("Olivia", Gender.Female, 8, "08148809631", Grade.FOUR));
        db.add(new Learner("Liam", Gender.Male, 9, "08148809632", Grade.FIVE));
        db.add(new Learner("Sophia", Gender.Female, 10, "08148809633", Grade.ONE));
        db.add(new Learner("Jackson", Gender.Male, 6, "08148809634", Grade.TWO));
        db.add(new Learner("Ava", Gender.Female, 7, "08148809635", Grade.THREE));
        db.add(new Learner("Lucas", Gender.Male, 8, "08148809636", Grade.FOUR));
        db.add(new Learner("Isabella", Gender.Female, 9, "08148809637", Grade.FIVE));
        db.add(new Learner( "James", Gender.Male, 4, "08148809638", Grade.ONE));
        db.add(new Learner("Mia", Gender.Female, 5, "08148809639", Grade.TWO));
        db.add(new Learner("Ethan", Gender.Male, 6, "08148809640", Grade.THREE));
        db.add(new Learner("Charlotte", Gender.Female, 7, "08148809641", Grade.FOUR));
        db.add(new Learner("Logan", Gender.Male, 8, "08148809642", Grade.FIVE));
    }

    @Override
    public List<Learner> read() {
        return db;
    }

    @Override
    public Learner readById(Integer id) {
        for(Learner lnr: db) {
            if(lnr.getId() == id) {
                return lnr;
            }
        }
        return null;
    }

    @Override
    public Learner create(Learner entity) throws IllegalArgumentException {
        db.add(entity);

        return entity;
    }

    /**
     * @param age Learner's age
     * @return true if age is between 4 and 11.
     */
    public boolean isValidAge(int age) {
        return age >= 4 && age <= 11;
    }
}
