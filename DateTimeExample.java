package timepakage;
import java.time.*;
import java.io.Serializable;


public class DateTimeExample implements Serializable {
    public LocalDateTime timestemp;
   public int amount;

public DateTimeExample(int amount){
    this.timestemp=LocalDateTime.now();
    this.amount = amount;
}   
    //getter
    public int getAmount() {
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