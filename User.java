package user;

import mypack.*;
import java.io.Serializable;
import java.util.*;
import timepakage.DateTimeExample;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    // WaterDrinkReminder pers = new WaterDrinkReminder();
    private String name;
    private int age;
    private double weight;
    private int many;
    List<DateTimeExample> dateTime;

    public User(String name, int age, double weight, int many) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.many = many;
        this.dateTime = new ArrayList<>();
    }

    // getter
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public int getMany() {
        return many;
    }

    public List<DateTimeExample> getDateTime() {
        return dateTime;
    }

    // setter

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setMany(int many) {
        this.many = many;
    }

    public void addDateTimeExample(DateTimeExample entry) {
        dateTime.add(entry);
    }

    // Add new user details
    public void addUser(Scanner sc, List<User> users) {

        try {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter age: ");
            int age = sc.nextInt();
            System.out.print("Enter weight: ");
            double weight = sc.nextDouble();
            // sc.nextLine(); // Consume newline

            if (age <= 100 && age > 0) {

                users.add(new User(name, age, weight, 0));
                System.out.println("User added successfully.");
                sc.nextLine();
                WaterDrinkReminder.saveUsers(users);
                WaterDrinkReminder.menu(sc, users);
            } else {
                System.out.println("please enter the velid age !");
                sc.nextLine();
                addUser(sc, users);
            }

        } catch (InputMismatchException e) {
            System.out.println("Please enter the valid details !");
            sc.nextLine(); // consume new line
            addUser(sc, users);

        }

    }

    // update user details
    public void update(Scanner scanner, List<User> users) {
        int i = 1;
        while (i == 1) {

            System.out.println("What do you want to update ");
            System.out.println("1. Want to change the age");
            System.out.println("2. Want to change the weight");
            System.out.println("3. Want to change the name");
            System.out.println("4. back to main menu");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    try {
                        System.out.print("Enter user's new age " + users.get(0).getName() + ": ");
                        int age = scanner.nextInt();

                        if (age <= 100 && age > 0) {
                            System.out.print(
                                    "your previous age is " + users.get(0).getAge() + " now new updated age is ");
                            users.get(0).setAge(age);
                            System.out.println(users.get(0).getAge());
                            scanner.nextLine();
                            WaterDrinkReminder.saveUsers(users);
                            update(scanner, users);
                        } else {
                            System.out.println("please enter velid age !");
                            scanner.nextLine();
                            update(scanner, users);
                        }

                    } catch (InputMismatchException e) {
                        System.out.println("please enter valid details !");
                        scanner.nextLine();
                        update(scanner, users);
                    }

                    break;
                case "2":
                    try {

                        System.out.print("Enter new weight of " + users.get(0).getName() + " : ");
                        double weight = scanner.nextDouble();
                        if (weight >= 1) {
                            System.out.print(
                                    "your previous weight is " + users.get(0).getWeight()
                                            + " now new updated weight is ");
                            users.get(0).setWeight(weight);
                            System.out.println(users.get(0).getWeight());
                            scanner.nextLine();
                            WaterDrinkReminder.saveUsers(users);
                            update(scanner, users);
                        } else {
                            System.out.println("please enter velid weight !");
                            scanner.nextLine();
                            update(scanner, users);

                        }

                    } catch (InputMismatchException e) {
                        System.out.println("please enter valid details !");
                        scanner.nextLine();
                        update(scanner, users);
                    }
                    break;
                case "3":
                    System.out.print("Enter user's new age " + users.get(0).getName() + ": ");
                    String name = scanner.nextLine();
                    System.out.print(
                            "your previous name is " + users.get(0).getName()
                                    + " now new updated name is ");
                    users.get(0).setName(name);
                    System.out.println(users.get(0).getName());
                    // scanner.nextLine();
                    WaterDrinkReminder.saveUsers(users);
                    update(scanner, users);

                    break;

                case "4":
                    i = 0;
                    WaterDrinkReminder.menu(scanner, users);
                    break;

                default:
                    System.out.println("invelid choice");

            }
        }

    }

    // Log water intake
    public void logWaterIntake(Scanner scanner, List<User> users) {
        try {
            User user = users.get(0);
            System.out.print("Enter your water intake in milliliters " + user.getName() + " : ");
            double amount = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            user.addDateTimeExample(new DateTimeExample(amount));
            System.out.println("Water intake logged successfully for " + user.getName());
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("please enter valid value !");
            logWaterIntake(scanner, users);

        }
        WaterDrinkReminder.menu(scanner, users);
    }

    // featch user details
    public void fetchUserDetails(Scanner scanner, List<User> users) {
        User user = users.get(0);
        System.out.print("User details : ");
        System.out.println(user.getName() + "," + user.getAge() + "," + user.getWeight() + user.getMany());
        System.out.println("Water intek of " + users.get(0).getName() + ":");
        for (DateTimeExample entry : users.get(0).getDateTime()) {
            System.out.println(entry);

        }
        WaterDrinkReminder.menu(scanner, users);

    }

}
