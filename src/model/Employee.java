package model;

import java.util.List;

public class Employee {
    private int id;
    private String fullName;
    private List<Book> currentBooks;
    private List<Book> pastBooks;
    private String email;
    private String password;

    public Employee() {
    }

    public Employee(int id, String fullName, List<Book> currentBooks, List<Book> pastBooks, String email, String password) {
        this.id = id;
        this.fullName = fullName;
        this.currentBooks = currentBooks;
        this.pastBooks = pastBooks;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public List<Book> getCurrentBooks() {
        return currentBooks;
    }

    public List<Book> getPastBooks() {
        return pastBooks;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
