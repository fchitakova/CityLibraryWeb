package main.java;

import java.util.HashSet;

import main.java.exceptions.MissingReaderException;

/**
 * This is reader wrapper class.It contains set of readers and provides access
 * to the information.
 * 
 * @author I356406
 *
 */

public class Readers {

	private HashSet<Reader> readers;

	public Readers() {
		readers = new HashSet<Reader>();
	}

	public HashSet<Reader> getReaders() {
		return readers;
	}

	public void setReaders(HashSet<Reader> readers) {
		this.readers = readers;
	}

	public boolean contains(Reader r) {
		return readers.contains(r);
	}

	public boolean add(Reader r) {
		return readers.add(r);
	}

	/**
	 * This method checks for a reader with the provided name. If such a reader
	 * is registered, it is returned. Otherwise MissingReaderException is thrown.
	 * 
	 * @param readerName is the name of searched reader
	 * @throws MissingReaderException 
	 */
	public Reader getReaderFromSet(String readerName) throws MissingReaderException {
		for (Reader i : readers) {
			if (i.getName().equals(readerName)) {
				return i;
			}
		}
		throw new MissingReaderException(Constants.NOT_REGISTERED_READER);

	}
}