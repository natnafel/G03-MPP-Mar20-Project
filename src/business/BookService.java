package business;

import dataaccess.BookRepository;

import java.util.List;

public class BookService {

	private BookRepository bookRepository = new BookRepository();

	public BookCopy createBookCopy(String isbn, int copyNum) throws LibrarySystemException {
		// validation field for Book form
		
		Book book = findBookByIsbn(isbn);
		if (book == null )
			throw new LibrarySystemException("Isbn does not exist");
		if(book.getCopyNums().contains(copyNum)) {
			throw new LibrarySystemException("Copy number already exists");
		}
		
		BookCopy bookCopy = new BookCopy(book,copyNum,true);
		return bookRepository.createBookCopy(bookCopy);
	}
	
	public Book findBookByIsbn(String isbn) {
		 if (isbn == null)
			 return null;
		
		return bookRepository.findBookByIsbn(isbn);
	}

	public CheckoutRecord createCheckoutRecord(LibraryMember libraryMember, Book book){
	    CheckoutRecord checkoutRecordToPersist = new CheckoutRecord(libraryMember, book.getNextAvailableCopy());
	    return bookRepository.createCheckoutRecord(checkoutRecordToPersist);
    }

    public List<Book> getAllBooks(){
	    return bookRepository.getAllBooks();
    }
}
