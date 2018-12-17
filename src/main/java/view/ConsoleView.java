package main.java.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import main.java.Book;
import main.java.Constants;
import main.java.LibraryBook;
import main.java.Reader;

/**
 * This class extends extends View class and provides implementation for
 * printing information to the console and communicating with the user via
 * console.
 */
public class ConsoleView extends View {

	private static final String TRY_AGAIN_MESSAGE = "Try again:\n";
	private static final String HAS_AVAILABLE_COPIES = " \n              available copies:";
	/**
	 * The maximum possible invalid input tries.
	 */
	private static final int MAX_INVALID_INPUT_TRIES = 2;

	/**
	 * Creates new ConsoleView.
	 * 
	 * @param language is the interface language of application main.java.view
	 * @throws Exception
	 */
	public ConsoleView(String language) throws IOException {
		super(language);
	}

	/**
	 * Prints @param registeredReaders to the console output.
	 */
	@Override
	public void printReaders(HashSet<Reader> registeredReaders) {
		if (registeredReaders.isEmpty()) {
			printResource(Constants.NOT_READERS);
			return;
		}
		printResource(Constants.REGISTERED_READERS_MESSAGE);
		System.out.println();
		for (Reader i : registeredReaders) {
			printMessage(i.getName() + '\n');
		}

	}

	/**
	 * Shows the user interface and waits for valid input choice.When valid choice
	 * is entered it is returned.
	 * 
	 * @throws Exception
	 */
	@Override
	public int getChosenAction(){
		int choice;
		do {
			showUI();
			choice = getChoice(Constants.CONSOLE_MENU_LOWER_BOUND, Constants.CONSOLE_MENU_UPPER_BOUND);
		} while (choice != Constants.EXIT_COMMAND_ID);
		return choice;
	}

	/**
	 * Shows the user interface containing menu options.
	 */
	
	public void showUI() {
		printMessage("\n\n\n");
		printMessage(getResource(Constants.TITLE) + "\n\n\n");
		printResource(Constants.PRESS);
		printMessage(Constants.SEE_ALL_BOOKS_SORTED_BY_TITLE_ID + Constants.DELIMITER);
		printResource(Constants.SEE_ALL_BOOKS_SORTED_BY_TITLE);
		printMessage(Constants.SEE_ALL_BOOKS_SORTED_BY_AUTHOR_ID + Constants.DELIMITER);
		printResource(Constants.SEE_ALL_BOOKS_SORTED_BY_AUTHOR);
		printMessage(Constants.SEE_AVAILABLE_BOOKS_ID + Constants.DELIMITER);
		printResource(Constants.SEE_AVAILABLE_BOOKS);
		printMessage(Constants.GIVE_BOOK_TO_READER_ID + Constants.DELIMITER);
		printResource(Constants.GIVE_BOOK_TO_READER);
		printMessage(Constants.RETURN_BOOK_ID + Constants.DELIMITER);
		printResource(Constants.RETURN_BOOK);
		printMessage(Constants.ADD_BOOK_ID + Constants.DELIMITER);
		printResource(Constants.ADD_BOOK);
		printMessage(Constants.REGISTER_READER_ID + Constants.DELIMITER);
		printResource(Constants.REGISTER_READER);
		printMessage(Constants.SEE_ALL_READERS_ID + Constants.DELIMITER);
		printResource(Constants.SEE_ALL_READERS);
		printMessage(Constants.SHOW_BOOKS_OF_READER_ID + Constants.DELIMITER);
		printResource(Constants.SHOW_BOOKS_OF_READER);
		printMessage(Constants.SEARCH_BY_TITLE_ID + Constants.DELIMITER);
		printResource(Constants.SEARCH_BY_TITLE);
		printMessage(Constants.SEARCH_BY_AUTHOR_ID + Constants.DELIMITER);
		printResource(Constants.SEARCH_BY_AUTHOR_RROPERTY);
		printMessage(Constants.CHANGE_LANGUAGE_ID + Constants.DELIMITER);
		printResource(Constants.CHANGE_LANGUAGE);
		printMessage(Constants.EXIT_COMMAND_ID + Constants.DELIMITER);
		printResource(Constants.EXIT);
		System.out.println();
		printResource(Constants.GET_CHOICE);

	}

