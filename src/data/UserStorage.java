package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.User;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class UserStorage {

    private final Path filePath;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type listType = new TypeToken<List<User>>() {}.getType();

    public UserStorage(Path filePath) {
        this.filePath = filePath;
        ensureExists();
    }

    private void ensureExists() {
        try {
            if (filePath.getParent() != null && Files.notExists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }
            if (Files.notExists(filePath)) {
                Files.writeString(filePath, "[]", StandardCharsets.UTF_8, StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot init users.json", e);
        }
    }

    private List<User> loadAll() {
        try (Reader r = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            List<User> users = gson.fromJson(r, listType);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Cannot read users.json", e);
        }
    }

    private void saveAll(List<User> users) {
        try (Writer w = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
            gson.toJson(users, listType, w);
        } catch (IOException e) {
            throw new RuntimeException("Cannot write users.json", e);
        }
    }

    public boolean existsByEmail(String email) {
        return loadAll().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public boolean register(User user) {
        List<User> users = loadAll();
        boolean exists = users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail()));
        if (exists) return false;

        users.add(user);
        saveAll(users);
        return true;
    }

    public User authenticate(String email, String password) {
        return loadAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public User findByEmail(String email) {
        return loadAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}