<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
<title>Recieve File</title>
</head>
<body>
<s:form method="POST" action="/struts/recieveFileAction">
<tr><td>
   File Name :</td><td>
			 <s:property value="fileUploadFileName"/> </td>
			</tr>
<tr><td >
   Content Type :</td><td> <s:property value="fileUploadContentType"/> </td>
			</tr> 
<%-- <tr><td >
   File : </td><td> <s:property value="fileUpload"/> </td>
			</tr>  --%>
	<tr><td >
			Data details</td>
			</tr>
		<tr><td><s:textarea name="fileContent" ><s:property value="fileContent"/></s:textarea>	</td>
			<td><s:submit value="Recieve data" name="submit" action="struts/recieveFileAction"/></td></tr>
			
	<tr><td><input type="hidden" name="fileUploadFIleName" value="<s:property value="fileUploadFileName"/>"/>
	<input type="hidden" name="filePath" value="<s:property value="filePath"/>"/>
	<input type="hidden" name="fileSize" value="<s:property value="fileSize"/>"/></td><td></td></tr>
	
	</s:form>
</body>
</html>