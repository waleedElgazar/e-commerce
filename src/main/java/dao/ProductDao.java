package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.Cart;
import models.Product;

public class ProductDao {
	private Connection con;
	private String query;
	private PreparedStatement stmt;
	private ResultSet rs;
	public ProductDao(Connection con) {
		super();
		this.con = con;
	}
	public List<Product> getAllProducts(){
		List<Product> products=new ArrayList<>();
		try {
			query="SELECT * FROM product";
			stmt=this.con.prepareStatement(query);
			rs=stmt.executeQuery();
			while(rs.next()) {
				Product row=new Product();
				row.setId(rs.getInt("id"));
				row.setCategory(rs.getString("category"));
				row.setName(rs.getString("name"));
				row.setPrice(rs.getDouble("price"));
				row.setImage(rs.getString("image"));
				products.add(row);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}

	public List<Cart> getCartProduct(ArrayList<Cart> cartList){
		List<Cart> products=new ArrayList<>();
		try {
			if(cartList.size()>0) {
				for(Cart item:cartList) {
					query="SELECT * FROM product WHERE id=?";
					stmt=this.con.prepareStatement(query);
					stmt.setInt(1, item.getId());
					rs=stmt.executeQuery();
					while(rs.next()) {
						Cart row=new Cart();
						row.setId(rs.getInt("id"));
						row.setCategory(rs.getString("category"));
						row.setName(rs.getString("name"));
						row.setPrice(rs.getDouble("price")*item.getQuantity());
						row.setQuantity(item.getQuantity());
						products.add(row);
					}
				}
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return products;
	}
	public double getTotalCartPrice(ArrayList<Cart> cartList) {
		double sum=0;
		
		try {
			if(cartList.size()>0) {
				for(Cart item:cartList) {
					query="SELECT price FROM product WHERE id=?";
					stmt=this.con.prepareStatement(query);
					stmt.setInt(1, item.getId());
					rs=stmt.executeQuery();
					while(rs.next()) {
						sum+=rs.getDouble("price")*item.getQuantity();
					}
			
				}
			}}catch (Exception e) {
			e.printStackTrace();
		}
		
		return sum;
	}
}
