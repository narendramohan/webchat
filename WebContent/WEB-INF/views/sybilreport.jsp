<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Sybil Attack Report</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<section class="container1"> 
<div>
<h1><font color="#EEDD82" size="5">Sybil Attack Report</font></h1>
<br>
<c:if test="${!empty attackReports}">
 <table class="rwd-table" align="left" border="1" width="100%" style="border-color: black; border-width: 1pt;border-spacing: 0pt">
  <tr style="background:gray;">
		<th>User Name</th>
		<th>IP Address</th>
		<th>Date Time</th>
		<th>Source Node</th>
		<th>Destination Node</th><!-- User,IP,Date,Time,Source Node,Destination Node<th>User</th> -->
	</tr>

  <c:forEach items="${attackReports}" var="report">
   <tr style="background: teal;;">
    <td><c:out value="${report.user}"/></td>
    <td><c:out value="${report.ip}"/></td>
    <td><c:out value="${report.date}"/></td> 
    <td><c:out value="${report.sourceNode}"/></td>
    <td><c:out value="${report.destinationNode}"/></td>
   </tr>
  </c:forEach>
 </table>
</c:if>
<c:if test="${empty attackReports}">
 <table class="rwd-table" align="left" border="1" width="100%">
  <tr><td>No Attacks found till now.</td><tr>
 </table>
</c:if>
</div>
</section>
</body>
</html>
