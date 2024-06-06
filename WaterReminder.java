package reminderpakae;

import java.util.List;
import java.util.Scanner;

import mypack.WaterDrinkReminder;
import user.User;

import java.time.*;

// import java.util.*;
public class WaterReminder extends Thread {
    public static double timeer = 60 * 1000;
    public static User userobj = new User(null, 0, 0, 0);
    Scanner sc = new Scanner(System.in);
    static LocalTime time;
    public static int many = 4;
    Thread t1 = new Thread();

    // choise function
    public void choise(Scanner sc, List<User> users) {

        System.out.println("Enter your choice : ");
        System.out.println("1. Do you want default reminder in every 1 minute of 4 times ? ");
        System.out.println("2. Do you want your custom reminder duration ?");
        System.out.println("3. Back to main menu ");

        String choice = sc.nextLine();
        switch (choice) {
            case "1":

                break;
            case "2":
                try {
                    System.out.print("Enter the duration in Minuts : ");
                    double i = sc.nextDouble();
                    if (i > 0 && i < 60 * 60 * 1000 * 24) {
                        timeer = i * 60 * 1000;

                        System.out.print("How many times you want your Reminder : ");
                        int m = sc.nextInt();
                        if (many > 0) {
                            many = m;
                            users.get(0).setMany(many);
                            System.out.println("you get reminder in every " + i + " minute for "
                                    + users.get(0).getMany() + " times");

                        } else {
                            System.out.println(("please enter valid value !"));
                        }

                    } else {
                        System.out.println("please enter the value that is more than 0 and less than 24 hours ");

                    }
                } catch (Exception e) {
                    System.out.println("please enter valid details !");
                    choise(sc, users);

                }
                break;
            case "3":
                WaterDrinkReminder.menu(sc, users);
                break;

            default:
                System.out.println("please enter valid choice");
                choise(sc, users);
                break;

        }

    }

    @Override
    public void run() {

        for (int i = 0; i < many; i++) {

            try {
                Thread.sleep((long) timeer);
                System.out.println("drink water");
            } catch (InterruptedException e) {
                e.printStackTrace();

            }

        }

    }

}
