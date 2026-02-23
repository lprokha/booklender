package model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String imagePath;
    private String issuedToName;

    public Book(int id, String title, String author, String imagePath, String issuedToName, boolean status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.imagePath = imagePath;
        this.issuedToName = issuedToName;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getIssuedToName() {
        return issuedToName;
    }
}