	/**
	 * This method gets books information from user input and if it is valid new
	 * Book is returned.Otherwise null is returned.
	 * 
	 * @return new Book object
	 * @throws IOException
	 */
	@Override
	public Book getBook() throws IOException {
		String author = getTextInputInfo(Constants.ENTER_AUTHOR, Constants.NOT_VALID_AUTHOR_NAME);
		if (author == null) {
			return null;
		}
		String title = getTextInputInfo(Constants.ENTER_TITLE, Constants.NOT_VALID_BOOK_TITLE);
		if (title == null)
			return null;
		return new Book(author, title);

	}

	/**
	 * This method gets reader information from user input and if it is valid new
	 * Reader is returned.Otherwise null is returned.
	 * 
	 * @return new Reader object or null.
	 * @throws IOException
	 */
	@Override
	public Reader getReader() throws IOException {
		String name = getTextInputInfo(Constants.ENTER_READER, Constants.NOT_VALID_READER_NAME);
		if (name == null) {
			return null;
		}
		return new Reader(name);
	}

	/**
	 * This method asks the user to enter information with inviting messege stored
	 * in @param infoMessege. This message indicates the type of information which
	 * is needed. If user input is valid text,its content is returned. Otherwise the
	 * same procedure is repeated until try number is less than
	 * MAX_INVALID_INPUT_TRIES constant.
	 * 
	 * @param infoMessege       is the inviting text messeges which tells the user
	 *                          what kind of information is needed.
	 * @param ifNotValidMessage contains the messege which should be printed if the
	 *                          input is not valid
	 * @return string containing the user input or string containing invalid choice
	 *         information.
	 */
	@Override
	public String getTextInputInfo(String infoMessege, String ifNotValidMessage) throws IOException {
		String input;
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		int invalidTryNumber = 0;
		boolean isValidInput = false;
		do {
			if (invalidTryNumber > 0) {
				printMessage(getResource(ifNotValidMessage) + TRY_AGAIN_MESSAGE);
			} else {
				printResource(infoMessege);
			}
			try {
				input = bf.readLine();
			} catch (IOException e) {
				input = "  ";
			}
			input = input.replaceAll("\\s+", " ").trim();
			isValidInput = checkInputTextValidity(input);
			++invalidTryNumber;
		} while (!isValidInput && invalidTryNumber < MAX_INVALID_INPUT_TRIES);

		if (invalidTryNumber == MAX_INVALID_INPUT_TRIES && !isValidInput) {
			return null;
		}
		return input;
	}

	/**
	 * This method finds @param propertyName's right representation depending on
	 * current language and prints it to console.
	 * 
	 * @param propertyName is name of messege in the properties file
	 */
	@Override
	public void printResource(String propertyName) {
		System.out.println(getResource(propertyName));

	}

	/**
	 * This method prints messege string to the string and do not print new line
	 * after that.
	 */
	@Override
	public void printMessage(String message) {
		System.out.print(message);
	}

	/**
	 * This method prints library books to console main.java.view.
	 * 
	 * @param books        is the book set to print
	 * @param infoMessage  contains information about the set of books which is
	 *                     printed
	 * @param errorMessage contains the info message shown in case the @param book
	 *                     is empty set
	 */
	@Override
	public void printBooks(Collection<? extends Book> books, String infoMessage, String errorMessage) {
		if (books.isEmpty()) {
			printResource(errorMessage);
			return;
		}
		printMessage(infoMessage + "\n\n");
		for (Book b : books) {
			printMessage(b.toString() + '\n');
		}
	}

	/**
	 * Prints list of readers from which user can choose.
	 * 
	 * @param readers contains the readers from which to choose
	 * @return chosen Reader or null if the invalid choice attempts are greater or
	 *         equal to MAX_INVALID_CHOICE_NUMBER constant.
	 */
	@Override
	public Reader chooseReader(Set<Reader> readers) {
		printResource(Constants.CHOOSE_READER_MESSAGE);
		System.out.println();
		HashMap<Integer, Reader> readersMap = new HashMap<Integer, Reader>();
		int value = 1;
		for (Reader r : readers) {
			readersMap.put(value, r);
			System.out.println((value++) + Constants.DELIMITER + r.getName());
		}
		int choice = getChoice(Constants.CHOICE_MENU_LOWER_BOUND, readers.size());
		if (choice == Constants.INVALID_CHOICE_NUMBER) {
			return null;
		}

		return readersMap.get(choice);
	}

