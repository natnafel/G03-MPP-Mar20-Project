package business;

import dataaccess.BookRepo;
import dataaccess.BookRepoAccess;

public class BookService {
	BookRepoAccess bookRepo = new BookRepo();
	boolean createBookCopy(Book book) throws LibrarySystemException {
		// validation field for Book form
		 if (book == null)
			 throw new LibrarySystemException();
		
		return bookRepo.createBookCopy(book);
	}
	
	 Book findBookByIsbn(String isbn) throws LibrarySystemException{
		 if (isbn == null)
			 throw new LibrarySystemException();
		
		return bookRepo.findBookByIsbn(isbn);
	}
	 
	 public LibraryMember findMemberByMemberId(String memberid) throws LibrarySystemException{
		 if (memberid == null)
				 throw new LibrarySystemException();
		 return bookRepo.findMemberByMemberId(memberid);
	 }
	
}
