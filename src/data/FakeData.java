package data;

import model.Book;
import model.Employee;

import java.util.List;

public class FakeData {

    public List<Book> getBooks() {
        return List.of(
                new Book(1, "Clean Code", "Robert Martin", "/img/clean.jpg", null, false),
                new Book(2, "Effective Java", "Joshua Bloch", "/img/java.jpg", "Лейла", true),
                new Book(3, "Design Patterns", "GoF", "/img/patterns.jpg", null, false),
                new Book(4, "Refactoring", "Martin Fowler", "/img/refactoring.jpg", "Лейла", true)
        );
    }

    public Book getBookById(int id) {
        return getBooks()
                .stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Employee getEmployee() {
        return new Employee(1, "Лейла Прохивская", getEmployeeCurrentBooks(), getEmployeePastBooks());
    }

    public List<Book> getEmployeeCurrentBooks() {
        return List.of(
                new Book(2, "Effective Java", "Joshua Bloch", "/img/java.jpg", "Лейла", true),
                new Book(4, "Refactoring", "Martin Fowler", "/img/refactoring.jpg", "Лейла", true)
        );
    }

    public List<Book> getEmployeePastBooks() {
        return List.of(
                new Book(1, "Clean Code", "Robert Martin", "/img/clean.jpg", null, false),
                new Book(3, "Design Patterns", "GoF", "/img/patterns.jpg", null, false)
        );
    }
}
