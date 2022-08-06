<%@page import="java.util.*"%>
<%@page import="dao.ProductDao"%>
<%@page import="models.*"%>
<%@page import="configrations.DBConfig"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
User auth = (User) request.getSession().getAttribute("auth");
if (auth != null) {
	request.setAttribute("auth", auth);
}

ProductDao productDao=new ProductDao(DBConfig.getConnection());

List<Product> products =productDao.getAllProducts();

ArrayList<Cart> cart_list=(ArrayList<Cart>) session.getAttribute("cart-list");
if(cart_list!=null){
	request.setAttribute("cart_list", cart_list);
}
%>


<!DOCTYPE html>
<html>
<head>
<%@include file="includes/head.jsp"%>
<title>Shopping</title>
</head>
<body>
	<%@include file="includes/navbar.jsp"%>
	<div class="container">
		<div class="card-header my-3">All Products</div>
		<div class="row">
		<%
			if(!products.isEmpty()){
				for(Product p:products){
				%>
				<div class="col-md-3 my-3">
				<div class="card w-100" style="width: 18rem;">
					<img class="card-img-top" src="product-images/<%=p.getImage()%>.jpg" alt="Card image cap">
					<div class="card-body">
						<h5 class="card-title"> <%= p.getName() %></h5>
						<h6 class="price">Price: $<%= p.getPrice()%></h6>
						<h6 class="category">Category: <%= p.getCategory() %></h6>
						<div class="mt-3 d-flec justify-content-between">
							<a href="add-to-cart?id=<%= p.getId() %>" class="btn btn-dark">Add to Cart</a> 
							<a href="order-now?uantity=1&id=1" class="btn btn-primary">Buy Now</a>

						</div>
					</div>
				</div>
			</div>
				<%}
			}
		%>
			
		</div>
	</div>
	<%@include file="includes/footer.jsp"%>
</body>
</html>
