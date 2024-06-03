package user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import timepakage.DateTimeExample;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    String name;
    int age;
    double weight;
    List<DateTimeExample> dateTime;

    public User(String name, int age, double weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.dateTime = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public List<DateTimeExample> getDateTime() {
        return dateTime;
    }

    public void addDateTimeExample(DateTimeExample entry) {
        dateTime.add(entry);
    }

 
}
