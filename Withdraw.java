package com.jsp.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		String mobile = req.getParameter("mobile");
		String password = req.getParameter("password");
		
		String url = "jdbc:mysql://localhost:3306/teca45?user=root&password=12345";
		String select = "select * from bank where mobilenumber=? and password=?";
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1, mobile);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				HttpSession session = req.getSession();
				session.setAttribute("mobile", mobile);
				RequestDispatcher dispatcher = req.getRequestDispatcher("WithdrawAmount.html");
				dispatcher.include(req, resp);
				
			}
			else
			{
				RequestDispatcher dispatcher = req.getRequestDispatcher("Withdraw.html");
				dispatcher.include(req, resp);
				writer.println("<center><h2>Invalid Details...</h2></center>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
