package com.hjss;

import com.hjss.enums.Gender;
import com.hjss.enums.Grade;

import com.hjss.exceptions.InvalidAgeException;

import com.hjss.menu.*;

import com.hjss.model.Coach;
import com.hjss.model.Learner;

import com.hjss.repository.CoachRepository;
import com.hjss.repository.LearnerRepository;
import com.hjss.repository.LessonRepository;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {
    private static App app;
    private final String name;
    private final LearnerRepository learnerRepository;
    private final CoachRepository coachRepository;
    private final LessonRepository lessonRepository;

    private Learner learner;

    private App() {
        name = "Hatfield Junior Swimming School";
        learnerRepository = new LearnerRepository();
        coachRepository = new CoachRepository();
        lessonRepository = new LessonRepository(coachRepository);
    }

    public static App getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;
    }

    public void start() {
        welcome();

        do run(); while (true);
    }

    public String getName() {
        return name;
    }

    private void welcome() {
        System.out.println(" ");
        System.out.println("==================================================================");
        System.out.println("*********** Welcome to " + getName() + " ***********");
        System.out.println("==================================================================");
    }

    private void run() {
        var mainMenu = new MainMenu();

        int input = mainMenu.execute();

        switch (input) {
            case 1:
                // Ensure we have the details of user prompting the application.
                handleLogin();

                handleBookASwimmingLesson();
                break;
            case 2:
                // Ensure we have the details of user prompting the application.
//                setAppUser(learnerService.login());
//
//                handleCancelChangeBooking();
                break;
            case 3:
                // Ensure we have the details of user prompting the application.
//                setAppUser(learnerService.login());
//
//                handleAttendSwimmingLesson();
                break;
            case 4:
//                handleShowLearnerReport();
                break;
            case 5:
//                handleShowCoachReport();
                break;
            case 6:
                handleRegistration();
                break;
            default:
                System.exit(0);
        }
    }

    private void handleRegistration() {
        System.out.println();
        System.out.println("************** Register A New Learner **************");

        Scanner console = new Scanner(System.in);
        String name;
        int age = 0;
        Gender gender;
        Grade grade;
        String contactNumber;
        int input;

        boolean isValidAge = false;

        System.out.print("Enter Name: ");
        name = console.nextLine();

        System.out.print("Enter Emergency Contact Number: ");
        contactNumber = console.nextLine();

        do {
            try {
                System.out.print("Enter Age: ");
                age = console.nextInt();

                if (!learnerRepository.isValidAge(age)) {
                    throw new InvalidAgeException();
                }

                isValidAge = true; // Set isValidAge to true if no exception is thrown
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31mError: Please enter a valid age.\u001B[0m");
                console.nextLine(); // Clear the buffer
            } catch (InvalidAgeException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
                console.nextLine(); // Clear the buffer
            }
        } while (!isValidAge);

        var genderMenu = new GenderMenu();
        input = genderMenu.execute();

        gender = switch (input) {
            case 1 -> Gender.Male;
            case 2 -> Gender.Female;
            default -> null;
        };

        var gradeMenu = new GradeMenu();
        input = gradeMenu.execute();

        grade = switch (input) {
            case 1 -> Grade.ONE;
            case 2 -> Grade.TWO;
            case 3 -> Grade.THREE;
            case 4 -> Grade.FOUR;
            case 5 -> Grade.FIVE;
            default -> null;
        };

        Learner learner = learnerRepository.create(new Learner(name, gender, age, contactNumber, grade));

        System.out.println();
        System.out.println("\u001B[32mSuccess: Your Registration was completed successfully!\u001B[0m");
        System.out.println();

        System.out.println(learner);
    }

    private void handleLogin() {
        var selectMenu = new SelectLearnerMenu();

        int input = selectMenu.execute();

        if (input == 0) System.exit(0);

        Learner learner = learnerRepository.readById(input);

        // It should never run but just being safe!
        if (learner == null) {
            System.out.println();
            System.out.println("\u001B[31mError: Learner Not Found.\u001B[0m");
            return;
        }

        // Set active learner
        setLearner(learner);

        System.out.println();
        System.out.println("You are logged in as: ");
        System.out.println(learner);
    }

    private void handleBookASwimmingLesson() {
        var bookLessonMenu = new BookLessonMenu();

        int input = bookLessonMenu.execute();

        System.out.println();
        System.out.println(lessonRepository.read());
    }


    public List<Learner> getAppLearners() {
        return this.learnerRepository.read();
    }

    public List<Coach> getAppCoaches() {
        return coachRepository.read();
    }

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }
}
