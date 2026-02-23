package data;

import model.Book;

import java.util.List;

public class FakeData {

    public List<Book> getBooks() {
        return List.of(
                new Book(1, "Clean Code", "Robert Martin", "/img/clean.jpg", null, false),
                new Book(2, "Effective Java", "Joshua Bloch", "/img/java.jpg", "Айбек", true),
                new Book(3, "Design Patterns", "GoF", "/img/patterns.jpg", null, false)
        );
    }

    public Book getBookById(int id) {
        return getBooks()
                .stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
