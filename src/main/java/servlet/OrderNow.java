package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import configrations.DBConfig;
import dao.OrderDao;
import models.Cart;
import models.Order;
import models.User;

/**
 * Servlet implementation class OrderNow
 */
@WebServlet("/order-now")
public class OrderNow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out=response.getWriter()){
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-mm-dd");
			Date date=new Date();
			
			User user=(User)request.getSession().getAttribute("auth");
			if(user!=null) {
				String productId=request.getParameter("id");
				int quantity=Integer.parseInt(request.getParameter("quantity"));
				if(quantity<=0) {
					quantity=1;
				}
				
				Order orderModel=new Order();
				orderModel.setId(Integer.parseInt(productId));
				orderModel.setUserId(user.getId());
				orderModel.setQuantity(quantity);
				orderModel.setDate(formatter.format(date));
				OrderDao orderDao= new OrderDao(DBConfig.getConnection());
				Boolean result= orderDao.insertOrder(orderModel);
				
				ArrayList<Cart> cart_list=(ArrayList<Cart>)request.getSession().getAttribute("cart-list");
				if(cart_list!=null) {
					for(Cart c: cart_list) {
						if(c.getId()==Integer.parseInt(productId)) {
							cart_list.remove(cart_list.indexOf(c));
							break;
						}
					}
				}
				if(result) {
					response.sendRedirect("orders.jsp");
				}else {
					out.print("order failed");
				}
			}else {
				response.sendRedirect("login.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
