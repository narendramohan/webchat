<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>All Users</title>
 <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<section class="container1">
    <div >
<font color="#EEDD82" size="5">List Users</font>
<a href="add" class="styled-button-11" shape="rect" style="color: buttonface;">Add More User</a>
<br/>
<br/>
<c:if test="${!empty users}">

 <table class="rwd-table" align="center" border="1" style="border-color: black; border-width: 1pt;border-spacing: 0pt" width="100%">
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
    </div>
  </section>
</body>
</html>
