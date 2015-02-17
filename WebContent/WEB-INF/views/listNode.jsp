<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>All Nodes</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
 <section class="container">
    <div >
<font color="#EEDD82" size="5">List Nodes</font>
<a href="add" class="styled-button-11" shape="rect" style="color: buttonface;">Add More Node</a>
<br>
<br>
<c:if test="${!empty nodes}">
 <table class="rwd-table" align="left" border="1" style="border-color: teal; border-width: 1pt;border-spacing: 0pt" width="100%">
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
<table align="left" border="1" style="border-color: teal; border-width: 1pt;border-spacing: 0pt" width="100%">
<tr style="background:gray;"> Currently no nodes are available. please register new nodes</tr>
</table>
</c:if>
</div>
</section>
</body>
</html>
