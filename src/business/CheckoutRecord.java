package business;

import java.time.LocalDate;

public class CheckoutRecord {
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;
    private LocalDate paidDate;
    private double finePaid;
    private Book book;
    private LibraryMember member;

    public CheckoutRecord(LibraryMember libraryMember, Book book) {
        this.checkoutDate = LocalDate.now();
        this.dueDate = checkoutDate.plusDays(book.getMaxCheckoutLength().getDays());
        this.member = libraryMember;
        this.book = book;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    public double getFinePaid() {
        return finePaid;
    }

    public void setFinePaid(double finePaid) {
        this.finePaid = finePaid;
    }

    public Book getBook() {
        return book;
    }

    public LibraryMember getMember() {
        return member;
    }
}
