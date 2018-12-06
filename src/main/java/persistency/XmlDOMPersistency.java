package main.java.persistency;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Set;

import javax.xml.parsers.*;

import main.java.Book;
import main.java.Catalogue;
import main.java.Constants;
import main.java.LibraryBook;
import main.java.Reader;
import main.java.Readers;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * XmlDOMPersistency implements Persistency interface. This class is responsible
 * for managing XMLfile persistency which holds information about books and
 * readers.It provides methods for adding readers and books ,loading and storing
 * library information to storage and other manipulations with books and
 * readers(like giving book to reader ,returning book etc). This persistency
 * implementation contains persistency file for each XMLDocument. Every
 * modification of data is saved.
 * 
 * @author I356406
 *
 */

public class XmlDOMPersistency implements Persistency {

	private File catalogueFile;
	private File readersFile;
	private Document catalogueDoc;
	private Document readersDoc;

	/**
	 * Creates new XMlDomPersistency class which manages XML files persistency.
	 * @throws SAXException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 * @throws TransformerConfigurationException 
	 * 
	 * @throws Exception
	 */
	public XmlDOMPersistency() throws  IOException, TransformerException, TransformerFactoryConfigurationError, SAXException, ParserConfigurationException {
		initDomPersistency();
	}

	private void initDomPersistency() throws IOException, TransformerException, TransformerFactoryConfigurationError, SAXException, ParserConfigurationException  {
		setReadersDocForLoading();
		setCatalogueDocForLoading();
	}

	/**
	 * This method is used for setting the file document from which to load Readers.
	 * 
	 * @param readersDocBuilder is the DocumentBuilder which is used for
	 *                          parsing/creating readers document
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 * @throws TransformerConfigurationException 
	 * @throws SAXException 
	 * @throws Exception
	 */
	private void setReadersDocForLoading() throws  IOException,  ParserConfigurationException, TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, SAXException  {
		DocumentBuilderFactory readersDocFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder readersDocBuilder = readersDocFactory.newDocumentBuilder();
		readersFile = new File(Constants.READERS_XML_FILE_PATH);
		if (!readersFile.exists()) {
			readersFile.createNewFile();
			readersDoc = readersDocBuilder.newDocument();
			Element root = readersDoc.createElement(Constants.XML_READER_WRAPPER_ELEMENT);
			readersDoc.appendChild(root);
			outputAsFile(readersDoc, readersFile);
		}
		readersDoc = readersDocBuilder.parse(readersFile);

	}

	/**
	 * This method is used for setting the right file document from which to load
	 * Catalogue.
	 * 
	 * @param catalogueBuilder is the DocumentBuilder which is used for
	 *                         parsing/creating ctalogue document
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 * @throws TransformerConfigurationException 
	 * @throws SAXException 
	 * @throws Exception
	 */
	private void setCatalogueDocForLoading() throws ParserConfigurationException, IOException, TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, SAXException  {

		DocumentBuilderFactory catalogueDocFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder catalogueDocBuilder = catalogueDocFactory.newDocumentBuilder();
		catalogueFile = new File(Constants.BOOKS_XML_FILE_PATH);

		if (!catalogueFile.exists()) {
			catalogueFile.createNewFile();
			catalogueDoc = catalogueDocBuilder.newDocument();
			Element root = catalogueDoc.createElement(Constants.XML_BOOK_WRAPPER_ELEMENT);
			catalogueDoc.appendChild(root);
			outputAsFile(catalogueDoc, catalogueFile);
		}

		catalogueDoc = catalogueDocBuilder.parse(catalogueFile);
	}

	/**
	 * Creates new reader element.
	 * 
	 * @param reader is Reader from which info for new element is taken
	 * @param doc    is the document to which the element is appended
	 * @return new Reader Element
	 * @throws ParserConfigurationException
	 */
	private Element createReader(Reader reader, Document doc) throws ParserConfigurationException {
		Element readerELement = doc.createElement(Constants.XML_READER_ELEMENT);
		Element name = doc.createElement(Constants.XML_READER_NAME_ELEMENT);
		name.setTextContent(reader.getName());
		readerELement.appendChild(name);
		for (Book b : reader.getReaderBooks()) {
			Element toAdd = addBookElement(b, doc);
			readerELement.appendChild(toAdd);
		}
		return readerELement;
	}

