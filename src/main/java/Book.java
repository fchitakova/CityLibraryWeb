package main.java;
import java.io.Serializable;

/**
 * Book class represents a book. The class implements @Serializable interface.
 * A book is defined by title and author's name.
 * @author I356406
 */

public class Book implements Serializable {

	/**
	 * Represents the name of the author in a String variable which is private
	 * member of this class.
	 */

	private String author;
	/**
	 * Represents the title of the book in a String variable which is private member
	 * of this class.
	 */
	private String title;

	/**
	 * Creates book with no information about title and author.
	 * Title and author are empty strings.
	 */
	public Book() {
		author = "";
		title = "";
	}

	/**
	 * Creates an object of class Book with given two parameters. This constructor
	 * takes the following list of parameteres:
	 * 
	 * @param authorName represents the name of book's author
	 * @param title      represents the title of the book
	 */
	public Book(String authorName, String title) {
		setAuthor(authorName);
		setTitle(title);
	}

	public void setTitle(String bookTitle) {
		if (bookTitle != null && bookTitle.matches(Constants.VALID_NAMETEXT_REGEX)) {
			title = bookTitle;
		} else {
			title = "";
		}
	}

	public void setAuthor(String authorName) {
		if (authorName != null && authorName.matches(Constants.VALID_NAMETEXT_REGEX)) {
			author = authorName;
		} else {
			author = "";
		}
	}

	/**
	 * This methods returns the book's title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method returns the name of book's author.
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param @Object otherObject is the right hand side of the comparison
	 *        operation.
	 * @return The method returns true if the two compared objects are referencing
	 *         to the same object in the heap or they have the same values of the
	 *         instance variables authorName and title.
	 */
	public boolean equals(Object otherObject) {
		if (otherObject instanceof Book && otherObject != null) {
			Book temp = (Book) otherObject;
			return this == otherObject || (author.toLowerCase().equals(temp.author.toLowerCase()) && title.toLowerCase().equals(temp.title.toLowerCase()));
		}
		return false;
	}

	/**
	 * Returns a hash value for book object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.toLowerCase().hashCode());
		result = prime * result + ((title == null) ? 0 : title.toLowerCase().hashCode());
		return result;
	}

	/**
	 * Returns the string representation of Book object.It appends book's name and
	 * book's author name.
	 */
	@Override
	public String toString() {
		return new String(this.title + " by " + this.author);

	}

}