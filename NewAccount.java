package com.jsp.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/NewAccount")
public class NewAccount extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String name = req.getParameter("name");
		String mobile = req.getParameter("mobile");
		String email = req.getParameter("email");
		String aadhar = req.getParameter("aadhar");
		String pan = req.getParameter("pan");
		
		String accountno = mobile.substring(0, 4) + aadhar.substring(0, 4) + pan.substring(0, 2);
		
		String url  = "jdbc:mysql://localhost:3306/teca45?user=root&password=12345";
		String insert = "insert into bank(username, mobilenumber,accountno,aadharNo, panNo) values(?,?,?,?,?)";
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(insert);
			ps.setString(1, name);
			ps.setString(2, mobile);
			ps.setString(3, accountno);
			ps.setString(4, aadhar);
			ps.setString(5, pan);
			int result = ps.executeUpdate();
			if(result!=0)
			{
				Random r = new Random();
				int otp = r.nextInt(10000);
				if(otp<1000)
				{
					otp+=1000;
				}
				HttpSession session = req.getSession();
				session.setAttribute("otp", otp);
				session.setAttribute("mobile", mobile);
				
				RequestDispatcher dispatcher = req.getRequestDispatcher("NewAccountOtpPage.html");
				dispatcher.include(req, resp);
				writer.println("<center><h2 style='color:green; position:relative; top:120px;'>Your OTP :"+otp+"</h2></center>");
			}
			else
			{
				RequestDispatcher dispatcher = req.getRequestDispatcher("NewAccount.html");
				dispatcher.include(req, resp);
				writer.println("<center><h2 style='color:red;'>Invalid Details...</h2></center>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
