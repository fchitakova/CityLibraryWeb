package main.java;

import java.util.Comparator;

/**
 * This class implements Comparator interface and is used for comparing two library books
 * books by the book's title.
 * 
 * @author I356406
 */

public class BookTitleComparator implements Comparator<LibraryBook> {
	@Override
	public int compare(LibraryBook bookToTest, LibraryBook bookToTest1) {
		return bookToTest.getTitle().compareTo(bookToTest1.getTitle());
	}

}