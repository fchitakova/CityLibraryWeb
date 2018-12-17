package main.java.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.Set;

import main.java.Book;
import main.java.Constants;
import main.java.LibraryBook;
import main.java.Reader;

/**
 * Base interface which provides methods for showing user interface of library
 * application and communicating with the user.This interface implementation can
 * support different UI languages.
 * 
 * @author I356406
 *
 */
public abstract class View {

	protected static final String RESOURCE_BUNDLE_BASE_PATH = "src\\main\\java\\resources\\languageResources_";
	protected static final String BG_LANGUAGE_INITIALS = "bg_BG";
	protected static final String EN_LANGUAGE_INITIALS = "en";

	/**
	 * Contains language resources.
	 */
	protected PropertyResourceBundle languageResources;
	/**
	 * Contains information for the current language.
	 */
	private String currentLanguage;

	/**
	 * Creates View with provided language information.
	 */
	protected View(String language) throws IOException {
		currentLanguage = language;
		initResourceBundle();
	}

	/**
	 * Prints the @param readers to the application main.java.view.
	 */
	public abstract void printReaders(HashSet<Reader> readers);

	/**
	 * This method prints books to application main.java.view.
	 * 
	 * @param books       is the book set to print
	 * @param infoMessage  contains information about the set of books which is
	 *                     printed
	 * @param errorMessage contains the info message shown in case the @param book
	 *                     is empty set
	 */
	public abstract void printBooks(Collection<? extends Book> books, String infoMessege, String errorMessage);


	/**
	 * Prints only avaialble library books.
	 * @param books
	 * @param infoMessage  contains information about the set of books which is
	 *                     printed
	 * @param errorMessage contains the info message shown in case the @param book
	 *                     is empty set
	 */
	public abstract void printAvailableBooks(Set<LibraryBook> books, String infoMessage, String errorMessage);
	/**
	 * This method finds @param propertyName's right representation depending on
	 * current language and prints it to application main.java.view.
	 * 
	 * @param propertyName is name of messege in the properties file
	 */
	public abstract void printResource(String propertyName);

	/**
	 * Prints to application main.java.view the messege passed as an argument .
	 */
	public abstract void printMessage(String message);

	/**
	 * Prints list of readers from which user can choose.
	 * 
	 * @param readers contains the readers
	 * @return chosen Reader or null if the readers set is empty
	 */
	public abstract Reader chooseReader(Set<Reader> readers);

	/**
	 * Gets user input choice from the application view.
	 * 
	 * @param menuLowerBound is the lower bound of choice range
	 * @param menuUpperBound is the upper bound of choice range
	 */
	public abstract int getChoice(int menuLowerBound, int menuUpperBound) throws IOException;

	/**
	 * Prints list of books from which user can choose to return.
	 * 
	 * @param books set contains the books from which user can choose
	 * @return chosen Book or null if the invalid choice attempts are greater or
	 *         equal to MAX_INVALID_CHOICE_NUMBER constant.
	 */
	public abstract Book chooseFromTakenBooks(Set<Book> books);
	

	/**
	 * Prints list of books from which user can choose book to take.
	 * 
	 * @param books set contains the books from which user can choose
	 * @return chosen Book or null if the invalid choice attempts are greater or
	 *         equal to MAX_INVALID_CHOICE_NUMBER constant.
	 */
	public abstract Book chooseFromAvaialbale(Set<LibraryBook> books);

	/**
	 *This method gets user's input and returns 
	 *integer corresponding to the action chosen 
	 *by the user.
	 */
	public abstract int  getChosenAction();
	
	
	/**
	 * This method asks the user to enter information with inviting messege stored
	 * in @param infoMessege. This message indicates the type of information which
	 * is needed. If user input is valid to be book or reader name it is returned.
	 * Otherwise the same procedure is repeated until valid input is entered.
	 * 
	 * @param infoMessege       is the inviting text messeges which tells the user
	 *                          what kind of information is needed.
	 * @param ifNotValidMessege contains the messege which should be printed if the
	 *                          input is not valid
	 * @return string containing the user input.
	 */
	public abstract String getTextInputInfo(String infoMessege, String ifNotValidMessege) throws IOException;

	/**
	 * This method gets books information from the user input and if it is valid,
	 * new Book is created.Otherwise procedure for getting text information is
	 * repeated.
	 * 
	 * @return new Book object
	 * @throws IOException
	 */
	public abstract Book getBook() throws IOException;

	/**
	 * This method gets reader information from the user input and if it is valid,
	 * new Reader is created.Otherwise procedure for getting text information is
	 * repeated.
	 * 
	 * @return new Reader object
	 * @throws IOException
	 */
	public abstract Reader getReader() throws IOException;

	public abstract char[] getUsername();

	public abstract char[] getPassword();

	/**
	 * This method checks if the user input is valid to be a reader or book.
	 * 
	 * @param input          is the input which will be checked
	 * @return true if the input is valid(which is when it does not contain numbers
	 *         and special symbols)
	 */
	protected boolean checkInputTextValidity(String input) {
		return input.matches(Constants.VALID_NAMETEXT_REGEX) && !input.equals("");
	}

	/**
	 * Re/Initializes the resource bundle to be the right one for the current
	 * application main.java.view language.
	 */
	private void initResourceBundle() throws IOException {

		StringBuilder filePath = new StringBuilder(RESOURCE_BUNDLE_BASE_PATH);
		if (currentLanguage.equals(Constants.BG_LANGUAGE)) {
			filePath.append(BG_LANGUAGE_INITIALS);
		} else {
			filePath.append(EN_LANGUAGE_INITIALS);
		}
		filePath.append(Constants.PROPERTIES_FILE_EXTENSION);

		InputStream fileInput = new FileInputStream(new File(filePath.toString()));

		languageResources = new PropertyResourceBundle(
				new InputStreamReader(fileInput, Charset.forName(Constants.UTF_8_ENCODING)));
	}

	/**
	 * Changes the application main.java.view's language.
	 * 
	 * @throws IOException
	 */
	public void changeLanguage() throws IOException {
		currentLanguage = (currentLanguage.equals(Constants.BG_LANGUAGE)) ? Constants.EN_LANGUAGE
				: Constants.BG_LANGUAGE;
		initResourceBundle();
	}

	public String getResource(String propertyName) {
		if (!propertyName.equals(Constants.EMPTY_MESSAGE)) {
			return languageResources.getString(propertyName);
		}
		return propertyName;
	}

}
