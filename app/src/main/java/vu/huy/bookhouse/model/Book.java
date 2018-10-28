package vu.huy.bookhouse.model;

public class Book {
    private int book_id;
    private String book_name;
    private String book_author;
    private String book_description;
    private int book_mark;
    private String book_link;

    public Book(int book_id, String book_name, String book_author, String book_description, int book_mark, String book_link) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_author = book_author;
        this.book_description = book_description;
        this.book_mark = book_mark;
        this.book_link = book_link;
    }

    public Book() {

    }
    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_description() {
        return book_description;
    }

    public void setBook_description(String book_description) {
        this.book_description = book_description;
    }

    public int getBook_mark() {
        return book_mark;
    }

    public void setBook_mark(int book_mark) {
        this.book_mark = book_mark;
    }

    public String getBook_link() {
        return book_link;
    }

    public void setBook_link(String book_link) {
        this.book_link = book_link;
    }

    @Override
    public String toString() {
        return book_name;
    }
}
