package main.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class holds a set containing the books in the library.It contains
 * methods for adding books, giving book to reader,storing books to a persistent
 * storage and loading books from it.Plus it can sort books and search for
 * certain book in the catalogue by title and also author name.
 * 
 * @author I356406
 *
 */

public class Catalogue implements Serializable {

	private HashSet<LibraryBook> libraryBooks;

	/**
	 * Creates new empty book catalogue.
	 */
	public Catalogue() {
		libraryBooks = new HashSet<LibraryBook>();
	}

	/**
	 * Creates new catalogue which contains the books in the set passed as argument.
	 * 
	 * @param libraryBooks is hash set which contains the book which will be added
	 *                     to catalogue.
	 */
	public Catalogue(HashSet<LibraryBook> libraryBooks) {
		this.libraryBooks = libraryBooks;
	}

	/**
	 * This method returns all the books in catalogue.
	 * 
	 * @return
	 */
	public HashSet<LibraryBook> getLibraryBooks() {
		return libraryBooks;
	}

	/**
	 * This method searches for books with book title which equals the passed String
	 * argument and returns set of books which contains with this title (if there
	 * are any)
	 * 
	 * @param searchedName is the string containing the string information which
	 *                     will be compared
	 */

	public Set<LibraryBook> searchByTitle(String searchedTitle) {
		return libraryBooks.stream().filter(b -> matches(searchedTitle, b.getTitle())).collect(Collectors.toSet());
	}

	/**
	 * This method searches for books by this author name and returns set containing
	 * all the books written by this author.
	 * 
	 * @param authorName is the string containing the author name.
	 */
	public Set<Book> searchByAuthor(String authorName) {
		return libraryBooks.stream().filter(b -> matches(authorName, b.getAuthor())).collect(Collectors.toSet());
	}

	/**
	 * This method sort catalogue books by specific order.
	 * 
	 * @param bookComparator is book comparator which is used for comparing.
	 * @param toSort         is the set of books which will be sorted
	 * @return list of books in sorted order or empty list if the collection passed
	 *         for sorting is empty.
	 */
	public List<LibraryBook> sortBooks(Comparator<LibraryBook> booksComparator, Set<LibraryBook> toSort) {
		List<LibraryBook> list = new ArrayList<LibraryBook>(toSort);
		Collections.sort(list, booksComparator);
		return list;
	}

	/**
	 * Checks if the book passed as argument has least one available copy.
	 * 
	 * @return true if the book is in the library catalogue and its copies number is
	 *         greater than zero.
	 */
	public boolean isAvailableBook(Book book) {
		LibraryBook toCheck = checkForBook(book);
		if (toCheck == null) {
			return false;
		}
		return toCheck.getCopies() > 0;

	}

	/**
	 * This method returns set containing the available books(the ones which has at
	 * least one copy in the catalogue at that moment).
	 * @return set of the available books.
	 */
	public Set<LibraryBook> getAvailableBooks() {
		return libraryBooks.stream().filter(b -> isAvailableBook(b)).collect(Collectors.toSet());
	}

	/**
	 * This method add @param book to the catalogue if it is not already in it.
	 */
	public void addLibraryBook(LibraryBook book) {
		libraryBooks.add(book);
	}

	/**
	 * This method adds @param bookToAdd to catalogue if the book is not already there.
	 *  If catalogue already contains book ,its copies number is incremented.
	 */
	public void addBook(Book bookToAdd) {
		LibraryBook searched = checkForBook(bookToAdd);
		if (searched == null) {
			libraryBooks.add(new LibraryBook(bookToAdd.getAuthor(), bookToAdd.getTitle(), Constants.STANDARD_COPY_ADD_NUM));
			return;
		}
		searched.setCopies(searched.getCopies() + Constants.STANDARD_COPY_ADD_NUM);
	}

	/**
	 * This method checks if the book is in the catalogue and if it is return
	 * reference to it. Otherwise return null.
	 * 
	 * @param book is the searched book
	 */
	public LibraryBook checkForBook(Book book) {
		for (LibraryBook i : libraryBooks) {
			if (i.equals(book)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * This method search for @param book in catalogue and if it is there the available copies 
	 * of that book are decremented.
	 */
	public void removeBook(Book book) {
		LibraryBook searched = checkForBook(book);
		if (searched != null) {
			searched.setCopies(searched.getCopies() + Constants.STANDARD_COPY_EXTRACT_NUM);
		}
	}

	/**
	 * This method checks if the @param searchedStr is the same or is a substring of
	 * the @param destString.Comparison is case insensitive.
	 * 
	 * @return true if the first argument equals the second or if it is substring of
	 *         the second.
	 */
	public static boolean matches(String searchedStr, String possibleMatch) {
		return possibleMatch.toLowerCase().equals(searchedStr.toLowerCase())
				|| possibleMatch.toLowerCase().contains(searchedStr.toLowerCase());
	}

}