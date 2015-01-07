<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
 
<html>
<head>
	<title>Sybil Guard</title>
	<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
	
	<META HTTP-EQUIV="refresh" CONTENT="15">
</head>
 
<body>
<table class="wwFormTable"> 
	<tr>
		<th colspan="3">Sybil Guard</th></tr>

	<tr>
		<th>User Name</th>
		<th>IP Address</th>
		<th>Date</th>
		<th>Time</th>
		<th>Source Node</th>
		<th>Destination Node</th><!-- User,IP,Date,Time,Source Node,Destination Node<th>User</th> -->
	</tr>
	<tr>
		<td>a</td><td>b</td><td>n</td>
	</tr>
<s:iterator value="nodeList" >
<tr>
 <fieldset>  
	<td><s:property value="nodeId"/></td>  
	<td><s:property value="user"/></td>  
	<%-- <td><s:property value="user"/></td>  --%>   
</fieldset>
</tr>  
</s:iterator>

</table> 
</body>
</html>