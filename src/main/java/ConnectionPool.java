package main.java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectionPool implements Runnable {

	private static final String JDBC_URL = "JDBC_URL";
	private static final String SERVER_NAME = "SERVER_NAME";
	private static final String PORT_NUMBER = "PORT_NUMBER";
	private static final String DRIVER_CLASS_NAME = "DRIVER_CLASS_NAME";
	private static final String DATABASE_NAME = "DATABASE_NAME";
	private static final int MAX_CONNECTIONS_NUMBER = 10;

	private List<Connection> availableConnections;
	private List<Connection> busyConnections;
	private String url;
	private String driverName;
	private boolean waitIfBusy;
	private boolean connectionPending = false;
	private boolean waitingConnectionFailed = false;
	private char[] username;
	private char[] password;

	/**
	 * Creates a pool of Data Base connections.
	 * 
	 * @param initialConnectionsSize is the initial size of the pool
	 * @param waitIfBusy             indicates if the current thread to wait some
	 *                               connection to be able if all the connections
	 *                               are busy at the moment
	 * @param username
	 * @param password
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ConnectionPool(int initialConnectionsSize, boolean waitIfBusy, char[] username, char[] password)
			throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		initDatabaseProperties();
		this.waitIfBusy = waitIfBusy;
		this.username = username;
		this.password = password;
		int numberOfConnections = initialConnectionsSize;
		if (initialConnectionsSize > MAX_CONNECTIONS_NUMBER) {
			numberOfConnections = MAX_CONNECTIONS_NUMBER;
		}
		availableConnections = new ArrayList<Connection>(numberOfConnections);
		busyConnections = new ArrayList<Connection>();
		for (int i = 0; i < numberOfConnections; i++) {
			availableConnections.add(makeNewConnection());
		}

	}

	/**
	 * The method returns a DB Connection if an available one exists. 
	 * If no connection is available, there are three possible cases: 
	 * 1) if MAX_CONNECTIONS_NUMBER has not been reached yet - 
	 * then a new one is being established, in case there is not an already 
	 * pending one and the next available connection is being waited. 
	 * 
	 * 2) If MAX_CONNECTIONS_NUMBER had been reached and the flag waitifbusy 
	 * is false, then a new SQLException is thrown.
	 * 
	 * 3) If MAX_CONNECTIONS_NUMBER is reached and waitifbusy is true then current thread
	 * waits until someone releases a connection. Then the whole process is
	 * repeated.
	 * 
	 * @return
	 * @throws SQLException if max connection limits is reached
	 */
	public synchronized Connection getConnection() throws SQLException {
		if (!availableConnections.isEmpty()) {
			int lastIndex = availableConnections.size() - 1;
			Connection existingConnection = availableConnections.get(lastIndex);
			availableConnections.remove(lastIndex);
			if (existingConnection.isClosed()) {
				notifyAll();
				return (getConnection());
			} else {
				busyConnections.add(existingConnection);
				return (existingConnection);
			}
		} else {
			if ((totalConnections() < MAX_CONNECTIONS_NUMBER) && !connectionPending) {
				waitingConnectionFailed = false;
				makeBackgroundConnection();
				if (waitingConnectionFailed) {
					throw new SQLException("Connection is not created");
				}
			} else if (!waitIfBusy) {
				throw new SQLException("Connection limit reached");
			}
			try {
				wait();
			} catch (InterruptedException ie) {
				
			}
			return (getConnection());
		}
	}

	/**
	 * This method is invoked when there are not any avaialble connections at that
	 * moment. It starts a new thread which establishes a new connection and then
	 * waits for connection.
	 */
	private void makeBackgroundConnection() {
		connectionPending = true;
		Thread connectThread = new Thread(this);
		connectThread.start();
	}

	/**
	 * Initializes the database properties using the information provided in the
	 * databaseProperties file,which path is indicated in
	 * LibraryController.DATABASE_PROPERTIES_FILEPATH.
	 * 
	 */
	private void initDatabaseProperties() throws IOException, FileNotFoundException {
		Properties databaseProperties = new Properties();
		try (FileInputStream fileInput = new FileInputStream(Constants.DATABASE_PROPERTIES_FILEPATH)) {
			databaseProperties.load(fileInput);
			driverName = databaseProperties.getProperty(DRIVER_CLASS_NAME);
			url = databaseProperties.getProperty(JDBC_URL) + databaseProperties.getProperty(SERVER_NAME) + ":"
					+ databaseProperties.getProperty(PORT_NUMBER) + ";databaseName="
					+ databaseProperties.getProperty(DATABASE_NAME);
		}
	}

	/**
	 * Creates new connection using the provided username and password arguments.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private Connection makeNewConnection() throws SQLException {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder usernameToStr = new StringBuilder();
		StringBuilder passwordToStr = new StringBuilder();
		for (int i = 0; i < username.length; ++i) {
			usernameToStr.append(username[i]);
		}
		for (int i = 0; i < password.length; ++i) {
			passwordToStr.append(password[i]);
		}
		Connection connection = DriverManager.getConnection(url, usernameToStr.toString(), passwordToStr.toString());

		return connection;
	}

	/**
	 * Makes the provided connection available to use again.
	 * 
	 * @param connection
	 */
	public synchronized void free(Connection connection) {
		busyConnections.remove(connection);
		availableConnections.add(connection);
		notifyAll();
	}

	/**
	 * Closes all the connections in the pool,no matter if they are avaialble or
	 * busy
	 * 
	 * @throws SQLException
	 */
	public synchronized void closeAllConnections() throws SQLException {
		closeConnections(availableConnections);
		availableConnections = new ArrayList<Connection>();
		closeConnections(busyConnections);
		busyConnections = new ArrayList<Connection>();
	}

	/**
	 * Closes all the connection in the provided list argument.
	 * 
	 * @param connections
	 */
	private void closeConnections(List<Connection> connections) throws SQLException {
		for (Connection connection : connections) {
			connection.close();
		}
	}

	/**
	 * Returns the total size of connections.
	 * 
	 * @return
	 */
	public synchronized int totalConnections() {
		return (availableConnections.size() + busyConnections.size());
	}

	/**
	 * Makes new connection and adds it to available connections.Then wakes up all
	 * the threads waiting for available connection.
	 */
	@Override
	public void run() {
		Connection connection = null;
		try {
			connection = makeNewConnection();
		} catch (SQLException e) {
			waitingConnectionFailed = true;
			e.printStackTrace();
		}
		if (waitingConnectionFailed != true) {
			synchronized (this) {
				availableConnections.add(connection);
				connectionPending = false;
				notifyAll();
			}
		}
	}
}