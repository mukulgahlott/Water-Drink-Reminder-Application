package reminderpakae;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

public class ReminderThread extends Thread  {
    private int interval;
    private String reminderMessage;
    private volatile boolean running = true;

    // Constructor
    public ReminderThread(int interval, String reminderMessage) {
        this.interval = interval;
        this.reminderMessage = reminderMessage;
    }

    public void stopReminder() {
        running = false;
    }

    // Run method to execute the reminder
    @Override
    public void run() {
        while (running) {
            // display notification
            displayNotification("Reminder", reminderMessage);
            try {
                Thread.sleep(interval * 1000); // Convert seconds to milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("user dont want reminder any more");
    }

    // Method to display desktop notifications
    private void displayNotification(String title, String message) {

        try {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage("");
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
