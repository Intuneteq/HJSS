package com.hjss;

import com.hjss.enums.Day;
import com.hjss.enums.Gender;
import com.hjss.enums.Grade;

import com.hjss.exceptions.*;

import com.hjss.menu.*;

import com.hjss.model.Booking;
import com.hjss.model.Coach;
import com.hjss.model.Learner;

import com.hjss.model.Lesson;
import com.hjss.repository.BookingRepository;
import com.hjss.repository.CoachRepository;
import com.hjss.repository.LearnerRepository;
import com.hjss.repository.LessonRepository;

import java.text.DecimalFormat;
import java.util.*;

public class App {
    private static App app;
    private final String name;
    private final LearnerRepository learnerRepository;
    private final CoachRepository coachRepository;
    private final LessonRepository lessonRepository;
    private final BookingRepository bookingRepository;

    private Learner learner;

    private App() {
        name = "Hatfield Junior Swimming School";
        learnerRepository = new LearnerRepository();
        coachRepository = new CoachRepository();
        lessonRepository = new LessonRepository(coachRepository);
        bookingRepository = new BookingRepository();
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
                handleLogin();

                handleBookASwimmingLesson();
                break;
            case 2:
                handleLogin();
//
                handleCancelChangeBooking();
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
        int input;
        var bookLessonMenu = new BookLessonMenu();

        input = bookLessonMenu.execute();

        List<Lesson> lessons = switch (input) {
            case 1 -> handleBookByDay();
            case 2 -> handleBookByCoach();
            case 3 -> handleBookByGrade();
            default -> null;
        };

        if (lessons == null) System.exit(0);

        String timeTable = lessonRepository.showTimeTable(lessons);

        Set<Integer> lessonIds = new HashSet<>();

        for (Lesson lesson : lessons) {
            lessonIds.add(lesson.getId());
        }

        var timeTableMenu = new TimeTableMenu(timeTable, lessonIds);

        int id = timeTableMenu.execute();

        Lesson lesson = lessonRepository.readById(id);

        Booking booking = null;

        try {
            booking = bookingRepository.create(new Booking(getLearner(), lesson));
        } catch (GradeMisMatchException | DuplicateBookingException | NoVacancyException e) {
            System.out.println();
            System.out.println("\u001B[31mError: " + e.getMessage() + "\u001B[0m");

            // Recursively prompt the user to retry booking
            handleBookASwimmingLesson();
            return;
        }

        System.out.println();
        System.out.println("\u001B[32mSuccess: Your Booking was completed successfully!\u001B[0m");
        System.out.println();

        System.out.println("Booking Itinerary: ");
        System.out.println(booking);
    }

    private void handleCancelChangeBooking() {
        var cancelChangeMenu = new CancelChangeMenu();
        int input = cancelChangeMenu.execute();

        switch (input) {
            case 1:
                handleCancelBooking();
                break;
            case 2:
                handleChangeBooking();
                break;
            default:
                System.exit(0);
        }
    }

    private List<Lesson> handleBookByDay() {
        var dayMenu = new DayMenu();

        int input = dayMenu.execute();

        Day day = switch (input) {
            case 1 -> Day.MONDAY;
            case 2 -> Day.WEDNESDAY;
            case 3 -> Day.FRIDAY;
            case 4 -> Day.SATURDAY;
            default -> null;
        };

        return lessonRepository.read(day);
    }

    private List<Lesson> handleBookByCoach() {
        var coachMenu = new CoachMenu();

        int id = coachMenu.execute();

        Coach coach = coachRepository.readById(id);

        return lessonRepository.read(coach);
    }

    private List<Lesson> handleBookByGrade() {
        var gradeMenu = new GradeMenu();

        int input = gradeMenu.execute();

        Grade grade = switch (input) {
            case 1 -> Grade.ONE;
            case 2 -> Grade.TWO;
            case 3 -> Grade.THREE;
            case 4 -> Grade.FOUR;
            case 5 -> Grade.FIVE;
            default -> null;
        };

        return lessonRepository.read(grade);
    }

    private void handleCancelBooking() {
        Booking booking = null;
        List<Booking> bookings = bookingRepository.read(getLearner());

        if (bookings.isEmpty()) {
            System.out.println();
            System.out.println("\u001B[32mBooking List is currently empty!\u001B[0m");
            return;
        }

        var bookingMenu = new BookingMenu(bookings);

        int bookingId = bookingMenu.execute();

        booking = bookingRepository.readById(bookingId);

        if (booking == null) {
            System.out.println("\u001B[31mError: Booking Not Found!\u001B[0m");
            return;
        }

        try {
            booking = bookingRepository.cancel(booking);
        } catch (BookingAttendedException e) {
            System.out.println("\u001B[31mError: " + e.getMessage() + "\u001B[0m");
            return;
        }

        System.out.println();
        System.out.println("\u001B[32mSuccess: Your Booking was Cancelled successfully!\u001B[0m");
        System.out.println();

        System.out.println("Booking Itinerary: ");
        System.out.println(booking);

    }

    private void handleChangeBooking() {
        Booking booking = null;
        List<Booking> bookings = bookingRepository.read(getLearner());

        if (bookings.isEmpty()) {
            System.out.println();
            System.out.println("\u001B[32mBooking List is currently empty!\u001B[0m");
            return;
        }

        var bookingMenu = new BookingMenu(bookings);

        int bookingId = bookingMenu.execute();

        booking = bookingRepository.readById(bookingId);

        if (booking == null) {
            System.out.println("\u001B[31mError: Booking Not Found!\u001B[0m");
            return;
        }

        // Find A new Lesson
        var bookLessonMenu = new BookLessonMenu();

        int lessonId = bookLessonMenu.execute();

        List<Lesson> lessons = switch (lessonId) {
            case 1 -> handleBookByDay();
            case 2 -> handleBookByCoach();
            case 3 -> handleBookByGrade();
            default -> null;
        };

        if (lessons == null) System.exit(0);

        String timeTable = lessonRepository.showTimeTable(lessons);

        Set<Integer> lessonIds = new HashSet<>();

        for (Lesson lesson : lessons) {
            lessonIds.add(lesson.getId());
        }

        var timeTableMenu = new TimeTableMenu(timeTable, lessonIds);

        int id = timeTableMenu.execute();

        Lesson lesson = lessonRepository.readById(id);

        try {
            booking = bookingRepository.change(booking, lesson);
        } catch (BookingAttendedException | BookingCancelledException | NoVacancyException | GradeMisMatchException |
                 DuplicateBookingException e) {
            System.out.println("\u001B[31mError: " + e.getMessage() + "\u001B[0m");
            return;
        }

        System.out.println();
        System.out.println("\u001B[32mSuccess: Your Booking was Changed successfully!\u001B[0m");
        System.out.println();

        System.out.println("Booking Itinerary: ");
        System.out.println(booking);
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

    public static String padToTwoDigits(double number) {
        DecimalFormat df = new DecimalFormat("00");
        return df.format(number);
    }
}
