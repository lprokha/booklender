package server;

import com.sun.net.httpserver.HttpExchange;
import data.BookStorage;
import data.EmployeeStorage;
import data.UserStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import model.Book;
import model.Employee;
import model.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooklenderServer extends BasicServer {

    private final Configuration freemarker;
    private final BookStorage bookStorage = new BookStorage(Path.of("storage", "books.json"));
    private final EmployeeStorage employeeStorage = new EmployeeStorage(Path.of("storage", "employees.json"));
    private final UserStorage userStorage = new UserStorage(Path.of("storage", "users.json"));

    private boolean registerFlashShown = false;
    private boolean registerFlashSuccess = false;
    private String registerFlashMessage = "";

    private boolean loginFlashError = false;

    private static final String SESSION_COOKIE = "sessionId";
    private static final int SESSION_MAX_AGE_SECONDS = 600;

    private final Map<String, String> sessions = new HashMap<>();

    public BooklenderServer(String host, int port) throws IOException {
        super(host, port);

        this.freemarker = initFreeMarker();

        registerGet("/", this::handleBooks);
        registerGet("/books", this::handleBooks);
        registerGet("/book", this::handleBook);
        registerGet("/employee", this::handleEmployee);

        registerGet("/register", this::registerGet);
        registerPost("/register", this::registerPost);

        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);

        registerGet("/profile", this::profileGet);

        registerPost("/book/issue", this::issueBookPost);
        registerPost("/book/return", this::returnBookPost);
        
        registerPost("/logout", this::logoutPost);
    }

    private void logoutPost(HttpExchange exchange) {
        String sessionId = getCookieValue(exchange, SESSION_COOKIE);
        if (sessionId != null) {
            sessions.remove(sessionId);
        }

        Cookie<String> dead = Cookie.make(SESSION_COOKIE, "");
        dead.setMaxAge(0);
        dead.setHttpOnly(true);
        setCookie(exchange, dead);

        redirect303(exchange, "/login");
    }

    private void returnBookPost(HttpExchange exchange) {
        Employee employee = getCurrentEmployee(exchange);
        if (employee == null) {
            redirect303(exchange, "/login");
            return;
        }

        List<Book> books = bookStorage.getBooks();
        Book book = books.isEmpty() ? null : books.get(0);
        if (book == null) {
            redirect303(exchange, "/books");
            return;
        }

        if (book.getIssuedToEmail() == null ||
                !book.getIssuedToEmail().equalsIgnoreCase(employee.getEmail())) {
            redirect303(exchange, "/book");
            return;
        }

        if (employee.getCurrentBooks() != null) {
            employee.getCurrentBooks().removeIf(b -> b.getId() == book.getId());
        }
        if (employee.getPastBooks() != null) {
            employee.getPastBooks().add(book);
        }

        book.setIssuedToEmail(null);
        book.setIssuedToName(null);
        book.setStatus(false);

        bookStorage.saveBooks(books);

        List<Employee> employees = employeeStorage.getEmployees();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmail() != null &&
                    employees.get(i).getEmail().equalsIgnoreCase(employee.getEmail())) {
                employees.set(i, employee);
                break;
            }
        }
        employeeStorage.saveEmployees(employees);

        redirect303(exchange, "/book");
    }

    private void issueBookPost(HttpExchange exchange) {
        Employee employee = getCurrentEmployee(exchange);
        if (employee == null) {
            redirect303(exchange, "/login");
            return;
        }

        if (employee.getCurrentBooks() != null && employee.getCurrentBooks().size() >= 2) {
            redirect303(exchange, "/profile");
            return;
        }

        List<Book> books = bookStorage.getBooks();
        Book book = books.isEmpty() ? null : books.get(0);
        if (book == null) {
            redirect303(exchange, "/books");
            return;
        }

        if (book.getIssuedToEmail() != null && !book.getIssuedToEmail().isBlank()) {
            redirect303(exchange, "/book");
            return;
        }

        book.setIssuedToEmail(employee.getEmail());
        book.setIssuedToName(employee.getFullName());
        book.setStatus(true);

        if (employee.getCurrentBooks() != null) {
            employee.getCurrentBooks().add(book);
        }

        bookStorage.saveBooks(books);

        List<Employee> employees = employeeStorage.getEmployees();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmail() != null &&
                    employees.get(i).getEmail().equalsIgnoreCase(employee.getEmail())) {
                employees.set(i, employee);
                break;
            }
        }
        employeeStorage.saveEmployees(employees);

        redirect303(exchange, "/book");
    }

    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDirectoryForTemplateLoading(new File("templates"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void renderTemplate(HttpExchange exchange, String templateFile, Map<String, Object> model) {
        try {
            Template template = freemarker.getTemplate(templateFile);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {
                template.process(model, writer);
                writer.flush();

                byte[] data = stream.toByteArray();
                sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
            }

        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private String newSessionId() {
        return java.util.UUID.randomUUID().toString();
    }

    private User getCurrentUser(HttpExchange exchange) {
        String sessionId = getCookieValue(exchange, SESSION_COOKIE);
        if (sessionId == null || sessionId.isBlank()) {
            return null;
        }

        String email = sessions.get(sessionId);
        if (email == null || email.isBlank()) {
            return null;
        }

        return userStorage.findByEmail(email);
    }

    private Employee getCurrentEmployee(HttpExchange exchange) {
        User user = getCurrentUser(exchange);
        if (user == null) return null;
        return employeeStorage.findByEmail(user.getEmail());
    }

    private Book firstBookOrNull(List<Book> books) {
        if (books.isEmpty()) {
            return null;
        }

        return books.get(0);
    }

    private void registerGet(HttpExchange exchange) {
        Map<String, Object> model = new HashMap<>();
        model.put("flashShown", registerFlashShown);
        model.put("flashSuccess", registerFlashSuccess);
        model.put("flashMessage", registerFlashMessage);

        registerFlashShown = false;
        registerFlashSuccess = false;
        registerFlashMessage = "";

        renderTemplate(exchange, "register.ftl", model);
    }

    private void registerPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> form = Utils.parseUrlEncoded(raw, "&");

        String email = form.getOrDefault("email", "").trim();
        String password = form.getOrDefault("password", "").trim();
        String fullName = form.getOrDefault("fullName", "").trim();

        boolean ok = false;

        if (!email.isBlank() && !password.isBlank() && !fullName.isBlank()) {
            ok = userStorage.register(new User(email, password, fullName));
        }

        registerFlashShown = true;
        registerFlashSuccess = ok;

        if (ok) {
            if (!employeeStorage.existsByEmail(email)) {
                int newId = employeeStorage.nextId();
                Employee employee = new Employee(newId, fullName, new ArrayList<Book>(), new ArrayList<Book>(), email);
                employeeStorage.addEmployee(employee);
            }
        } else {
            registerFlashMessage = "Регистрация не удалась: пользователь уже существует или поля пустые.";
        }

        redirect303(exchange, "/register");
    }

    private void loginGet(HttpExchange exchange) {
        Map<String, Object> model = new HashMap<>();
        model.put("loginError", loginFlashError);

        loginFlashError = false;

        renderTemplate(exchange, "login.ftl", model);
    }

    private void loginPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> form = Utils.parseUrlEncoded(raw, "&");

        String email = form.getOrDefault("email", "").trim();
        String password = form.getOrDefault("password", "").trim();

        User user = null;
        if (!email.isBlank() && !password.isBlank()) {
            user = userStorage.authenticate(email, password);
        }

        if (user == null) {
            loginFlashError = true;
            redirect303(exchange, "/login");
            return;
        }

        String sessionId = newSessionId();
        sessions.put(sessionId, user.getEmail());

        Cookie cookie = Cookie.make(SESSION_COOKIE, sessionId);
        cookie.setMaxAge(SESSION_MAX_AGE_SECONDS);
        cookie.setHttpOnly(true);
        setCookie(exchange, cookie);

        redirect303(exchange, "/profile");
    }

    private void profileGet(HttpExchange exchange) {
        Map<String, Object> model = new HashMap<>();

        User user = getCurrentUser(exchange);
        if (user != null) {
            model.put("isReal", true);
            model.put("email", user.getEmail());
            model.put("fullName", user.getFullName());

            renderTemplate(exchange, "profile.ftl", model);
            return;
        }

        model.put("isReal", false);
        model.put("email", "unknown@example.com");
        model.put("fullName", "Некий пользователь");

        renderTemplate(exchange, "profile.ftl", model);
    }

    private void handleBooks(HttpExchange exchange) {
        List<Book> books = bookStorage.getBooks();

        Map<String, Object> model = new HashMap<>();
        model.put("books", books);

        renderTemplate(exchange, "books.ftl", model);
    }

    private void handleBook(HttpExchange exchange) {
        List<Book> books = bookStorage.getBooks();
        Book book = books.isEmpty() ? null : books.get(0);

        Map<String, Object> model = new HashMap<>();
        model.put("book", book);
        model.put("isAuth", getCurrentUser(exchange) != null);

        User user = getCurrentUser(exchange);
        boolean canReturn = false;
        if (user != null && book != null && book.getIssuedToEmail() != null) {
            canReturn = book.getIssuedToEmail().equalsIgnoreCase(user.getEmail());
        }
        model.put("canReturn", canReturn);

        renderTemplate(exchange, "book.ftl", model);
    }

    private void handleEmployee(HttpExchange exchange) {
        List<Employee> employees = employeeStorage.getEmployees();
        Employee employee = employees.isEmpty() ? null : employees.get(0);

        Map<String, Object> model = new HashMap<>();
        model.put("employee", employee);

        renderTemplate(exchange, "employee.ftl", model);
    }
}