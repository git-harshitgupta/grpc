package org.example.sec04;

public class JsonPerson{
    String lastName;
    int age;
    String email;
    boolean employed;
    double salary;
    long bankAccountNumber;
    int balance;

    public JsonPerson(String lastName, int age, String email, boolean employed, double salary, long bankAccountNumber, int balance) {
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.employed = employed;
        this.salary = salary;
        this.bankAccountNumber = bankAccountNumber;
        this.balance = balance;
    }
}