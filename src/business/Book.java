package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

/**
 *
 */
final public class Book implements Serializable {
	
	private static final long serialVersionUID = 6110690276685962829L;
	private int id;
	private List<BookCopy> copies;
	private List<Author> authors;
	private String isbn;
	private String title;
	private CheckoutLength maxCheckoutLength;

	public enum CheckoutLength{
		SEVEN(7),
		TWENTY_ONE(21);

		private int days;
		CheckoutLength(int days){
			this.days = days;
		}
		public int getDays(){
			return this.days;
		}
	}
	public Book(int id, String isbn, String title, CheckoutLength maxCheckoutLength, List<Author> authors) {
		this.isbn = isbn;
		this.id = id;
		this.title = title;
		this.maxCheckoutLength = maxCheckoutLength;
		this.authors = Collections.unmodifiableList(authors);
	}

	public List<Integer> getCopyNums() {
		List<Integer> retVal = new ArrayList<>();
		for(BookCopy c : copies) {
			retVal.add(c.getCopyNum());
		}
		return retVal;
		
	}
	
	public void setBookCopy(List<BookCopy> copies) {
		this.copies = copies;
	}
	

	@Override
	public boolean equals(Object ob) {
		if(ob == null) return false;
		if(ob.getClass() != getClass()) return false;
		Book b = (Book)ob;
		return b.isbn.equals(isbn);
	}
	
	
	public boolean isAvailable() {
		if(copies == null) {
			return false;
		}
		return copies.stream()
				     .map(l -> l.isAvailable())
				     .reduce(false, (x,y) -> x || y);
	}
	@Override
	public String toString() {
		return "isbn: " + isbn + ", maxLength: " + maxCheckoutLength + ", available: " + isAvailable();
	}
	
	public int getNumCopies() {
		return (int) copies.stream().filter(BookCopy::isAvailable).count();
	}
	
	public String getTitle() {
		return title;
	}
	public List<BookCopy> getCopies() {
		return copies;
	}
	
	public List<Author> getAuthors() {
		return authors;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public BookCopy getNextAvailableCopy() {	
		Optional<BookCopy> optional 
			= copies.stream()
			        .filter(BookCopy::isAvailable).findFirst();
		return optional.orElse(null);
	}
	
	public BookCopy getCopy(int copyNum) {
		for(BookCopy c : copies) {
			if(copyNum == c.getCopyNum()) {
				return c;
			}
		}
		return null;
	}

	public int getId(){
		return this.id;
	}
	public CheckoutLength getMaxCheckoutLength() {
		return maxCheckoutLength;
	}

}
