// org
// import necessary libraries
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.io.*;
import java.util.*;
import java.util.List;

class User implements Serializable {
    // user attributes
    public String name;
    public int age;
    public double weight;

    // Constructor
    public User(String name, int age, double weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }
}
// main class
 class WaterDrinkReminder {
 // storage file
    private  static final String FILENAME = "users.txt";
// icon png
    private static final String ICON_PATH = "water-glass.png";

    public static class ReminderThread extends Thread {
        private  int interval;
        private  String reminderMessage;
        private  volatile boolean running = true;
        private  final int indentationLevel;

        // Constructor
        public ReminderThread(int interval, String reminderMessage, int indentationLevel) {
            this.interval = interval;
            this.reminderMessage = reminderMessage;
            this.indentationLevel = indentationLevel;
        }

        public void stopReminder() {
            running = false;
        }

    // Run method to execute the reminder
        @Override
        public void run() {
            while (running) {
                StringBuilder im = new StringBuilder();
                for (int i = 0; i < indentationLevel; i++) {
                    im.append("\t"); // Add tabs for indentation
                }
                im.append(reminderMessage);
                // display notification
                displayNotification("Reminder", im.toString());
                try {
                    Thread.sleep(interval * 1000); // Convert seconds to milliseconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        // Method to display desktop notification
        private void displayNotification(String title, String message) {

            try {
                SystemTray tray = SystemTray.getSystemTray();
                Image image = Toolkit.getDefaultToolkit().createImage(ICON_PATH);
                TrayIcon trayIcon = new TrayIcon(image, "Reminder");
                trayIcon.setImageAutoSize(true);
                trayIcon.setToolTip("Reminder");
                tray.add(trayIcon);
                trayIcon.displayMessage(title, message, MessageType.NONE);
            } catch (AWTException e) {
                System.err.println("Failed to display notification: " + e.getMessage());
            }
        }
    }

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
            System.out.println("5. Exit");

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

        users.add(new User(name, age, weight));
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

    // Fetch existing user details
    private static void fetchUserDetails(Scanner scanner, List<User> users) {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.print("Enter the index of the user you want to fetch (0-" + (users.size() - 1) + "): ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (index >= 0 && index < users.size()) {
            User user = users.get(index);
            System.out.println("User details:");
            System.out.println(user.name + "," + user.age + "," + user.weight);
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
        System.out.print("Enter the index of the user you want to calculate water intake for (0-" + (users.size() - 1) + "): ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (index >= 0 && index < users.size()) {
            User user = users.get(index);
            double waterIntake = user.weight * 30.0; // Adjust multiplier as needed
            System.out.println("Recommended water intake for " + user.name + " (" + user.weight + " kg): " + waterIntake + " milliliters per day.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    // user can set reminder and also set the message for reminder
    static void reminder() {
        try (Scanner scanner = new Scanner(System.in)) {
            List<ReminderThread> reminderThreads = new ArrayList<>();
            int indentationLevel = 0;

            System.out.print("Enter interval in seconds (0 to stop adding): ");
            int interval = scanner.nextInt();

            scanner.nextLine(); // Consume newline

            System.out.print("Enter reminder message: ");
            String reminderMessage = scanner.nextLine();

            ReminderThread reminderThread = new ReminderThread(interval, reminderMessage, indentationLevel);
            reminderThreads.add(reminderThread);
            reminderThread.start();

            // Allow stopping a reminder thread
            while (!reminderThreads.isEmpty()) {
                System.out.print("Enter 0 to exit: ");
                int index = scanner.nextInt();
                if (index == 0) {
                    ReminderThread threadToRemove = reminderThreads.get(index);
                    threadToRemove.stopReminder();
                    reminderThreads.remove(threadToRemove);
                } else {
                    System.out.println("Invalid statement.");
                }
            }
        }
    }
}
