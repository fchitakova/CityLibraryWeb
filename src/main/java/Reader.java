package main.java;
import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;
/**
 * The reader class represents a reader and implements the @Serialazble
 * interface. Every reader has an unique name and holds list with the books
 * which has taken.
 * 
 * @author I356406
 */

public class Reader implements Serializable {

	/**
	 * The readerName variable represents the reader's full name.
	 */
	private String name;
	/**
	 * This variable of type Set is used for storing all the books that reader has
	 * taken from the library.
	 */
	private Set<Book> readerBooks;

	/**
	 * Default constructor which initializes name with empty string and creates an
	 * empty set of read books.
	 */
	public Reader() {
		name = "";
		readerBooks = new HashSet<Book>();
	}

	/**
	 * This constructor creates a Reader class with a reader name passed as argument
	 * and an empty set of books.
	 */
	public Reader(String readerName) {
		setName(readerName);
		readerBooks = new HashSet<Book>();
	}

	/**
	 * Set the reader's name to the passed argument if it is not null and a valid
	 * reader name. Otherwise reader's name is initialized with an empty string.
	 * 
	 * @param readerName
	 */
	public void setName(String readerName) {
		if (readerName != null && readerName.matches(Constants.VALID_NAMETEXT_REGEX)) {
			name = readerName;
		} else {
			name = "";
		}
	}

	public void setReadersBooks(HashSet<Book> readersBooks) {
		readerBooks = readersBooks;
	}

	/**
	 * This method returns the reader's name.
	 * 
	 * @return String representing name
	 */

	public String getName() {
		return name;
	}

	/**
	 * This method returns reference to the HashSet containing reader's
	 * books.readerToTest
	 */

	public Set<Book> getReaderBooks() {
		return readerBooks;
	}

	/**
	 * This method adds new book to reader's book list.
	 * 
	 * @param bookToAdd is reference to Book object which should be added
	 * @return true if the book is successfully added to the list and false
	 *         otherwise
	 */
	public boolean addBook(Book bookToAdd) {

		return readerBooks.add(bookToAdd);
	}

	/**
	 * This methods removes the book referenced by the parameter of the method if it
	 * is in the set.
	 */
	public void returnBook(Book bookToReturn) {
		readerBooks.remove(bookToReturn);
	}
	
	/**
	 * Returns true if the reader has at least one taken book.
	 */
	public boolean hasAnyTakenBooks() {
		if(readerBooks.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.toLowerCase().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reader other = (Reader) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.toLowerCase().equals(other.name.toLowerCase()))
			return false;
		return true;
	}

}