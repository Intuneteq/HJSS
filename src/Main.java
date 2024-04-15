import com.hjss.App;

/**
 * The Main class serves as the entry point of the application.
 * It instantiates the {@link App} singleton instance and starts the application.
 *
 <p>
 * The application follows several design patterns to manage its structure and functionality:
 * <ul>
 *     <li>Singleton Design Pattern: The {@link App} class is implemented as a singleton to ensure that only one instance of the application is created.</li>
 *     <li>Template Design Pattern: The application utilizes the template design pattern for menu handling and repository management, providing a structured approach to common tasks.</li>
 *     <li>Mediator Pattern: The application employs the mediator pattern for managing lesson bookings, facilitating communication and interaction between the Lesson, Learner, Lesson Repository and LearnerRepository classes.</li>
 *     <li>Repository Pattern: Data logic around the model classes is managed using the repository pattern. This pattern abstracts data access and manipulation, providing a standardized interface for interacting with the underlying data storage.</li>
 * </ul>
 * </p>
 *
 *
 * <p>
 * The application architecture is based on several key components:
 * <ul>
 *     <li>Model Classes: These classes represent real-life objects pertaining to the application domain, providing a structured way to manage data and behavior.</li>
 *     <li>Menu Classes: Terminal interface classes responsible for interacting with the user, presenting menu options and gathering user input.</li>
 *     <li>Repository Classes: These classes manage data logic around the model classes, abstracting data access and manipulation operations.</li>
 * </ul>
 * </p>
 *
 */
public class Main {
    /**
     * The main method of the application.
     * It instantiates the singleton instance of {@link App} and starts the application.
     *
     * @param args The command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Instantiate the singleton instance of App
        App app = App.getInstance();

        // Start the application
        app.start();
    }
}