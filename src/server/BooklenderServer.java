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

    private boolean hasCurrentUser = false;
    private String currentUserEmail = "";

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

        hasCurrentUser = true;
        currentUserEmail = user.getEmail();

        redirect303(exchange, "/profile");
    }

    private void profileGet(HttpExchange exchange) {
        Map<String, Object> model = new HashMap<>();

        if (hasCurrentUser && !currentUserEmail.isBlank()) {
            User user = userStorage.findByEmail(currentUserEmail);
            if (user != null) {
                model.put("isReal", true);
                model.put("email", user.getEmail());
                model.put("fullName", user.getFullName());

                renderTemplate(exchange, "profile.ftl", model);
                return;
            }
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