package server;

import com.sun.net.httpserver.HttpExchange;
import data.FakeData;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import model.Book;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class BooklenderServer extends BasicServer {

    private final Configuration freemarker;
    private final FakeData fakeData = new FakeData();

    public BooklenderServer(String host, int port) throws IOException {
        super(host, port);

        this.freemarker = initFreeMarker();

        registerGet("/", this::handleBooks);
        registerGet("/books", this::handleBooks);
        registerGet("/book", this::handleBook);
        registerGet("/employee", this::handleEmployee);
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

    private void handleBooks(HttpExchange exchange) {
        var books = fakeData.getBooks();

        Map<String, Object> model = new HashMap<>();
        model.put("books", books);

        renderTemplate(exchange, "books.ftl", model);
    }

    private void handleBook(HttpExchange exchange) {
        Book book = fakeData.getBooks().get(0);

        Map<String, Object> model = new HashMap<>();
        model.put("book", book);

        renderTemplate(exchange, "book.ftl", model);
    }

    private void handleEmployee(HttpExchange exchange) {
        var employee = fakeData.getEmployee();

        Map<String, Object> model = new HashMap<>();
        model.put("employee", employee);

        renderTemplate(exchange, "employee.ftl", model);
    }
}