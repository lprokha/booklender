package data;

import com.google.gson.reflect.TypeToken;
import model.Book;

import java.nio.file.Path;
import java.util.List;

public class BookStorage {
    private final JsonListStorage<Book> storage;

    public BookStorage(Path file) {
        this.storage = new JsonListStorage<>(file, new TypeToken<List<Book>>() {}.getType());
    }

    public List<Book> getBooks() {
        return storage.loadAll();
    }

    public void saveBooks(List<Book> books) {
        storage.saveAll(books);
    }
}