	/**
	 * This method creates new bookElement.
	 * 
	 * @param book is Book object containing the information which Element will
	 *             contain
	 * @param doc  is the document to which element is appended
	 * @return newly created book element
	 */
	private Element addBookElement(Book book, Document doc) {
		Element bookElement = doc.createElement(Constants.XML_BOOK_ELEMENT);
		Element title = doc.createElement(Constants.XML_BOOK_TITLE_ELEMENT);
		title.setTextContent(book.getTitle());
		Element author = doc.createElement(Constants.XML_BOOK_AUTHOR_ELEMENT);
		author.setTextContent(book.getAuthor());
		bookElement.appendChild(title);
		bookElement.appendChild(author);
		return bookElement;
	}

	/**
	 * This method creates new library book Element.
	 * 
	 * @param book is LibraryBook object containing the information which Element
	 *             will contain
	 * @param doc  is the document to which element is appended
	 * @return newly created library book element
	 */
	private Element createLibraryBookElem(LibraryBook book, Document doc) {
		Element bookToAdd = addBookElement(book, doc);
		Element copies = doc.createElement(Constants.XML_BOOK_COPIES_ELEMENT);
		String numOfCopies = book.getCopies().toString();
		copies.setTextContent(numOfCopies);
		bookToAdd.appendChild(copies);
		return bookToAdd;
	}

