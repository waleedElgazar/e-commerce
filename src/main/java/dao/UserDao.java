package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import models.User;

public class UserDao {

	private Connection con;
	private String query;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	public UserDao(Connection con) {
		this.con=con;
	}
	public User Login (String email,String pass) {
		User user=null;
		try {
			query="SELECT * FROM users WHERE email=? AND password=?";
			stmt=this.con.prepareStatement(query);
			stmt.setString(1, email);
			stmt.setString(2, pass);
			rs=stmt.executeQuery();
			if(rs.next()) {
				user=new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return user;
	}
}
