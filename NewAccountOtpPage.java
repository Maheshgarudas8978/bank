package com.jsp.bank;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/NewAccountOtpPage")
public class NewAccountOtpPage extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		int otp = (int)session.getAttribute("otp");
		
		String otpU = req.getParameter("otp");
		int otpU1 = Integer.parseInt(otpU);
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		if(otpU1==otp)
		{
			RequestDispatcher dispatcher = req.getRequestDispatcher("NewAccountPasswordPage.html");
			dispatcher.include(req, resp);
		}
		else
		{
			RequestDispatcher dispatcher = req.getRequestDispatcher("NewAccountOtpPage.html");
			dispatcher.include(req, resp);
			writer.println("<center><h2 style='color:red; position:relative;top:100px;'>Invalid OTP</h2></center>");
		}
	}
}
