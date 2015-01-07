<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
<style>
.error {
	color: #ff0000;
}

.errorblock{
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding:8px;
	margin:16px;
}
body
{
background-image:url('../images/ShBZl.png');
background-color:#cccccc;
}
</style>
</head>
<body >

<form:form name="loginForm" commandName="loginForm" action="/webchat/auth/login-user"
	method="POST">
	<form:errors path="*" cssClass="errorblock" element="div"/>
	<h2 align="left"><font color="#EEDD82">Web chat</font></h2>
	<br clear="all" />
	<br clear="all" />
	<%
//System.out.println(request.getAttribute("test"));
int failedAttempts = request.getAttribute("failedtimes")!=null?(Integer)request.getAttribute("failedtimes"):0;
%>
	<table align="left" width="100%">
	<tr>
			<td ><div><img src="../images/people-to-people752x483.png"> </div></td>
	<td>
	<table align="right" border="1" style="border-color: teal; border-width: 1pt;border-spacing: 0pt">
		<tr style="background: teal;">
			<td colspan="2"><h2 align="left"><font color="#EEDD82">Please Login</font></h2></td>
			
		</tr>
		<tr style="background: teal;">
			<td>Login Id</td>
			<td><form:input path="userId" size="50"/></td>
			
		</tr>
		<tr style="background: teal;">
			<td>Password</td>
			<td><form:password path="password" size="50"/></td>			
		</tr>
		<%if(failedAttempts>0) {%>
		<tr>
			<td>&nbsp;</td>
			<td><img src="<c:url value="captcha"/>"></td>
		</tr>
		<tr>
			<td>Please Enter Captcha details</td>
			<td><form:password path="captcha" size="50"/></td>			
		</tr>		
		<%} %>
		<tr style="background: teal;">
		<td>&nbsp;</td>
			<td align="left"><input type="submit" value="Login" />&nbsp;|&nbsp;<a href="forgot-pwd"><font color="white">Forgot password</font></a>&nbsp;|&nbsp;<a href="../register"><font color="white">Signup</font></a></td>
		</tr>

		<tr style="background: teal;">
			<td colspan="2" align="left">
		<c:if test="${error!=null}">
		<div class="errorblock"><font color="red">${error}</font></div></c:if></td></tr>
	</table>
	</td>
	</tr>
	</table>
	<input type="hidden" name="submit" value="submit"/>
	
</form:form>
</body>
</html>