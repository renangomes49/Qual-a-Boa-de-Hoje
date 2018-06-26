package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
	
	private static final String ip = "localhost";
	private static final int port = 5432;
	private static final String user = "postgres";
	private static final String password = "123456"; // senha do db
	private static final String database = "eventos"; // nome do db


	public static Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:postgresql://" + ip + ":" + port + "/" + database, user, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
