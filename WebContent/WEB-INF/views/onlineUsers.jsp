<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Friend Request</title>
<script type="text/javascript">
function sendInvite(userName){
	var url = "/webchat/user/invite?user="+userName;
	window.showModalDialog(url, "_self", "dialogHeight:340px; dialogWidth:440px");
}
</script>
</head>
<body>
<h1><font color="#EEDD82">All users currently online</font></h1>
<div style="position: absolute; top:60px;left: 10px">
<c:if test="${!empty onlineUsers}">
	<table border="1" style="border-color: black; border-width: 1pt;border-spacing: 0pt" width="100%">
	<tr style="background:gray;"><th>User Name</th><th>Invite</th></tr>
	<c:forEach var="user" items="${onlineUsers}">
		
		<c:if test="${user.name!=userName}" >
			<tr style="background: teal;;"><td>
				<font color="white" size="6" face="verdana">${user.name}</a>
			</td>
			<td><a href="#a" onclick="javacript:sendInvite('${user.name}')"><font color="white" size="6" face="verdana">Invite</font></a></td>
			</tr>
		</c:if>
		
	</c:forEach>
	</table>
</c:if>
<c:if test="${empty onlineUsers}">
	<table border="1" width="100%" style="border-color: black;border-width:0pt;padding:0;margin: 0px">
	<tr><td>No online users available currently</td></tr>
	</table>
</c:if>
</div>
</body>
</html>