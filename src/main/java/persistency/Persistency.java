package main.java.persistency;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

import main.java.Book;
import main.java.Catalog;
import main.java.Reader;
import main.java.Readers;

/**
 * Base interface for persistence which stores information about library books and readers.
 * This interface provides methods for data transactions and some operations like adding book and reader,
 * giving book to a reader and returning book.
 * @author I356406
 *
 */
public interface Persistency {

	/**
	 * Loads Readers from the persistence and return them.
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws Exception
	 */
	public Readers loadReaders() throws SQLException, IOException, ParserConfigurationException, SAXException;

	/**
	 * Loads books catalog from persistence and return reference to it.
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public Catalog loadBookCatalogue() throws SQLException, ParserConfigurationException, SAXException, IOException;

	/**
	 * Adds new reader to persistence if it is not already there.
	 * @param reader is the reader to add
	 * @throws ParserConfigurationException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 * @throws TransformerConfigurationException 
	 * @throws IOException 
	 * @throws PropertyVetoException 
	 * @throws SQLException 
	 */
	public abstract void addReader(Reader reader) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, SQLException, PropertyVetoException, IOException;

	/**
	 * Adds @param book to the catalog persistency.If the book is already there
	 * new copy of it is added.
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 * @throws TransformerConfigurationException 
	 * @throws SQLException 
	 * @throws PropertyVetoException 
	 */
	public abstract void addBook(Book book) throws IOException, TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, SQLException, PropertyVetoException;

	/**
	 * This method checks if passed reader and book arguments are in persistency.
	 * If they are in persistence,firstly book's information is deleted from
	 * reader's book list and then copies of that books are incremented. 
	 * 
	 * @param book is the book which should be returned.
     * @param reader is the reader which returns.
	 * @throws Exception 
	 * @throws TransformerFactoryConfigurationError 
	 */
	public abstract void returnBook(Reader reader, Book book) throws TransformerFactoryConfigurationError,SQLException, PropertyVetoException, IOException, Exception;

	/**
	 * This method checks if passed reader and book arguments are in persistency.
	 * If they are, Book referenced by @param book is added to the book list of @param reader.
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 * @throws TransformerConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws SQLException 
	 * @throws PropertyVetoException 
	 */
	public abstract void giveBookToReader(Reader reader, Book book) throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, ParserConfigurationException, SAXException, IOException, SQLException, PropertyVetoException ;


}
