package tracker;

import java.util.ArrayList;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String completedJava;
    private String completedDSA;
    private String completedDatabases;
    private String completedSpring;
    private ArrayList<Integer> java;
    private ArrayList<Integer> dsa;
    private ArrayList<Integer> database;
    private ArrayList<Integer> spring;

    public Student(int id, String firstName, String lastName, String emailAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.completedJava = "no";
        this.completedDSA = "no";
        this.completedDatabases = "no";
        this.completedSpring = "no";
        this.java = new ArrayList<>();
        this.dsa = new ArrayList<>();
        this.database = new ArrayList<>();
        this.spring = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getCompletedJava() {
        return completedJava;
    }

    public void setCompletedJava(String completedJava) {
        this.completedJava = completedJava;
    }

    public String getCompletedDSA() {
        return completedDSA;
    }

    public void setCompletedDSA(String completedDSA) {
        this.completedDSA = completedDSA;
    }

    public String getCompletedDatabases() {
        return completedDatabases;
    }

    public void setCompletedDatabases(String completedDatabases) {
        this.completedDatabases = completedDatabases;
    }

    public String getCompletedSpring() {
        return completedSpring;
    }

    public void setCompletedSpring(String completedSpring) {
        this.completedSpring = completedSpring;
    }

    public ArrayList<Integer> getJava() {
        return java;
    }

    public ArrayList<Integer> getDsa() {
        return dsa;
    }

    public ArrayList<Integer> getDatabase() {
        return database;
    }

    public ArrayList<Integer> getSpring() {
        return spring;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", java=" + java +
                ", dsa=" + dsa +
                ", database=" + database +
                ", spring=" + spring +
                '}';
    }
}
