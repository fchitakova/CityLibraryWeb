package main.java;

import java.util.HashSet;

/**
 * This is reader wrapper class.It contains set of readers and provides access to the information.
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
	 * This method checks if the reader is in the reader's set and if it is there returns
	 * reference to it. Otherwise returns null.
	 * 
	 * @param reader is the reference to the searched reader's object representation
	 */
	public Reader getReaderFromSet(Reader reader) {
		if (readers.contains(reader)) {
			for (Reader i : readers) {
				if (i.equals(reader)) {
					return i;
				}
			}
		}
		return null;
	}
}