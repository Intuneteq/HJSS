package com.hjss;

import com.hjss.enums.*;

import com.hjss.exceptions.*;

import com.hjss.menu.*;

import com.hjss.model.*;

import com.hjss.repository.*;

import java.text.DecimalFormat;
import java.util.*;

/**
 * The App class represents the main application instance.
 * It follows the singleton design pattern to ensure that only one instance of the application is created.
 *
 * <p>
 * This class is designed to be open for expansion. In case of a franchise or multiple instances of the application running concurrently,
 * more instances of the App class can be created and run in the main class. Each instance can have its own state and manage its own set of data
 * independently from other instances, providing a scalable solution for managing multiple instances of the application.
 * </p>
 */
public class App {
    /**
     * Application instance,
     * following the singleton design pattern to ensure only one instance of the application is created.
     */
    private static App app;

    /**
     * The name of the application.
     */
    private final String name;

    /**
     * Repository for managing operations related to learners.
     */
    private final LearnerRepository learnerRepository;

    /**
     * Repository for managing operations related to coaches.
     */
    private final CoachRepository coachRepository;

    /**
     * Repository for managing operations related to lessons.
     */
    private final LessonRepository lessonRepository;

    /**
     * Repository for managing operations related to bookings.
     */
    private final BookingRepository bookingRepository;

    /**
     * Repository for managing operations related to reviews.
     */
    private final ReviewRepository reviewRepository;

    /**
     * The learner currently interacting with the application.
     */
    private Learner learner;

    private final Scanner console;


    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the name and all repository instances required by the application.
     */
    private App() {
        name = "Hatfield Junior Swimming School";
        learnerRepository = new LearnerRepository();
        coachRepository = new CoachRepository();
        lessonRepository = new LessonRepository(coachRepository);
        bookingRepository = new BookingRepository();
        reviewRepository = new ReviewRepository();
        console = new Scanner(System.in);
    }