	/**
	 * Returns transformer which is used for transforming XML document tree to
	 * stream
	 * 
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerConfigurationException
	 */
	private Transformer getTransformer()
			throws TransformerFactoryConfigurationError, TransformerConfigurationException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		return transformer;
	}

	/**
	 * This method transforms the content of the @param doc to the file referenced
	 * by @param file.
	 * 
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 * @throws TransformerFactoryConfigurationError
	 */
	private void outputAsFile(Document doc, File file)
			throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError {
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(file);
		getTransformer().transform(source, result);
	}

	/**
	 * This method loads readers from XML document by creating new reader from the
	 * valid reader elements in readers document.
	 * 
	 * @return Readers containing successfully parsed reader objects. If there is
	 *         not any valid reader information new empty Readers object is returned
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	@Override
	public Readers loadReaders() throws ParserConfigurationException, SAXException, IOException {
		Readers readersList = new Readers();
		Element root = readersDoc.getDocumentElement();
		NodeList readers = root.getElementsByTagName(Constants.XML_READER_ELEMENT);
		for (int i = 0; i < readers.getLength(); ++i) {
			Reader newReader = parseReader((Element) readers.item(i));
			if (newReader != null) {
				readersList.add(newReader);
			}
		}
		DocumentBuilderFactory readersDocFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder readersDocBuilder = readersDocFactory.newDocumentBuilder();
		readersFile = new File(Constants.READERS_XML_FILE_PATH);
		readersDoc = readersDocBuilder.parse(readersFile);
		return readersList;
	}

	/**
	 * This method loads books from XML document by creating new library book from
	 * the valid book elements in catalogue document
	 * 
	 * @return Catalogue containing successfully parsed book objects. If there is
	 *         not any valid book information,new empty Catalogue is returned.
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	@Override
	public Catalogue loadBookCatalogue() throws ParserConfigurationException, SAXException, IOException {
		Catalogue toLoad = new Catalogue();
		Element root = catalogueDoc.getDocumentElement();
		NodeList books = root.getElementsByTagName(Constants.XML_BOOK_ELEMENT);
		for (int j = 0; j < books.getLength(); ++j) {
			LibraryBook bookToAdd = parseLibraryBook((Element) books.item(j));
			if (bookToAdd != null) {
				toLoad.addLibraryBook(bookToAdd);
			}
		}
		DocumentBuilderFactory catalogueDocFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder catalogueDocBuilder = catalogueDocFactory.newDocumentBuilder();
		catalogueFile = new File(Constants.BOOKS_XML_FILE_PATH);
		catalogueDoc = catalogueDocBuilder.parse(catalogueFile);
		return toLoad;
	}

	/**
	 * This method takes @param readerToParse which is Element and if it is Reader
	 * element its content is parsed and used to create new Reader object.
	 * 
	 * @return Reader if element is of type book and null otherwise
	 */
	private Reader parseReader(Element readerToParse) {
		if (!readerToParse.getTagName().equals(Constants.XML_READER_ELEMENT)) {
			return null;
		}
		Reader newReader = new Reader();
		Node nameNode = readerToParse.getElementsByTagName(Constants.XML_READER_NAME_ELEMENT).item(0);
		String name = nameNode.getTextContent();
		newReader.setName(name);
		NodeList books = readerToParse.getElementsByTagName(Constants.XML_BOOK_ELEMENT);
		for (int j = 0; j < books.getLength(); ++j) {
			Element currentBook = (Element) books.item(j);
			Book toAdd = parseBook(currentBook);
			newReader.addBook(toAdd);
		}
		return newReader;
	}

	/**
	 * This method takes @param Element book which is Element and if it is a Book
	 * element its content is parsed and used to create new Book object
	 * 
	 * @return Book if element is of type book and null otherwise
	 */
	private Book parseBook(Element book) {
		if (!book.getTagName().equals(Constants.XML_BOOK_ELEMENT)) {
			return null;
		}
		Node titleNode = book.getElementsByTagName(Constants.XML_BOOK_TITLE_ELEMENT).item(0);
		String title = titleNode.getTextContent();
		Node authorNode = book.getElementsByTagName(Constants.XML_BOOK_AUTHOR_ELEMENT).item(0);
		String author = authorNode.getTextContent();
		return new Book(author, title);
	}

	/**
	 * This method takes @param libraryBook which is Element and if it is
	 * LibraryBook element its content is parsed and used to create new LibraryBook
	 * object
	 * 
	 * @return LibraryBook if element is of type book and null otherwise
	 */
	private LibraryBook parseLibraryBook(Element libraryBook) {
		if (!libraryBook.getTagName().equals(Constants.XML_BOOK_ELEMENT)) {
			return null;
		}
		Node titleNode = libraryBook.getElementsByTagName(Constants.XML_BOOK_TITLE_ELEMENT).item(0);
		String title = titleNode.getTextContent();
		Node authorNode = libraryBook.getElementsByTagName(Constants.XML_BOOK_AUTHOR_ELEMENT).item(0);
		String author = authorNode.getTextContent();
		Node numOfCopies = libraryBook.getElementsByTagName(Constants.XML_BOOK_COPIES_ELEMENT).item(0);
		Integer copies = Integer.parseInt(numOfCopies.getTextContent());
		return new LibraryBook(author, title, copies);
	}

	/**
	 * Adds new reader element to the readers file.
	 * 
	 * @param reader contains the information for creating new Reader element.
	 * @throws ParserConfigurationException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 * @throws TransformerConfigurationException 
	 */
	@Override
	public void addReader(Reader reader) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError{
		Element newReader = createReader(reader, readersDoc);
		Element root = readersDoc.getDocumentElement();
		root.appendChild(newReader);
		outputAsFile(readersDoc, readersFile);
	}

	/**
	 * Check if @param Book book is in the books catalogue Document
	 * 
	 * @return reference to the element corresponding to @param book if the book is
	 *         in catalogue and null otherwise.
	 */
	private Element findBook(Book book) {
		NodeList books = catalogueDoc.getElementsByTagName(Constants.XML_BOOK_ELEMENT);
		for (int i = 0; i < books.getLength(); i++) {
			Element currentBookElem = (Element) books.item(i);
			LibraryBook currentBook = parseLibraryBook(currentBookElem);
			if (currentBook.equals(book)) {
				return currentBookElem;
			}
		}
		return null;
	}

	/**
	 * This method changes the copies of a book if it is in the catalogue.
	 * 
	 * @param changeAmount contains the number which will be added/removed(if
	 *                     negative) to copies of @param book
	 * @return true if @param bookToChange is in the catalogue so it can be changed.
	 *         Otherwise returns false.
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 * @throws TransformerConfigurationException 
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	private boolean changeBookCopies(Book book, Integer changeAmount) throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError {
		Element searchedBookElement = findBook(book);
		if (searchedBookElement == null) {
			return false;
		}
		String copiesStr = searchedBookElement.getElementsByTagName(Constants.XML_BOOK_COPIES_ELEMENT).item(0)
				.getTextContent();
		Integer copies = Integer.parseInt(copiesStr) + changeAmount;
		searchedBookElement.getElementsByTagName(Constants.XML_BOOK_COPIES_ELEMENT).item(0)
				.setTextContent(copies.toString());
		outputAsFile(catalogueDoc, catalogueFile);
		return true;

	}

	/**
	 * Adds @param book to the catalogue document.If the book is already there new
	 * copy of it is added.
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 * @throws TransformerConfigurationException 
	 */
	@Override
	public void addBook(Book bookToAdd) throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError{
		if (!changeBookCopies(bookToAdd, Constants.STANDARD_COPY_ADD_NUM)) {
			LibraryBook newBook = new LibraryBook(bookToAdd.getAuthor(), bookToAdd.getTitle(),
					Constants.STANDARD_COPY_ADD_NUM);
			catalogueDoc.getDocumentElement().appendChild(createLibraryBookElem(newBook, catalogueDoc));
			outputAsFile(catalogueDoc, catalogueFile);
		}
	}

	/**
	 * This method search for @param reader in readers document and if the reader is
	 * found element corresponding to that reader is returned.
	 * 
	 * @return Element reader if the reader is in the persistency and null
	 *         otherwise.
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	private Element findReader(Reader reader) throws ParserConfigurationException, SAXException, IOException {
		NodeList readers = readersDoc.getElementsByTagName(Constants.XML_READER_ELEMENT);
		for (int i = 0; i < readers.getLength(); ++i) {
			Element currentReader = (Element) readers.item(i);
			String readerName = currentReader.getElementsByTagName(Constants.XML_READER_NAME_ELEMENT).item(0)
					.getTextContent();
			if (readerName.equals(reader.getName())) {
				return currentReader;
			}
		}
		return null;
	}

	/**
	 * Checks if passed reader and book arguments are in persistency. If they are
	 * there,firstly book's number of copies is changed in catalogue document. Then
	 * book element is added to corresponding reader's books document tree. Both
	 * documents are saved.
	 * 
	 * @param book   is the book which is given.
	 * @param reader is the reader which takes the book.
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 * @throws TransformerConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	@Override
	public void giveBookToReader(Reader reader, Book book) throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, ParserConfigurationException, SAXException, IOException{
		Element readerToModify = findReader(reader);
		if (readerToModify == null) {
			return;
		}
		Node elemToAdd = addBookElement(book, readersDoc);
		readerToModify.appendChild(elemToAdd);
		outputAsFile(readersDoc, readersFile);
		changeBookCopies(book, Constants.STANDARD_COPY_EXTRACT_NUM);
	}

	/**
	 * Checks if passed reader and book arguments are in persistency. If they are
	 * there,firstly book's information is deleted from readers document. Then
	 * copies of that books are incremented in catalogue document. Both documents
	 * are saved.
	 * 
	 * @param book   is the book which is being returned.
	 * @param reader is the reader which returns.
	 * @throws TransformerFactoryConfigurationError 
	 * @throws Exception 
	 */
	@Override
	public void returnBook(Reader reader, Book book) throws TransformerFactoryConfigurationError, Exception{
		Element readerToModify = findReader(reader);
		if (readerToModify == null) {
			return;
		}
		NodeList books = readerToModify.getElementsByTagName(Constants.XML_BOOK_ELEMENT);
		for (int i = 0; i < books.getLength(); ++i) {
			Element currentBookElem = (Element) books.item(i);
			Book currentBook = parseBook(currentBookElem);
			if (currentBook != null && currentBook.equals(book)) {
				readerToModify.removeChild(currentBookElem);
				outputAsFile(readersDoc, readersFile);
				changeBookCopies(book, Constants.STANDARD_COPY_ADD_NUM);
				return;
			}
		}
	}

}
