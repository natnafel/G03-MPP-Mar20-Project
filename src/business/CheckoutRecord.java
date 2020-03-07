package business;

import java.time.LocalDate;

public class CheckoutRecord {
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;
    private LocalDate paidDate;
    private double finePaid;
    private BookCopy bookCopy;
    private LibraryMember member;

    public CheckoutRecord(LibraryMember libraryMember, BookCopy bookCopy) {
        this.checkoutDate = LocalDate.now();
        this.dueDate = checkoutDate.plusDays(bookCopy.getBook().getMaxCheckoutLength().getDays());
        this.member = libraryMember;
        this.bookCopy = bookCopy;
    }

    public CheckoutRecord(LocalDate checkoutDate, LocalDate dueDate, LocalDate returnedDate, LocalDate paidDate, double finePaid, BookCopy bookCopy) {
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
        this.paidDate = paidDate;
        this.finePaid = finePaid;
        this.bookCopy = bookCopy;
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

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public LibraryMember getMember() {
        return member;
    }
}
