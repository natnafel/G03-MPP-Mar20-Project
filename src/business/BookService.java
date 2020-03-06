package business;

import java.util.List;

import business.Book.CheckoutLength;
import dataaccess.BookRepo;
import dataaccess.BookRepoAccess;

public class BookService {
	BookRepoAccess bookRepo = new BookRepo();
	public boolean createBookCopy(String isbn, int copyNum) throws LibrarySystemException {
		// validation field for Book form
		
		Book book = findBookByIsbn(isbn);
		if (book == null )
			throw new LibrarySystemException("Isbn does not exist");
		if(book.getCopyNums().contains(copyNum)) {
			throw new LibrarySystemException("Copy number already exists");
		}
		
		BookCopy bookCopy = new BookCopy(book,copyNum,true);
		return bookRepo.createBookCopy(bookCopy);
	}
	
	public Book findBookByIsbn(String isbn) {
		 if (isbn == null)
			 return null;
		
		return bookRepo.findBookByIsbn(isbn);
	}
	 
	 public LibraryMember findMemberByMemberId(String memberid) throws LibrarySystemException{
		 if (memberid == null)
				 throw new LibrarySystemException();
		 return bookRepo.findMemberByMemberId(memberid);
	 }
	
}
