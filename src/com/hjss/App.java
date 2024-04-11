package com.hjss;

import com.hjss.enums.Day;
import com.hjss.enums.Gender;
import com.hjss.enums.Grade;

import com.hjss.enums.Rating;
import com.hjss.exceptions.*;

import com.hjss.menu.*;

import com.hjss.model.*;

import com.hjss.repository.*;

import java.text.DecimalFormat;
import java.util.*;

public class App {
    private static App app;
    private final String name;
    private final LearnerRepository learnerRepository;
    private final CoachRepository coachRepository;
    private final LessonRepository lessonRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;

    private Learner learner;

    private App() {
        name = "Hatfield Junior Swimming School";
        learnerRepository = new LearnerRepository();
        coachRepository = new CoachRepository();
        lessonRepository = new LessonRepository(coachRepository);
        bookingRepository = new BookingRepository();
        reviewRepository = new ReviewRepository();
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

                handleCancelChangeBooking();
                break;
            case 3:
                handleLogin();

                handleAttendSwimmingLesson();
                break;
            case 4:
                handleShowLearnerReport();
                break;
            case 5:
                handleShowCoachReport();
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

    private void handleAttendSwimmingLesson() {
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
            booking = bookingRepository.attend(booking);
        } catch (BookingCancelledException | GradeMisMatchException e) {
            System.out.println();
            System.out.println("\u001B[31mError: " + e.getMessage() + "\u001B[0m");
            return;
        }

        System.out.println();
        System.out.println("\u001B[32mSuccess: Your Booking was Attended successfully!\u001B[0m");
        System.out.println();

        System.out.println("Booking Itinerary: ");
        System.out.println(booking);

        // Give review
        var ratingMenu = new RatingMenu();

        Rating rating = switch (ratingMenu.execute()) {
            case 1 -> Rating.One;
            case 2 -> Rating.Two;
            case 3 -> Rating.Three;
            case 4 -> Rating.Four;
            case 5 -> Rating.Five;
            default -> null;
        };

        if (rating == null) System.exit(0);

        Scanner console = new Scanner(System.in);

        System.out.print("Kindly give review feedback: ");
        String feedback = console.nextLine();

        Review review;

        review = new Review(rating, feedback, booking);

        review = reviewRepository.create(review);

        System.out.println();
        System.out.println("\u001B[32mThank you for making your review!\u001B[0m");
        System.out.println();

        System.out.println("Review Information: ");
        System.out.println(review);
    }

    /**
     * Handles the printing of the learners' report for the month at Hatfield Junior Swimming School.
     * This report includes detailed information about each learner's bookings,
     * cancellations, and attendance for the month.
     */
    private void handleShowLearnerReport() {
        System.out.println();
        System.out.println("Report For Hatfield Junior Swimming School Learners For The Month");


        // Get all Learners
        List<Learner> learners = getAppLearners();

        for (Learner lr : learners) {
            System.out.println();
            // Get Learner bookings, cancelled bookings, and attended bookings
            List<Booking> bookings = bookingRepository.read(lr);

            List<Booking> canceledBookings = bookingRepository.read(lr, "cancelled");
            List<Booking> attendedBookings = bookingRepository.read(lr, "attended");

            // Print student information and statistics
            System.out.println(lr + "\nTotal Booking: " + bookings.size() + "\nTotal Attendance: " + attendedBookings.size() + "\nTotal Cancellations: " + canceledBookings.size());

            // Print lessons booked by the learner
            System.out.println();
            System.out.println("Lessons booked by " + lr.getName() + ":");
            if (bookings.isEmpty()) {
                System.out.println();
                System.out.println("\u001B[31m" + lr.getName() + " has no lesson history\u001B[0m");
            } else {
                // Lessons
                for (Booking bk : bookings) {
                    System.out.println(bk.getLesson());
                }
            }

            System.out.println("--------------------------------------");
        }
    }

    private void handleShowCoachReport() {
        System.out.println();
        // Print each coach name,and average rating
        List<Coach> coaches = getAppCoaches();


        System.out.println("************** Coaches Review **************");
        System.out.println("----------------------------------------");
        for (Coach coach : coaches) {
            List<Review> reviews = reviewRepository.read(coach);

            float avgRating = reviewRepository.getAvgRating(reviews);
            System.out.printf("| Name: %-7s | Average Rating: %.2f | %n", coach.getName(), avgRating);

            System.out.println("----------------------------------------");
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