    /**
     * Returns the singleton instance of the App class.
     * If the instance doesn't exist, it creates one.
     *
     * @return The singleton instance of the App class.
     */
    public static App getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;
    }

    /**
     * Starts the application by displaying a welcome message and initiating the main menu loop.
     * The main menu loop continues running until the user manually terminates the application.
     */
    public void start() {
        // Display welcome message
        welcome();

        // Initiate the main menu loop
        do run(); while (true);
    }

    /**
     * Retrieves the name of the application.
     *
     * @return The name of the application.
     */
    public String getName() {
        return name;
    }

    /**
     * Displays a welcome message upon starting the application.
     * The welcome message includes the name of the application.
     */
    private void welcome() {
        System.out.println(" ");
        System.out.println("==================================================================");
        System.out.println("*********** Welcome to " + getName() + " ***********");
        System.out.println("==================================================================");
    }

    /**
     * Runs the main menu functionality of the application.
     * It displays the main menu, executes the selected menu option, and handles the corresponding actions.
     */
    private void run() {
        // Create a new instance of the main menu
        var mainMenu = new MainMenu();

        // Execute the main menu and retrieve user input
        int input = mainMenu.execute();

        // Branch based on the user input
        switch (input) {
            // Case 1: Book a swimming lesson
            case 1:
                // Handle user login
                handleLogin();
                // Proceed to book a swimming lesson
                handleBookASwimmingLesson();
                break;

            // Case 2: Cancel or change a booking
            case 2:
                // Handle user login
                handleLogin();
                // Proceed to cancel or change a booking
                handleCancelChangeBooking();
                break;

            // Case 3: Attend a swimming lesson
            case 3:
                // Handle user login
                handleLogin();
                // Proceed to attend a swimming lesson
                handleAttendSwimmingLesson();
                break;

            // Case 4: Show monthly learners reports
            case 4:
                // Display the learner report
                handleShowLearnerReport();
                break;

            // Case 5: Show monthly coaches reports
            case 5:
                // Display the coach report
                handleShowCoachReport();
                break;

            // Case 6: Register a new learner
            case 6:
                // Proceed to handle registration
                handleRegistration();
                break;

            // Default: Exit the application
            default:
                // Terminate the application
                System.exit(0);
        }
    }

    /**
     * Handles the registration process for a new learner.
     * It prompts the user for necessary learner information and registers the learner.
     */
    private void handleRegistration() {
        System.out.println();
        System.out.println("************** Register A New Learner **************");

        String name;
        String contactNumber;

        int age = 0;

        Grade grade;
        Gender gender;

        boolean isValidAge = false;

        // Get the user's name
        name = promptName();

        // Get the user's contact number
        contactNumber = promptContactNumber();

        // Get the user's age - Validate the age also.
        age = getAge(age, isValidAge);

        // Get the user's gender
        gender = getGender();

        // Get the user's grade
        grade = getGrade();

        try {
            // Create the new learner and add to the list of the application learners.
            Learner learner = learnerRepository.create(new Learner(name, gender, age, contactNumber, grade));

            System.out.println();
            System.out.println("\u001B[32mSuccess: Your Registration was completed successfully!\u001B[0m");
            System.out.println();

            System.out.println(learner);
        } catch (InvalidAgeException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }

    /**
     * Handles the login process for a learner.
     * It prompts the user to select a learner from the list of available learners,
     * sets the selected learner as the active learner, and displays a confirmation message.
     */
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

    /**
     * Handles the process of booking a swimming lesson.
     * It prompts the user to select a booking method (by day, by coach, or by grade),
     * displays the available lessons based on the selected method,
     * prompts the user to choose a lesson from the displayed list,
     * creates a booking for the selected lesson, and displays the booking itinerary.
     */
    private void handleBookASwimmingLesson() {
        // Prompt the user for the lesson to be booked
        Lesson lesson = getLesson();

        if (lesson == null) return;

        Booking booking;

        try {
            // Create a booking for the selected lesson
            booking = bookingRepository.create(new Booking(getLearner(), lesson));
        } catch (GradeMisMatchException | DuplicateBookingException | NoVacancyException e) {
            System.out.println();
            System.out.println("\u001B[31mError: " + e.getMessage() + "\u001B[0m");

            // Recursively prompt the user to retry booking
            handleBookASwimmingLesson();
            return;
        }

        // Display a success message and the booking itinerary
        System.out.println();
        System.out.println("\u001B[32mSuccess: Your Booking was completed successfully!\u001B[0m");
        System.out.println();

        System.out.println("Booking Itinerary: ");
        System.out.println(booking);
    }

    /**
     * Handles the process of cancelling or changing a booking.
     * It prompts the user to select an option (cancel booking or change booking),
     * retrieves the booking or lesson based on the user's selection,
     * performs the canceling or changing operation, and displays the result.
     */
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

    /**
     * Handles the process of attending a swimming lesson.
     * It prompts the user to select a booking, marks the booking as attended,
     * allows the user to provide a review, and creates the review in the repository.
     */
    private void handleAttendSwimmingLesson() {
        // Retrieve booking for attendance
        Booking booking = getBooking();

        // End process when no booking found
        if (booking == null) return;

        try {
            // Mark the booking as attended
            booking = bookingRepository.attend(booking);
        } catch (BookingCancelledException | GradeMisMatchException e) {
            // Handle exception cases
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
     * Displays a report for Hatfield Junior Swimming School learners for the month.
     * Retrieves information about learners, their bookings, cancellations, and attendances,
     * and prints out relevant statistics and details.
     */
    private void handleShowLearnerReport() {
        System.out.println();
        System.out.println("****** Report For Hatfield Junior Swimming School Learners For The Month ******");


        // Get all Learners
        List<Learner> learners = getAppLearners();

        for (Learner lr : learners) {
            System.out.println();
            // Get Learner bookings, cancelled bookings, and attended bookings
            List<Booking> bookings = bookingRepository.read(lr);
            List<Booking> canceledBookings = bookingRepository.read(lr, "cancelled");
            List<Booking> attendedBookings = bookingRepository.read(lr, "attended");

            String stats = lr.toString() +
                    "\nTotal Booking: " + bookings.size() +
                    "\nTotal Attendance: " + attendedBookings.size() +
                    "\nTotal Cancellations: " + canceledBookings.size();

            // Print student information and statistics
            System.out.println(stats);

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

    /**
     * Displays a report for coaches at Hatfield Junior Swimming School, including their names
     * and average ratings based on reviews.
     */
    private void handleShowCoachReport() {
        System.out.println();

        // Retrieve all coaches
        List<Coach> coaches = getAppCoaches();

        // Print header for the coaches review section
        System.out.println("************** Coaches Review **************");
        System.out.println("----------------------------------------");
        for (Coach coach : coaches) {
            // Retrieve reviews for the coach
            List<Review> reviews = reviewRepository.read(coach);

            // Calculate the average rating for the coach
            float avgRating = reviewRepository.getAvgRating(reviews);

            // Print coach name and average rating
            System.out.printf("| Name: %-7s | Average Rating: %.2f | %n", coach.getName(), avgRating);

            System.out.println("----------------------------------------");
        }
    }

    /**
     * Handles the process of booking swimming lessons by day.
     * It prompts the user to select a day, retrieves lessons available on that day,
     * and returns the list of lessons.
     *
     * @return The list of lessons available on the selected day.
     */
    private List<Lesson> handleBookByDay() {
        // Prompt user to select a day
        var dayMenu = new DayMenu();
        int input = dayMenu.execute();

        // Map user input to the corresponding day
        Day day = switch (input) {
            case 1 -> Day.MONDAY;
            case 2 -> Day.WEDNESDAY;
            case 3 -> Day.FRIDAY;
            case 4 -> Day.SATURDAY;
            default -> null;
        };

        // Retrieve lessons available on the selected day
        return lessonRepository.read(day);
    }

    /**
     * Handles the process of booking swimming lessons by coach.
     * It prompts the user to select a coach, retrieves lessons coached by that coach,
     * and returns the list of lessons.
     *
     * @return The list of lessons coached by the selected coach.
     */
    private List<Lesson> handleBookByCoach() {
        // Prompt user to select a coach
        var coachMenu = new CoachMenu();

        int id = coachMenu.execute();

        Coach coach = coachRepository.readById(id);

        // Retrieve lessons coached by the selected coach
        return lessonRepository.read(coach);
    }

    /**
     * Handles the process of booking swimming lessons by grade.
     * It prompts the user to select a grade, retrieves lessons available for that grade,
     * and returns the list of lessons.
     *
     * @return The list of lessons available for the selected grade.
     */
    private List<Lesson> handleBookByGrade() {
        // Prompt user to select a grade
        var gradeMenu = new GradeMenu();

        int input = gradeMenu.execute();

        // Map user input to the corresponding grade
        Grade grade = switch (input) {
            case 1 -> Grade.ONE;
            case 2 -> Grade.TWO;
            case 3 -> Grade.THREE;
            case 4 -> Grade.FOUR;
            case 5 -> Grade.FIVE;
            default -> null;
        };

        // Retrieve lessons available for the selected grade
        return lessonRepository.read(grade);
    }

    /**
     * Handles the process of cancelling a booking.
     * It retrieves the booking to be cancelled,
     * performs the cancelling operation, and displays the result.
     */
    private void handleCancelBooking() {
        // Get the active user booking to be cancelled.
        Booking booking = getBooking();

        // End process when no booking is returned.
        if (booking == null) return;

        try {
            // Attempt to cancel the booking
            booking = bookingRepository.cancel(booking);
        } catch (BookingAttendedException e) {

            // Handle exception cases.
            System.out.println("\u001B[31mError: " + e.getMessage() + "\u001B[0m");
            return;
        }

        // Display a success message and booking itinerary
        System.out.println();
        System.out.println("\u001B[32mSuccess: Your Booking was Cancelled successfully!\u001B[0m");
        System.out.println();

        System.out.println("Booking Itinerary: ");
        System.out.println(booking);
    }

    /**
     * Handles the process of changing a booking.
     * It retrieves the booking and the new lesson for the change,
     * performs the changing operation, and displays the result.
     */
    private void handleChangeBooking() {
        // Prompt the user for the booking to be changed
        Booking booking = getBooking();

        // End process when no booking is returned.
        if (booking == null) return;

        // Prompt the user for the new lesson
        Lesson lesson = getLesson();

        // End process when no lesson is returned.
        if (lesson == null) return;

        try {
            // Attempt to change the booking
            booking = bookingRepository.change(booking, lesson);
        } catch (BookingAttendedException | BookingCancelledException | NoVacancyException | GradeMisMatchException |
                 DuplicateBookingException e) {
            // Handle exception cases.
            System.out.println("\u001B[31mError: " + e.getMessage() + "\u001B[0m");
            return;
        }

        System.out.println();
        System.out.println("\u001B[32mSuccess: Your Booking was Changed successfully!\u001B[0m");
        System.out.println();

        System.out.println("Booking Itinerary: ");
        System.out.println(booking);
    }

    /**
     * Retrieves the grade selected by the user from the grade menu.
     *
     * @return The selected grade.
     */
    private static Grade getGrade() {
        Grade grade;
        int input;
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

        return grade;
    }

    /**
     * Retrieves the gender selected by the user from the gender menu.
     *
     * @return The selected gender.
     */
    private static Gender getGender() {
        Gender gender;
        int input;
        var genderMenu = new GenderMenu();
        input = genderMenu.execute();

        gender = switch (input) {
            case 1 -> Gender.Male;
            case 2 -> Gender.Female;
            default -> null;
        };
        return gender;
    }

    /**
     * Prompts the user to enter an age until a valid age is provided.
     *
     * @param age        The age entered by the user.
     * @param isValidAge Flag indicating whether the entered age is valid.
     * @return The validated age.
     */
    private int getAge(int age, boolean isValidAge) {
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

        return age;
    }

    /**
     * Prompts the user to enter a contact number.
     *
     * @return The entered contact number.
     */
    private String promptContactNumber() {
        String contactNumber;
        System.out.print("Enter Emergency Contact Number: ");
        contactNumber = console.nextLine();
        return contactNumber;
    }

    /**
     * Prompts the user to enter a name.
     *
     * @return The entered name.
     */
    private String promptName() {
        String name;
        System.out.print("Enter Name: ");
        name = console.nextLine();
        return name;
    }

    /**
     * Retrieves the booking selected by the user.
     * If no bookings are available, it displays a message and returns null.
     * If a booking is selected, it returns the booking.
     *
     * @return The selected booking or null if no bookings are available.
     */
    private Booking getBooking() {
        // Read all user bookings
        List<Booking> bookings = bookingRepository.read(getLearner());

        // Display message and Return when no booking is found.
        if (bookings.isEmpty()) {
            System.out.println();
            System.out.println("\u001B[32mBooking List is currently empty!\u001B[0m");
            return null;
        }

        // Instantiate the booking menu
        var bookingMenu = new BookingMenu(bookings);

        // Display the bookings to the user and retrieve the user's choice id.
        int bookingId = bookingMenu.execute();

        // Retrieve the booking by its id.
        Booking booking = bookingRepository.readById(bookingId);

        // Display message and Return when this booking is not found.
        if (booking == null) {
            System.out.println("\u001B[31mError: Booking Not Found!\u001B[0m");
            return null;
        }

        return booking;
    }

    /**
     * Retrieves the lesson selected by the user.
     * If no lessons are available, it exits the application.
     * If a lesson is selected, it returns the lesson.
     *
     * @return The selected lesson or null if no lessons are available.
     */
    private Lesson getLesson() {
        int input;
        var bookLessonMenu = new BookLessonMenu();

        // Execute the book lesson menu to prompt the user for the booking method
        input = bookLessonMenu.execute();

        List<Lesson> lessons = switch (input) {
            case 1 -> handleBookByDay(); // Book by day
            case 2 -> handleBookByCoach(); // Book by coach
            case 3 -> handleBookByGrade(); // Book by grade
            default -> null;
        };

        // Exit if no lessons are available
        if (lessons == null) System.exit(0);

        // Display the timetable for the selected lessons
        String timeTable = lessonRepository.showTimeTable(lessons);

        // Create a set to store lesson IDs for validation
        Set<Integer> lessonIds = new HashSet<>();

        for (Lesson lesson : lessons) {
            lessonIds.add(lesson.getId());
        }

        // Display the timetable menu for the user to select a specific lesson
        var timeTableMenu = new TimeTableMenu(timeTable, lessonIds);
        int id = timeTableMenu.execute();

        // Retrieve the selected lesson
        Lesson lesson = lessonRepository.readById(id);

        // It should never run but just being safe!
        if (lesson == null) {
            System.out.println();
            System.out.println("No Lesson Found!");
            return null;
        }

        return lesson;
    }

    /**
     * Retrieves a list of learners registered in the application.
     *
     * @return A list of learners.
     */
    public List<Learner> getAppLearners() {
        return this.learnerRepository.read();
    }

    /**
     * Retrieves a list of coaches registered in the application.
     *
     * @return A list of coaches.
     */
    public List<Coach> getAppCoaches() {
        return coachRepository.read();
    }

    /**
     * Retrieves the currently logged-in learner.
     *
     * @return The logged-in learner.
     */
    public Learner getLearner() {
        return learner;
    }

    /**
     * Sets the currently logged-in learner.
     *
     * @param learner The learner to set as logged-in.
     */
    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    /**
     * Formats a double number into a string padded with zeros to ensure two digits.
     *
     * @param number The number to format.
     * @return The formatted string.
     */
    public static String padToTwoDigits(double number) {
        DecimalFormat df = new DecimalFormat("00");
        return df.format(number);
    }
}
