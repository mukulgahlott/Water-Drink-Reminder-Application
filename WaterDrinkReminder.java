// org
// import necessary libraries
package mypack;
import timepakage.*;
import reminderpakae.*;
import user.User;
import java.io.*;
import java.util.*;

// main class
class WaterDrinkReminder {
    // storage file
    private static final String FILENAME = "users.txt";

    public static void main(String[] args) {
        // Load existing users
        List<User> users = loadUsers();
        Scanner sc = new Scanner(System.in);

        // menu features
        while (true) {
            System.out.println("Enter your choice:");
            System.out.println("1. Enter user details");
            System.out.println("2. Fetch user details");
            System.out.println("3. Calculate recommended water intake");
            System.out.println("4. Set reminder for drinking water");
            System.out.println("5. Log water intake");
            System.out.println("6. Display water log");
            System.out.println("7. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addUser(sc, users);
                    saveUsers(users);
                    break;
                case 2:
                    fetchUserDetails(sc, users);

                    break;
                case 3:
                    calculateWaterIntake(sc, users);
                    break;
                case 4:
                    reminder();
                    break;
                case 5:
                    logWaterIntake(sc, users);
                    saveUsers(users);
                    break;
                case 6:
                    displayWaterLog(sc, users);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
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

    // Add new user details
    static void addUser(Scanner scanner, List<User> users) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        System.out.print("Enter weight: ");
        double weight = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        users.add   (0,new User(name, age, weight));
        System.out.println("User added successfully.");
    }

    // Save user details into file
    private static void saveUsers(List<User> users) {
        // covert object into output stream
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(users);
            System.out.println("Users saved successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while saving users: " + e.getMessage());
        }
    }
    // liner search in list

    public static int linearSearch(List<User> list, String key) {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(key)) {
                return i; // Return the index of the key if found

            }
        }
        return -1; // Return -1 if the key is not found

    }

    // Fetch existing user details
    private static void fetchUserDetails(Scanner scanner, List<User> users) {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.print("enter the user name ");
        String key = scanner.nextLine();
        int index = linearSearch(users, key);
        if (index == -1) {
            System.out.println("user not found");
        }
        if (index >= 0 && index < users.size()) {
            User user = users.get(index);
            System.out.println("User details:");
            System.out.println(user.getName() + "," + user.getAge() + "," + user.getWeight());
        } else {
            System.out.println("Invalid index.");
        }
    }

    // Calculate recommended water intake based on weight
    private static void calculateWaterIntake(Scanner scanner, List<User> users) {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.print("enter the user name that you want to calculate the water intek requirment ");
        String key = scanner.nextLine();
        int index = linearSearch(users, key);

        if (index >= 0 && index < users.size()) {
            User user = users.get(index);
            double waterIntake = user.getWeight() * 30.0; // Adjust multiplier as needed
            System.out.println("Recommended water intake for " + user.getName() + " (" + user.getWeight() + " kg): "
                    + waterIntake + " milliliters per day.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    // Log water intake
    public static void logWaterIntake(Scanner scanner, List<User> users) {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.println("Enter the user name you want to log water intake for : ");
        String key = scanner.nextLine();
        int index = linearSearch(users, key);
        
        if (index >= 0 && index < users.size()) {
            User user = users.get(index);
            System.out.print("Enter water intake in milliliters: ");
            int amount = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            user.addDateTimeExample(new DateTimeExample(amount));
            System.out.println("Water intake logged successfully for " + user.getName());
        } else {
            System.out.println("Invalid index.");
        }
    }

    // Display water log
    public static void displayWaterLog(Scanner scanner, List<User> users) {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.print("Enter the user name whose water log you want to display : ");
        String key = scanner.nextLine();
        int index = linearSearch(users, key);

        if (index >= 0 && index < users.size()) {
            User user = users.get(index);
            System.out.println("Water log for " + user.getName() + ":");
            for (DateTimeExample entry : user.getDateTime()) {
                System.out.println(entry);
            }
        } else {
            System.out.println("Invalid index.");
        }
    }

    // Set reminder for drinking water
    public static void reminder() {
        try (Scanner scanner = new Scanner(System.in)) {
            List<ReminderThread> reminderThreads = new ArrayList<>();

            System.out.print("Enter interval in seconds (0 to stop adding): ");
            int interval = scanner.nextInt();

            scanner.nextLine(); // Consume newline

            System.out.print("Enter reminder message: ");
            String reminderMessage = scanner.nextLine();

           
            ReminderThread reminderThread = new ReminderThread(interval, reminderMessage);
            reminderThreads.add(reminderThread);
            reminderThread.start();

            // Allow stopping a reminder thread
            while (!reminderThreads.isEmpty()) {
                System.out.print("Enter 0 to exit: ");
                int index = scanner.nextInt();
                if (index == 0) {
                    ReminderThread threadToRemove = reminderThreads.get(0); // Always removes the first one in this implementation
                    threadToRemove.stopReminder();
                    reminderThreads.remove(threadToRemove);
                } else {
                    System.out.println("Invalid statement.");
                }
            }
        }
    }
}