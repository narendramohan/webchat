<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Friends</title>
</head>
<body>
<h1><font color="#EEDD82">Click on friend name to start chat</font></h1>
<div style="position: absolute; top:60px;left: 10px">
<c:if test="${!empty friends}">
	<table width="100%" border="1" style="border-color: black; border-width: 1pt;border-spacing: 0pt">
	<tr style="background:gray;"><th>Friend Name</th></tr>
	<c:forEach var="friend" items="${friends}">
		<c:if test="${friend.friendUserName!=userName}" >
			<tr style="background: teal;;"><td>
			<a style="text-decoration: none; padding-left: 200px;" href="/webchat/chat-page?user=${friend.friendUserName}"><img style="padding-top: 20px; border: none;" src="../images/user.png"/><font color="white" size="6" face="verdana">${friend.friendUserName}</font></a><br/>
			</td>
			</tr>
		</c:if>
	</c:forEach>
	</table>
</c:if>
<c:if test="${empty friends}">
	<table border="1" width="100%" style="border-color: black;border-width:0pt;padding:0;margin: 0px">
	<tr style="background: teal;;"><td>No friends available currently</td></tr>
	</table>
</c:if>
</div>
</body>
</html>