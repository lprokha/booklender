package data;

import com.google.gson.reflect.TypeToken;
import model.Employee;

import java.nio.file.Path;
import java.util.List;

public class EmployeeStorage {
    private final JsonListStorage<Employee> storage;

    public EmployeeStorage(Path file) {
        this.storage = new JsonListStorage<>(file, new TypeToken<List<Employee>>() {}.getType());
    }

    public List<Employee> getEmployees() {
        return storage.loadAll();
    }

    public void saveEmployees(List<Employee> employees) {
        storage.saveAll(employees);
    }

    public Employee findByEmail(String email) {
        return getEmployees().stream()
                .filter(e -> e.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public boolean existsByEmail(String email) {
        return getEmployees().stream()
                .anyMatch(e -> e.getEmail() != null && e.getEmail().equalsIgnoreCase(email));
    }

    public void addEmployee(Employee employee) {
        List<Employee> employees = getEmployees();
        employees.add(employee);
        saveEmployees(employees);
    }

    public int nextId() {
        int max = 0;
        for (Employee e : getEmployees()) {
            if (e.getId() > max) max = e.getId();
        }
        return max + 1;
    }

    public Employee authenticate(String email, String password) {
        List<Employee> employees = getEmployees();

        for (Employee e : employees) {
            if (e.getEmail() != null && e.getEmail().equalsIgnoreCase(email) && e.getPassword() != null && e.getPassword().equals(password)) {
                return e;
            }
         }

        return null;
    }
}
