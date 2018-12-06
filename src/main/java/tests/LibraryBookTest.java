package main.java.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.java.Book;
/**
 * Test class to test Book class methods
 * 
 * @author I356406
 *
 */
public class LibraryBookTest {

	/**
	 * This method tests if exception is thrown when null values are passed to the constructor.
	 */
	@Test(expected = NullPointerException.class)
	public void testBookCreationWithNullArg() {
		Book bookToTest = new Book(null, "...");
		Book bookToTest2 =new Book("...",null);
	}

	/**
	 * This test tests whether the overriden equals() mehthod in Book class works as expected.*/
	@Test
	public void testEqualsMethod() {
		Book bookToTest=new Book("Dan Brown","Hell");
		Book bookToTest1=new Book("Dan Brown","Hell");
		assertTrue(bookToTest.equals(bookToTest1));
		Book bookToTest2=bookToTest;
		assertTrue(bookToTest.equals(bookToTest2));
		assertFalse(bookToTest.equals(new Book("Ivan Vazov","Pod Igoto")));
		
	}
	
	/**
	 * This test method tests whethe two equal objects of class Book will have the same hash code value
	 */
	@Test
	public void testHashCodeMethod() {
			Book bookToTest=new Book("Dan Brown","Hell");
			Book bookToTest1=new Book("Dan Brown","Hell");
			assertEquals(bookToTest.hashCode(),bookToTest1.hashCode());
	}
	
  /**
   * This method tests the toString method.
   */
	
	@Test
	public void testToString() {
		Book bookToTest=new Book("Dan Brown","Hell");
		Book bookToTest1=new Book("Ivan Vazov","Pod Igoto");
		assertEquals("Hell by Dan Brown",bookToTest.toString());
		assertEquals("Pod Igoto by Ivan Vazov",bookToTest1.toString());
	}

}
