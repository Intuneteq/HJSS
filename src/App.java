import com.hjss.menu.MainMenu;
import com.hjss.repository.LearnerRepository;

public class App {
    private static App app;
    private final String name;
    private final LearnerRepository learnerRepository;

    private App() {
        name = "Hatfield Junior Swimming School";
        learnerRepository = new LearnerRepository();
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

        System.out.println(input);
    }
}
