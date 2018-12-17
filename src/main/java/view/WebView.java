package main.java.view;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Servlet;

import main.java.Book;
import main.java.LibraryBook;
import main.java.Reader;
import main.java.controller.MultipleSubmitButtons;

public class WebView extends View{
	
	
	private Servlet menuManagingServlet;

	public WebView(String language) throws IOException {
		super(language);
		menuManagingServlet=new MultipleSubmitButtons();
	}

	@Override
	public void printReaders(HashSet<Reader> readers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printBooks(Collection<? extends Book> books, String infoMessege, String errorMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printAvailableBooks(Set<LibraryBook> books, String infoMessage, String errorMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printResource(String propertyName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Reader chooseReader(Set<Reader> readers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChoice(int menuLowerBound, int menuUpperBound) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Book chooseFromTakenBooks(Set<Book> books) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book chooseFromAvaialbale(Set<LibraryBook> books) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getTextInputInfo(String infoMessege, String ifNotValidMessege) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book getBook() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChosenAction() {
		// TODO Auto-generated method stub
		return 0;
	}

}
