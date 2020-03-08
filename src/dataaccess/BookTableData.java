package dataaccess;

import business.Book;


public class BookTableData {
    private String title;
    private String authors;
    private String isbn;
    private String number;
    private String isAvailable;
    private String copyNum;
    public BookTableData(Book book) {
        this.title = book.getTitle();
        this.authors = book.getAuthors().toString();
        this.isbn = book.getIsbn();
        this.number = Integer.toString(book.getNumCopies());
       // this.maxDay = Integer.toString(book.getMaxCheckoutLength());
    }

    //public BookTableData(){};

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getNumber() {
        return number;
    }

    public String getCopyNum() {
        return copyNum;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setCopyNum(String copyNum) {
        this.copyNum = copyNum;
    }

    @Override
    public String toString() {
        return title + authors + isbn + number;
    }

}
