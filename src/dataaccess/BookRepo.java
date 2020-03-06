package dataaccess;

import java.util.List;

import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.LibraryMember;

public class BookRepo implements BookRepoAccess {

	@Override
	public Book findBookByIsbn(String isbn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LibraryMember findMemberByMemberId(String memberid) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean createCheckoutRecord(CheckoutRecord checkoutrecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CheckoutRecord> getOverdueCheckoutRecord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createBookCopy(BookCopy bookCopy) {
		// TODO Auto-generated method stub
		return false;
	}





}
