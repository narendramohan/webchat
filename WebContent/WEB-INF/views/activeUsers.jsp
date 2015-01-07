<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>User Home Page</title>
<style>
.error {
	color: #ff0000;
}

.errorblock{
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding:8px;
	margin:16px;
}
body
{
background-image:url('../images/ShBZl.png');
background-color:#cccccc;
}
</style>
</head>
<body>
<h1><font color="#EEDD82">Active users list</font></h1>
<%
	String userName = (String) session.getAttribute("userName");
	System.out.println(userName);
%>

<c:if test="${!empty onlineUsers}">
<table border="1" style="border-color: teal; border-width: 1pt;border-spacing: 0pt">
<tr style="background:gray;">   
   <th>User Name</th>
   <th>Ip Address</th></tr>
	<c:forEach var="user" items="${onlineUsers}">
		<tr style="background: teal;;">
			<td><font size="6" face="verdana">${user.name}</font></td>
			<td><font size="6" face="verdana">${user.ip}</font></td>
			</tr>
	</c:forEach>
</table>
</c:if>
<c:if test="${empty onlineUsers}">
<table>
<tr style="background:gray;"><td> There are no active users </td> </tr>
</table>
</c:if>
</body>
</html>