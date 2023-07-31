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
@WebServlet("/MobileTranEnterAmount")
public class MobileTranEnterAmount extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String amount = req.getParameter("amount");
		double amountS = Double.parseDouble(amount);
		
		HttpSession session = req.getSession();
		String mobileS = (String)session.getAttribute("mobileS");
		String temp1 = mobileS.substring(0, 4);
		String temp2 = mobileS.substring(7, 9);
		String temp3 = "****";
		String mobileD = temp1+temp3+temp2;
		String mobileR = (String)session.getAttribute("mobileR");
		
		String url = "jdbc:mysql://localhost:3306/teca45?user=root&password=12345";
		String select = "select * from bank where mobilenumber=?";
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1, mobileS);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				double amount1 = rs.getDouble(5);
				String name  = rs.getString(2);
				if(amountS>0 && amountS<amount1)
				{
				double amountSUpdate = amount1-amountS;
				String amountUpdate = "update bank set amount=? where mobilenumber=?";
				PreparedStatement ps1 = connection.prepareStatement(amountUpdate);
				ps1.setDouble(1, amountSUpdate);
				ps1.setString(2, mobileS);
				int rs1 = ps1.executeUpdate();
				if(rs1!=0)
				{
					String select1 = "select * from bank where mobilenumber=?";
					PreparedStatement ps3 = connection.prepareStatement(select1);
					ps3.setString(1, mobileR);
					ResultSet rs3 = ps3.executeQuery();
					if(rs3.next())
					{
					double amount2 = rs3.getDouble(5);
					double amountRUpdate = amount2+amountS;
					String amountUUpdate = "update bank set amount=? where mobilenumber=?";
					PreparedStatement ps2 = connection.prepareStatement(amountUUpdate);
					ps2.setDouble(1, amountRUpdate);
					ps2.setString(2, mobileR);
					int rs2 = ps2.executeUpdate();
					if(rs2!=0)
					{
						RequestDispatcher dispatcher = req.getRequestDispatcher("Welcome.html");
						dispatcher.include(req, resp);
						writer.println("<center><h3 style='position:relative; top:80px; color:green;'>Amount Transferred successfully...</h3></center>");
						writer.println("<center><h3 style='position:relative; top:100px; color:green;'>Amount : "+amountS+"</h3></center>");
						writer.println("<center><h3 style='position:relative; top:105px; color:green;'>Mobile No :"+mobileD+"</h3></center>");
						writer.println("<center><h3 style='position:relative; top:110px; color:green;'>Name : "+name+"</h3></center>");
					}
					}
				}
				}
				else
				{
					RequestDispatcher dispatcher = req.getRequestDispatcher("MobileTranEnterAmount.html");
					dispatcher.include(req, resp);
					writer.println("<center><h3 style='position:relative; top:80px; color:green;'>please enter the valid amount...!</h3></center>");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
