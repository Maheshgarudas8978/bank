package com.jsp.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/NewAccountPasswordPage")
public class NewAccountPasswordPage extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String mobile = (String)session.getAttribute("mobile");
		
		String password1 = req.getParameter("password1");
		String password2 = req.getParameter("password2");
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		String url  = "jdbc:mysql://localhost:3306/teca45?user=root&password=12345";
		String update = "update bank set password=? where mobilenumber=?";
		if(password1.equals(password2))
		{
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement ps = connection.prepareStatement(update);
				ps.setString(1, password2);
				ps.setString(2, mobile);
				int result = ps.executeUpdate();
				if(result!=0)
				{
					RequestDispatcher dispatcher = req.getRequestDispatcher("Welcome.html");
					dispatcher.include(req, resp);
					writer.println("<center><h1 style='color:green; position:relative;top:100px;'>Account Created Successfully.....</h1></center>");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			RequestDispatcher dispatcher = req.getRequestDispatcher("NewAccountPasswordPage.html");
			dispatcher.include(req, resp);
			writer.println("<center><h2 style='color:red;position:relative; top:120px;'>Passwords Mismatch</h2></center>");
		}
	}
}
