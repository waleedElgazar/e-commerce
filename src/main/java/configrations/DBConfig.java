package configrations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {

	private static Connection connection= null;
	
	public static Connection getConnection() throws ClassNotFoundException {
		if(connection==null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/e-commerce","root","root");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Connected..");
		}
		return connection;
	}
	
}
