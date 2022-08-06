package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import models.Order;

public class OrderDao {
	private Connection con;
	private String query;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	public OrderDao(Connection con) {
		this.con=con;
	}
	public boolean insertOrder(Order order) {
		boolean result=false;
		
		try {
			query="INSERT INTO orders(product_id,user_id,quantity,order_date) VALUES(?,?,?,?)";
			stmt=this.con.prepareStatement(query);
			stmt.setInt(1, order.getId());
			stmt.setInt(2, order.getUserId());
			stmt.setInt(3, order.getQuantity());
			stmt.setString(4, order.getDate());
			stmt.executeUpdate();
			result=true;
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
