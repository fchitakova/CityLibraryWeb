package main.java.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.java.AuthorComparator;
import main.java.LibraryBook;

/**
 * This class tests AuthorComparator class's compare() method
 * 
 * @author I356406
 *
 */

public class AuthorComparatorTest {

	@Test
	public void testAuthorComparrison() {
		AuthorComparator btc = new AuthorComparator();
		LibraryBook bookToTest = new LibraryBook("Dan Brown", "Hell",1);
		LibraryBook bookToTest1 = new LibraryBook("Ivan Vazov", "Pod Igoto",1);
		LibraryBook bookToTest2 = new LibraryBook("AnyBody", "Hell",1);
		LibraryBook bookToTest3 = bookToTest;
		assertTrue(btc.compare(bookToTest, bookToTest1) < 0);
		assertTrue(btc.compare(bookToTest, bookToTest3)==0);
		assertTrue(btc.compare(bookToTest3, bookToTest2)>0);
	}
}
