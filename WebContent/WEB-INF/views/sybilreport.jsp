<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Sybil Attack Report</title>
</head>
<body>
<h1><font color="#EEDD82">Sybil Attack Report</font></h1>

<c:if test="${!empty attackReports}">
 <table align="left" border="1" style="border-color: black; border-width: 1pt;border-spacing: 0pt">
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
 <table align="left" border="1">
  <tr><td>No Attacks found till now.</td><tr>
 </table>
</c:if>
</body>
</html>
