<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>All Users</title>
</head>
<body>
<h1><font color="#EEDD82">List Nodes</font></h1>
<h3><a href="add" shape="rect" style="color: buttonface;">Add More Node</a></h3>

<c:if test="${!empty nodes}">
 <table align="left" border="1" style="border-color: teal; border-width: 1pt;border-spacing: 0pt">
  <tr style="background:gray;">
   <th>Node ID</th>
   <th>Node Name</th>
   <th>User Name</th>
   <th>Actions</th>
  </tr>

  <c:forEach items="${nodes}" var="node">
   <tr style="background: teal;">
    <td><c:out value="${node.id}"/></td>
    <td><c:out value="${node.nodeName}"/></td>
    <td><c:out value="${node.userName}"/></td>
    <td align="center"><a href="assignuser.html?id=${node.id}" shape="rect" style="color: buttonface;">Assign</a> | <a href="delete.html?id=${node.id}"  shape="rect" style="color: buttonface;">Delete Node</a></td>
   </tr>
  </c:forEach>
 </table>
</c:if>
<c:if test="${empty nodes}">
<table align="left" border="1" style="border-color: teal; border-width: 1pt;border-spacing: 0pt">
<tr style="background:gray;"> Currently no nodes are available. please register new nodes</tr>
</table>
</c:if>
</body>
</html>
