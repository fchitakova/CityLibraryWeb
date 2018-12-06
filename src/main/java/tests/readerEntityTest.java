package main.java.tests;
 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.java.Book;
import main.java.Reader;

/**
 * This class tests all the methods of a Reader class.
 * @author I356406
 *
 */
public class readerEntityTest {

	/**
	 * This method tests whether default contructor create new empty object with empty Book Set and name reference null.
	 */
	@Test
	public void testDefaultContructorCreation() {
		Reader readerToTest=new Reader();
		assertNull(readerToTest.getName());
		assertTrue(readerToTest.getReaderBooks().isEmpty());
	}
	
	
	/**
	 * This method tests whether the contructor with parameter creates a valid object
	 */
	@Test(expected = NullPointerException.class)
	public void testContructorWithParam() {
		Reader readerToTest=new Reader(null);
		Reader readerToTest2=new Reader("Ivan");
		assertEquals("Ivan",readerToTest.getName());
		assertNull(readerToTest.getReaderBooks().isEmpty());
	}
	
	/**
	 * This method test wheteher  Reader class's addBook() method's behaviour is as it is expected to be.
	 */
	@Test
	public void testAddindBook() {
		Book b1=new Book("Ivan Vazov","Pod Igoto");
		Book b2=new Book("Dan Braun","Ad");
		Book b3=b1;
		Reader reader=new Reader("Georgi Petrov");
		reader.addBook(b1);
		assertTrue(reader.getReaderBooks().contains(b1));
		assertEquals(false,reader.addBook(b3));
		reader.addBook(b2);
		assertEquals(2,reader.getReaderBooks().size());
	}
	
	/**
	 *  This method test wheteher  Reader class's returnBook() method's behaviour is as it is expected to be.
	 */
	@Test
	public void testReturningBook() {
		Book b1=new Book("Ivan Vazov","Pod Igoto");
		Book b2=new Book("Dan Braun","Ad");
		Book b3=b1;
		Reader reader=new Reader("Georgi Petrov");
		reader.addBook(b1);
		reader.addBook(b2);
		assertEquals(false,reader.addBook(b3));
	}
	
}
