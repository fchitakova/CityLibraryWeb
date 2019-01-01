package main.java;
import java.io.Serializable;

/**
 * LibraryBook class extends Book.
 * It holds additional information about the number available copies of a book.
 * LibraryBook objects are compared with the same logic as Book objects-by comparing title
 * and author.
 * @method equals() and hashCode() are not overriden.They are inherited from @Book base class.
 * @author I356406
 *
 */

public class LibraryBook extends Book implements Serializable  {


	private Integer copies;

	/**
	 * The default constructor overrides @Book default constructor.
	 * It creates LibraryBook with title and author which are empty strings and
	 * one number of copies.
	 */
	public LibraryBook() {
		super();
		copies = 1;
	}

	/**
	 * Creates a library book object with title ,author and copies information provided with passed arguments.
	 */
	public LibraryBook(String author, String title, Integer copies) {
		super(author, title);
		this.copies = copies;
	}

	public Integer getCopies() {
		return copies;
	}

	/**
	 * Set library book's copies to @param copies 
	 * if it is positive integer or at least zero.
	 */
	public void setCopies(Integer copies) {
		if (copies >= 0) {
			this.copies = copies;
		}
	}

	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

}