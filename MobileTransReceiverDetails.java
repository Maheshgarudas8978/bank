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
@WebServlet("/MobileTransReceiverDetails")
public class MobileTransReceiverDetails extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String mobile = req.getParameter("mobileR");
		
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
				HttpSession session = req.getSession();
				session.setAttribute("mobileR", mobile);
				RequestDispatcher dispatcher = req.getRequestDispatcher("MobileTranEnterAmount.html");
				dispatcher.include(req, resp);
			}
			else
			{
				RequestDispatcher dispatcher = req.getRequestDispatcher("MobileTransReceiverDetails.html");
				dispatcher.include(req, resp);
				writer.println("<center><h2 style='position:relative; top:200px;'>Invalid mobile number...</h2></center>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
