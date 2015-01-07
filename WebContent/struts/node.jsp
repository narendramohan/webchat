<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>

<title>Node Frame</title>
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
background-image:url('<%=request.getContextPath()%>/images/ShBZl.png');
background-color:#cccccc;
}
</style>
<script type="text/javascript">
function goBack(){
	
	window.location.href="/webchat/auth/login-user";
}

</script>
</head>
<body>
<s:form action="/struts/sendFileAction" namespace="/" method="POST" enctype="multipart/form-data">

<tr><td>Node Frame</td></tr>
<tr><td>
	<table>
		<tr><td colspan="2">
			<s:file name="fileUpload" label="Select a File to upload" size="40" />
						</td>
			</tr>
		<tr><td><s:textfield name="sourceName" label="Source Name"/>	</td>
			<td><s:submit value="Send" name="submit"/></td></tr>
		<tr><td><s:textfield name="destinationName" label="Destination Name"/>	</td>
			&nbsp;</td><td><input type="button" value="Back" onclick="goBack()"/></td></tr>
			
	</table>
 </td></tr>
<tr><td><%-- <table>
	<tr><td >
			Data details</td>
			</tr>
		<tr><td><s:textarea name="dataDetails"/>	</td>
			<td><s:submit value="Recieve data" name="submit"  action="recieveFile" /></td></tr>
	</table> --%></td></tr>

</s:form>
</body>
</html>