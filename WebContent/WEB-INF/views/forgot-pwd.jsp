<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot Password</title>
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
<body>
<form:form name="loginForm" commandName="loginForm" action="forgot-pwd"
	method="POST">
	<form:errors path="*" cssClass="errorblock" element="div"/>
	<h2 align="left"><font color="#EEDD82">Web chat</font></h2>
	<br clear="all" />
	<br clear="all" />
	
	<table align="right" cellpadding="5" cellspacing="5" border="1" style="border-color: teal; border-width: 1pt;border-spacing: 0pt">
		<tr style="background: teal;">
			<td colspan="2"><h2 align="left"><font color="#EEDD82">Forgot Password</font></h2></td>
			
		</tr>
		<tr style="background: teal;">
			<td colspan="2">Please enter your registered Email Id:</td>			
		</tr>
		<tr style="background: teal;">
			<td><form:input path="emailId" /></td>
			<td align="center"><input type="submit"
				value="Send Password" /></td>
		</tr>
		<tr style="background: teal;">
			<td colspan="2" align="left"><a href="login-user" shape="rect" style="color:buttonface;">Login</a></td>
		</tr>
		
	</table>
	<input type="hidden" name="submit" value="submit"/>
</form:form>
</body>
</html>