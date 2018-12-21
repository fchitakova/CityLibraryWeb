package main.java.exceptions;

/**
 * This exception is thrown either reader is not registered, or there are not
 * any readers.
 * 
 * @author I356406
 *
 */
public class ReaderException extends Exception {
	public ReaderException(String message) {
		super(message);
	}
}
