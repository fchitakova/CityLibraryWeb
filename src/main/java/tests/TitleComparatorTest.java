package main.java.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.java.Book;
import main.java.BookTitleComparator;
import main.java.LibraryBook;
/**
 * This class test TitleComparator class's compare() method.
 * @author I356406
 *
 */
public class TitleComparatorTest {
	@Test
	public void testTitleComparrison() {
		BookTitleComparator btc=new BookTitleComparator();
		LibraryBook bookToTest=new LibraryBook("Dan Brown","Hell",1);
		LibraryBook bookToTest1=new LibraryBook("Ivan Vazov","Pod Igoto",1);
		LibraryBook bookToTest2=new LibraryBook("AnyBody","Hell",1);
		assertTrue(btc.compare(bookToTest,bookToTest1)<0);
		assertTrue(btc.compare(bookToTest, bookToTest2)==0);
	}
}
