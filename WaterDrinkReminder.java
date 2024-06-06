package mypack;

// import timepakage.*;
import user.User;
import java.io.*;
import java.util.*;

import reminderpakae.WaterReminder;

// main class
public class WaterDrinkReminder {

    static WaterReminder obj = new WaterReminder();
    static Thread objThread = new WaterReminder();
    static User userobj = new User(null, 0, 0, 0);
    int i = 1;
    private static final String FILENAME = "users.txt";

    // main method
    public static void main(String[] args) {
        // Load existing users
        List<User> users = loadUsers();
        Scanner sc = new Scanner(System.in);

        if (users.isEmpty()) {
            login(sc, users);
        } else if (!users.isEmpty()) {

            menu(sc, users);
        }

    }

    // Load existing user details
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        // convert bytes stream to object
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
            users = (List<User>) ois.readObject();
            System.out.println("Users loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing users found. Creating a new user database.");
        }
        return users;
    }

    // Save user details into file
    public static void saveUsers(List<User> users) {
        // covert object into output stream
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(users);

        } catch (IOException e) {
            System.out.println("Error occurred while saving users: " + e.getMessage());
        }
    }

    // // calculate daily water intake
    private static void calculateWaterIntake(Scanner scanner, List<User> users) {
        double waterIntake = users.get(users.size() - 1).getWeight() * 30.0; // Adjust multiplier as needed
        System.out.println("Recommended water intake for " + users.get(users.size() - 1).getName() + " ("
                + users.get(users.size() - 1).getWeight() + " kg): "
                + waterIntake + " milliliters per day.");

    }

    public static void login(Scanner sc, List<User> users) {

        if (users.isEmpty()) {
            System.out.println("1. Login");
            System.out.println("2. Exit");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    userobj.addUser(sc, users);
                    saveUsers(users);
                    break;

                case "2":
                    System.out.println("Exiting.....");
                    System.exit(0);
                    break;

                default:
                    System.out.println("please enter valid details !");
                    login(sc, users);
                    break;
            }
        }
    }

    // main menu
    public static void menu(Scanner sc, List<User> users) {

        if (!users.isEmpty()) {
            System.out.println("Hii " + users.get(users.size() - 1).getName());
            calculateWaterIntake(sc, users);
            System.out.println("1. Update user details");
            System.out.println("2. Show user details");
            System.out.println("3. Set reminder for drinking water");
            System.out.println("4. Log water intake");
            System.out.println("5. log out");
            System.out.println("6. Exit");
            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    userobj.update(sc, users);
                    saveUsers(users);
                    break;
                case "2":
                    userobj.fetchUserDetails(sc, users);
                    break;
                case "3":
                    obj.choise(sc, users);
                    objThread.start();
                    objThread.setPriority(Thread.MAX_PRIORITY);
                    sc.nextLine();
                    WaterDrinkReminder.menu(sc, users);

                    break;
                case "4":
                    userobj.logWaterIntake(sc, users);
                    saveUsers(users);
                    break;
                case "5":
                    System.out.println("Logout sucssesfully");
                    users.clear();
                    saveUsers(users);
                    login(sc, users);
                    break;
                case "6":
                    System.out.println("Exiting.....");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    menu(sc, users);
            }
        }
    }
}
