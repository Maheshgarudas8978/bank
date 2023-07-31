<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
table
{
	border:2px solid silver;
	border-collapse: collapse;
	position: relative;
	top:10px;
}
td
{
	border:2px solid silver;
	border-collapse: collapse;
	padding: 10px;
	margin:10px;
}
h2
{	
	display: inline;
}
</style>
</head>
<body>
<center>
<form action="CheckBalanceJsp.jsp">
<input placeholder="Enter Your Password" name="password"><br><br>
<input type="submit" value="SUBMIT">
</form>
</center>

<%
String password = request.getParameter("password");
String url = "jdbc:mysql://localhost:3306/teca44?user=root&password=12345";
String select = "select * from bank where password=?";
String name = "";
String mobilenumber = "";
double amount=0.0 ;
try{
	Class.forName("com.mysql.jdbc.Driver");
	Connection connection = DriverManager.getConnection(url);
	PreparedStatement ps = connection.prepareStatement(select);
	ps.setString(1,password);
	ResultSet rs = ps.executeQuery();
	if(rs.next())
	{
		name = rs.getString(2);
		mobilenumber = rs.getString(3);
		amount = rs.getDouble(5);
	}
	else
	{
		
	}
}
catch(Exception e)
{
	e.printStackTrace();
}

%>
<%if(name!=""){ %>
<center>
<table>
 <tr><td><h2><%= "Name"%></h2></td>
 <td><h2><%= "MobileNo"%></h2></td>
 <td><h2><%= "Amount"%></h2></td></tr>
 <tr><td><%= name %></td>
<td><%= mobilenumber %></td>
<td><%= amount %></td></tr>
</table>
</center>
<%} %>
</body>
</html>