	/**
	 * Prints list of books from which user can choose to return.
	 * 
	 * @param books set contains the books from which user can choose
	 * @return chosen Book or null if the invalid choice attempts are greater or
	 *         equal to MAX_INVALID_CHOICE_NUMBER constant.
	 */
	@Override
	public Book chooseFromTakenBooks(Set<Book> books) {
		printResource(Constants.CHOOSE_BOOK_MESSAGE);
		System.out.println();
		HashMap<Integer, Book> booksMap = new HashMap<Integer, Book>();
		int value = 1;

		for (Book book : books) {
			booksMap.put(value, book);
			System.out.println((value++) + Constants.DELIMITER + book.toString());
		}
		int choice = getChoice(Constants.CHOICE_MENU_LOWER_BOUND, books.size());
		if (choice == Constants.INVALID_CHOICE_NUMBER) {
			return null;
		}
		return booksMap.get(choice);
	}

	/**
	 * Prints list of books from which user can choose book to take.
	 * 
	 * @param books set contains the books from which user can choose
	 * @return chosen Book or null if the invalid choice attempts are greater or
	 *         equal to MAX_INVALID_CHOICE_NUMBER constant.
	 */
	@Override
	public Book chooseFromAvaialbale(Set<LibraryBook> books) {
		printResource(Constants.CHOOSE_BOOK_MESSAGE);
		System.out.println();
		HashMap<Integer, LibraryBook> booksMap = new HashMap<Integer, LibraryBook>();
		int value = 1;

		for (LibraryBook book : books) {
			booksMap.put(value, book);
			System.out.println(
					(value++) + Constants.DELIMITER + book.toString() + HAS_AVAILABLE_COPIES + book.getCopies());
		}
		int choice = getChoice(Constants.CHOICE_MENU_LOWER_BOUND, books.size());
		if (choice == Constants.INVALID_CHOICE_NUMBER) {
			return null;
		}
		return booksMap.get(choice);
	}

	/**
	 * Gets user choice and checks if it is valid. The choice is
	 * valid if it is between @param menuLowerBound and @param menuUpperBound values.
	 * If the choice is not valid the same procedure is repeated until input attempts are 
	 * less than MAX_INVALID_CHOICE_NUMBER
	 * 
	 * @param menuLowerBound is the lower limit of the numbers which are mapped to command choices
	 * @param menuUpperBound is the upper limit of the numbers which are mapped to command choices 
	 * 
	 * @return userChoice or INVALID_CHOICE constant
	 */
	@Override
	public int getChoice(int menuLowerBound, int menuUpperBound) {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		int userChoice = Constants.INVALID_CHOICE_NUMBER;
		int numberOfTries = 0;
		boolean successfulChoice = false;
		do {
			successfulChoice = true;
			if (numberOfTries > 0) {
				printResource(Constants.INVALID_CHOICE_PROPERTY);
			}
			try {
				String choice = bf.readLine().trim();
				userChoice = Integer.parseInt(choice);
				if (userChoice < menuLowerBound || userChoice > menuUpperBound) {
					throw new IndexOutOfBoundsException();
				}
			} catch (NumberFormatException | IOException | IndexOutOfBoundsException e) {
				successfulChoice = false;
			}
			++numberOfTries;
		} while (!successfulChoice && numberOfTries < MAX_INVALID_INPUT_TRIES);
		if (numberOfTries == MAX_INVALID_INPUT_TRIES && !successfulChoice) {
			return Constants.INVALID_CHOICE_NUMBER;
		}
		return userChoice;
	}

	@Override
	public char[] getUsername() {
		printResource(Constants.ENTER_USERNAME);
		Scanner sc = new Scanner(System.in);
		char[] a = sc.next().toCharArray();
		return a;
	}

	@Override
	public char[] getPassword() {
		printResource(Constants.ENTER_PASSWORD);
		Scanner sc = new Scanner(System.in);
		char[] a = sc.next().toCharArray();
		return a;
	}

	@Override
	public void printAvailableBooks(Set<LibraryBook> books, String infoMessage, String errorMessage) {
		if (books.isEmpty()) {
			printResource(errorMessage);
			return;
		}
		printResource(infoMessage);
		System.out.println();
		for (LibraryBook b : books) {
			printMessage(b.toString() + HAS_AVAILABLE_COPIES + b.getCopies() + "\n\n");
		}
	}
}
