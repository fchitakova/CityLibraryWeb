package main.java;

import java.util.Comparator;

/**This class implements Comparator interface and is used for comparing two library books by the book's author's name.
 * 
 * @author I356406
 */


public class AuthorComparator implements Comparator<LibraryBook> {
	@Override
	public int compare(LibraryBook bookToTest, LibraryBook bookToTest1) {
		return bookToTest.getAuthor().compareTo(bookToTest1.getAuthor());
	}
}
