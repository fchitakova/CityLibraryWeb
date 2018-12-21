package main.java.exceptions;

/**
 * Signals that there are not any books.
 * @author I356406
 *
 */
public class BookException extends Exception {
     
	public BookException(String message) {
		super(message);
	}
}
