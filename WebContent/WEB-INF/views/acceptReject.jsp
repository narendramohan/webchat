<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Friend Request</title>
</head>
<body>
<h1><font color="#EEDD82">Friend's Request</font></h1>
<div style="position: absolute; top:60px;left: 10px">
<c:if test="${!empty invites}">
 <table align="left" border="1" style="border-color: teal; border-width: 1pt;border-spacing: 0pt">
  <tr style="background:gray;">
   <th>User Name</th>
   <th>Message</th>
   <th>Actions</th>
  </tr>

  <c:forEach items="${invites}" var="invite">
   <tr style="background: teal;">
    <td><c:out value="${invite.friendUserName}"/></td>
    <td>Hi, I want to be your friend. please accept.</td>
    <td align="center"><a href="accept.html?userName=${invite.friendUserName}">Accept</a> | <a href="reject.html?userName=${invite.idfriend}">Reject</a></td>
   </tr>
  </c:forEach>
 </table>
</c:if>

<c:if test="${empty invites}">
 <table align="left" border="1" style="border-color: teal; border-width: 1pt;border-spacing: 0pt">
  <tr style="background: teal;">
   <td>Currently No Invites available</td>
  </tr>
 </table>
</c:if>
</div>
</body>
</html>