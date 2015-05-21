package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreConnection {

	private static final String DRIVER = "org.postgresql.Driver";
	private static final String DB_PREFIX = "jdbc:postgresql://";

	public PostgreConnection() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			String errorMessage = "Unable to find driver!";
			throw new UgyfelDaoException(errorMessage);
		}
	}

	public Connection getConnection() {

		Connection connection = null;

		try {

			String url = DB_PREFIX + "localhost:" + "5432/" + "UGYFEL";
			String user = "ugyfelUser";
			String password = "ugyfelPassword";
			connection = DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			String errorMessage = "Unable to connect to database!";
			throw new UgyfelDaoException(errorMessage, e.getCause());
		}

		if (connection == null) {
			throw new UgyfelDaoException("Fail to connect!");
		}
		return connection;
	}

}
