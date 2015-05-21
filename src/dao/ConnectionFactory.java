package dao;

import java.sql.Connection;

public class ConnectionFactory {

	private static PostgreConnection postgreConnection = new PostgreConnection();

	private ConnectionFactory() {

	}

	public static Connection getPostgreConncection() {
		return postgreConnection.getConnection();
	}

}
