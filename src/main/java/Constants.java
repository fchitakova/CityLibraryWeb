package main.java;

/**
 * This class contains the constants used from the classes of library application.
 * It cannot be instantiated nor inherited.
 * @author I356406
 *
 */
public final class Constants {
	private Constants(){
		
	}

	public static final String EMPTY_MESSAGE="";
	public static final String ALL_BOOKS_LABEL="ALL_BOOKS_LABEL";
	public static final String SEE_ALL_BOOKS="SEE_ALL_BOOKS";
	public static final String AVAILABLE_BOOKS_LABEL="AVAILABLE_BOOKS_LABEL";
	public static final String SORTED_BY_TITLE = "SORTED_BY_TITLE";
	public static final String SORTED_BY_AUTHOR = "SORTED_BY_AUTHOR";
	public static final String BOOK_TITLE_LABEL="BOOK_TITLE_LABEL";
	public static final String GIVE_BOOK_LABEL="GIVE_BOOK_LABEL";
	public static final String BOOK_AUTHOR_LABEL="BOOK_AUTHOR_LABEL";
	public static final String SUBMIT_BUTTON_LABEL="SUBMIT_BUTTON_LABEL";
	public static final String BOOK_COPIES_LABEL="BOOK_COPIES_LABEL";
	public static final String READER_NAME_LABEL="READER_NAME_LABEL";
	public static final String CHOOSE_SORTING_ORDER="CHOOSE_SORTING_ORDER";
	public static final String SEE_AVAILABLE_BOOKS = "SEE_AVAILABLE_BOOKS";
	public static final String GIVE_BOOK_TO_READER = "GIVE_BOOK_TO_READER";
	public static final String RETURN_BOOK = "RETURN_BOOK";
	public static final String ADD_BOOK = "ADD_BOOK";
	public static final String PRINT_BOOKS_BY_AUTHOR="PRINT_BOOKS_BY_AUTHOR";
	public static final String ENTER_USERNAME="ENTER_USERNAME";
	public static final String ENTER_PASSWORD="ENTER_PASSWORD";
	public static final String PRINT_BOOKS_BY_TITLE="PRINT_BOOKS_BY_TITLE";
	public static final String SUCCESSFULLY_RETURNED_BOOK = "SUCCESSFULLY_RETURNED_BOOK";
	public static final String NOT_LIBRARY_BOOK = "NOT_LIBRARY_BOOK";
	public static final String SUCCESSFUL_ADDED_BOOK = "SUCCESSFUL_ADDED_BOOK";
	public static final String REGISTER_READER = "REGISTER_READER";
	public static final String SUCCESSFUL_READER_REGISTRATION = "SUCCESSFUL_READER_REGISTRATION";
	public static final String UNSUCCESSFUL_READER_REGISTRATION = "UNSUCCESSFUL_READER_REGISTRATION";
	public static final String NOT_REGISTERED_READER = "NOT_REGISTERED_READER";
	public static final String NOT_VALID_READER_NAME = "NOT_VALID_READER_NAME";
	public static final String NOT_VALID_BOOK_TITLE = "NOT_VALID_BOOK_TITLE";
	public static final String NOT_VALID_AUTHOR_NAME = "NOT_VALID_AUTHOR_NAME";
	public static final String SEE_ALL_READERS = "PRINT_READERS";
	public static final String REGISTERED_READERS_MESSAGE="REGISTERED_READERS_MESSAGE";
	public static final String SUCCESSFULLY_GIVEN_BOOK = "SUCCESSFULLY_GIVEN_BOOK";
	public static final String TOO_MANY_INVALID_ATTEMPTS="TOO_MANY_INVALID_ATTEMPTS";
	public static final String EXISTING_READER = "EXISTING_READER";
	public static final String TAKEN_BOOKS_BY_READER="TAKEN_BOOKS_BY_READER";
	public static final String NOT_ANY_TAKEN_BOOKS="NOT_ANY_TAKEN_BOOKS";
	public static final String SEARCH_BY_TITLE = "SEARCH_BY_TITLE";
	public static final String PRINT_ALL_BOOKS="PRINT_ALL_BOOKS";
	public static final String PRINT_AVAILABLE_BOOKS="PRINT_AVAILABLE_BOOKS";
	public static final String PRINT_SORTED = "PRINT_SORTED";
	public static final String NOT_AVAILABLE_BOOK = "NOT_AVAILABLE_BOOK";
	public static final String NOT_ANY_BOOKS_WITH_TITLE = "NOT_ANY_BOOKS_WITH_TITLE";
	public static final String SEARCH_BY_AUTHOR_RROPERTY = "SEARCH_BY_AUTHOR";
	public static final String NOT_ANY_BOOKS_FROM_THIS_AUTHOR = "NOT_ANY_BOOKS_FROM_THIS_AUTHOR";
	public static final String CHANGE_LANGUAGE = "CHANGE_LANGUAGE";
	public static final String CHOOSE_BOOK_MESSAGE="CHOOSE_BOOK_MESSAGE";
    public static final String CHOOSE_READER_MESSAGE="CHOOSE_READER_MESSAGE";
    public static final String HAS_AVAILABLE_COPIES="HAS_AVAILABLE_COPIES";
	public static final String EXIT = "EXIT";
	public static final String GET_CHOICE = "GET_CHOICE";
	public static final String LANGUAGE = "language";
	public static final String BG_LANGUAGE = "bulgarian";
	public static final String  EN_LANGUAGE = "english";
	public static final String SHOW_BOOKS_OF_READER="SHOW_BOOKS_OF_READER";
	public static final String VIEW = "main.java.view";
	public static final String PERSISTENCY_PROPERTY = "persistencyType";
	public static final String FILE_SYSTEM_PERSISTENCY = "fileSystem";
	public static final String DATABASE_PERSISTENCY = "database";
	public static final String CONSOLE_VIEW = "console";
	public static final String NOT_READERS = "NOT_READERS";
	public static final String NOT_ANY_BOOKS = "NOT_ANY_BOOKS";
	public static final String ENTER_TITLE = "ENTER_TITLE";
	public static final String TITLE = "TITLE";
	public static final String ENTER_READER = "ENTER_READER";
	public static final String ENTER_AUTHOR = "ENTER_AUTHOR";
	public static final String PRESS = "PRESS";
	public static final String INVALID_CHOICE_PROPERTY = "INVALID_CHOICE";
	public static final String GOODBYE = "GOODBYE";
	public static final String PROPERTIES_FILE_EXTENSION = ".properties";
	public static final String BOOKS_XML_FILE_PATH = "C:\\Users\\i356406\\EEeclipse-workspace\\CityLibraryWeb\\WebContent\\WEB-INF\\lib\\resourses\\catalogue.xml";
	public static final String READERS_XML_FILE_PATH = "C:\\Users\\i356406\\EEeclipse-workspace\\CityLibraryWeb\\WebContent\\WEB-INF\\lib\\resourses\\readers.xml";
	public static final String LIBRARY_PROPERTIES_FILE_PATH = "C:\\Users\\i356406\\EEeclipse-workspace\\CityLibraryWeb\\WebContent\\WEB-INF\\lib\\resourses\\library.properties";
	public static final String DATABASE_PROPERTIES_FILEPATH = "C:\\Users\\i356406\\EEeclipse-workspace\\CityLibraryWeb\\WebContent\\WEB-INF\\lib\\resourses\\databaseProperties.properties";
	public static final String DATABASE_PROPERTIES_FILE="databasePersistencyPropertiesFile";
	public static final String UTF_8_ENCODING = "UTF-8";
	public static final String XML_BOOK_ELEMENT="book";
	public static final String XML_BOOK_WRAPPER_ELEMENT="books";
	public static final String XML_BOOK_TITLE_ELEMENT="title";
	public static final String XML_BOOK_AUTHOR_ELEMENT="author";
	public static final String XML_BOOK_COPIES_ELEMENT="copies";
	public static final String XML_READER_ELEMENT="reader";
	public static final String XML_READER_NAME_ELEMENT="name";
	public static final String XML_READER_WRAPPER_ELEMENT="readers";
	public static final String EXIT_SUBMENU="EXIT_SUBMENU";
	public static final String VALID_NAMETEXT_REGEX="(\\w*\\s*\\w*)*";
	public static final String DELIMITER=".";
	public static final int CHOICE_MENU_LOWER_BOUND=1;
	public static final int STANDARD_COPY_ADD_NUM=1;
	public static final int STANDARD_COPY_EXTRACT_NUM=-1;
	public static final int CONSOLE_MENU_LOWER_BOUND = 0;
	public static final int CONSOLE_MENU_UPPER_BOUND = 12;
	public static final int INVALID_CHOICE_NUMBER = -1;
	public static final int SEE_ALL_BOOKS_SORTED_BY_TITLE_ID = 1;
	public static final int SEE_ALL_BOOKS_SORTED_BY_AUTHOR_ID = 2;
	public static final int SEE_AVAILABLE_BOOKS_ID = 3;
	public static final int GIVE_BOOK_TO_READER_ID = 4;
	public static final int RETURN_BOOK_ID = 5;
	public static final int ADD_BOOK_ID = 6;
	public static final int REGISTER_READER_ID = 7;
	public static final int SEE_ALL_READERS_ID = 8;
	public static final int SHOW_BOOKS_OF_READER_ID=9;
	public static final int SEARCH_BY_TITLE_ID = 10;
	public static final int SEARCH_BY_AUTHOR_ID = 11;
	public static final int CHANGE_LANGUAGE_ID = 12;
	public static final int EXIT_COMMAND_ID = 0;
	public static final int STORED_FINAL_DATA=1;
	public static final int NOT_STORED_FINAL_DATA=0;
	public static final int SORT_BY_AUTHOR_ID=1;
	public static final int SORT_BY_TITLE_ID=2;

}