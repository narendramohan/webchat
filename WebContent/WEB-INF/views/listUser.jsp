<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>All Users</title>
</head>
<body>
<h1><font color="#EEDD82">List Users</font></h1>
<h3><a href="add" shape="rect" style="color: buttonface;">Add More User</a></h3>

<c:if test="${!empty users}">
 <table align="left" border="1" style="border-color: black; border-width: 1pt;border-spacing: 0pt">
  <tr style="background:gray;">
   <th>User ID</th>
   <th>User Name</th>
   <th>User Password</th>
   <th>User Email</th>
   <th>User Type</th>
   <th>Status</th>
   <th>Actions</th>
  </tr>

  <c:forEach items="${users}" var="user">
   <tr style="background: teal;;">
    <td><c:out value="${user.id}"/></td>
    <td><c:out value="${user.name}"/></td>
    <td><c:out value="${user.password}"/></td>
    <td><c:out value="${user.email}"/></td>
    <td><c:if test="${user.type==1}"> Admin </c:if><c:if test="${user.type==0}"> User</c:if></td>
    <td><c:if test="${user.status!=0}"> Inactive (<a shape="rect" href="activate.html?id=${user.id}">Activate</a>)</c:if><c:if test="${user.status==0}"> Active</c:if></td>
    <td align="center"><a shape="rect" style="color: buttonface;" href="edit.html?id=${user.id}">Edit</a> | <a shape="rect" style="color: buttonface;" href="delete.html?id=${user.id}">Delete</a></td>
   </tr>
  </c:forEach>
 </table>
</c:if>
</body>
</html>
