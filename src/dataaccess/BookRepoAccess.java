package dataaccess;

import java.util.List;

import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.LibraryMember;

public interface BookRepoAccess {
	public Book findBookByIsbn(String isbn);
	public LibraryMember findMemberByMemberId(String memberid);
    public boolean createBookCopy(BookCopy bookCopy);
    public boolean createCheckoutRecord(CheckoutRecord checkoutrecord);
    public List<CheckoutRecord> getOverdueCheckoutRecord();

}