package com.jsp.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/CreditAmount")
public class CreditAmount extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String mobile = (String)session.getAttribute("mobile");
		
		String amount = req.getParameter("amount");
		double uAmount = Double.parseDouble(amount);
		
		String url = "jdbc:mysql://localhost:3306/teca45?user=root&password=12345";
		String select = "select * from bank where mobilenumber=?";
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1, mobile);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				double dAmount = rs.getDouble(5);
				if(uAmount>0)
				{
					double add = dAmount+uAmount;
					String update = "update bank set amount=? where mobilenumber=?";
					PreparedStatement ps2 = connection.prepareStatement(update);
					ps2.setDouble(1, add);
					ps2.setString(2, mobile);
					int result = ps2.executeUpdate();
					if(result!=0)
					{
						RequestDispatcher dispatcher = req.getRequestDispatcher("Welcome.html");
						dispatcher.include(req, resp);
						writer.println("<center><h3 style='position:relative; top:80px; color:green;'>Amount Credited successfully...</h3></center>");
					}
					else
					{
						RequestDispatcher dispatcher = req.getRequestDispatcher("WithdrawAmount.html");
						dispatcher.include(req, resp);
						writer.println("<center><h2>Enter the valid amount...!</h2></center>");
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
