package timepakage;

import java.time.*;
import java.io.Serializable;

public class DateTimeExample implements Serializable {
    public LocalDateTime timestemp;
    public double amount;

    public DateTimeExample(double amount) {
        this.timestemp = LocalDateTime.now();
        this.amount = amount;
    }

    // getter
    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestemp;
    }

    @Override
    public String toString() {
        return "WaterLogEntry{" +
                "amount=" + amount +
                ", timestamp=" + timestemp +
                '}';
    }

}
