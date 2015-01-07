<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
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
</style>
</head>

<body>
<h2><font color="#EEDD82">Send file to friend</font></h2>

<form:form method="POST" action="uploadSuccess" commandName="fileUpload" enctype="multipart/form-data">

<form:errors path="*" cssClass="errorblock" element="div"/>
<table border="1" style="border-color: teal; border-width: 1pt;border-spacing: 0pt">
<tr style="background:gray;"><th>
Please select a file to upload :</th><td> <input type="file" name="file" />
<span><form:errors path="file" cssClass="error" /></span></td></tr>
<tr style="background:gray;"><th>Select Friend :</th><td> <form:select path="userName" > 
           			<form:options items="${users}" />			
				</form:select></td></tr>
<tr style="background:gray;"><th>Select Node :</th><td> <form:select path="nodeName" > 
           			<form:options items="${nodes}" />			
				</form:select></td></tr>				
<tr style="background:gray;"><td colspan="2" align="center"><input type="submit" value="Send File" /></td></tr>
</table>
 
</form:form>
<table>
<tr><td>
<h2><font color="#EEDD82">List of files received from Friends</font></h2>
<c:if test="${!empty filesRecieved}">

 <table align="left" border="1" style="border-color: teal; border-width: 1pt;border-spacing: 0pt">
  <tr style="background:gray;">
   <th>File Name</th>
   <th>Received from</th>
   <th>Download</th>
  </tr>

  <c:forEach items="${filesRecieved}" var="file">
   <tr style="background: teal;;">
    <td><c:out value="${file.filename}"/></td>
    <td><c:out value="${file.fromUser}"/></td>
    <td align="center"><a href="downloadRecievedFile.html?id=${file.idfilesharing}" shape="rect" style="color: buttonface;">Download</a> </td>
   </tr>
  </c:forEach>
 </table>
</c:if>
<c:if test="${empty filesRecieved}">
<table align="left" border="1" style="border-color: teal; border-width: 1pt;border-spacing: 0pt">
<tr style="background:gray;"> Currently you have not recieved any files.</tr>
</table>
</c:if>
</td></tr>
<tr><td>&nbsp;
</td></tr>
<tr><td>
<h2><font color="#EEDD82">List of files sent to Friends</font></h2>
<c:if test="${!empty filesSent}">

 <table align="left" border="1" style="border-color: teal; border-width: 1pt;border-spacing: 0pt">
  <tr style="background:gray;">
   <th>File Name</th>
   <th>Sent to</th>
   <th>Download</th>
  </tr>

  <c:forEach items="${filesSent}" var="file">
   <tr style="background: teal;;">
    <td><c:out value="${file.filename}"/></td>
    <td><c:out value="${file.toUser}"/></td>
    <td align="center"><a href="downloadFile.html?id=${file.idfilesharing}" shape="rect" style="color: buttonface;">Download</a> </td>
   </tr>
  </c:forEach>
 </table>
</c:if>
<c:if test="${empty filesSent}">
<table align="left" border="1" style="border-color: teal; border-width: 1pt;border-spacing: 0pt">
<tr style="background:gray;"> Currently you have not sent any files.</tr>
</table>
</c:if>
</td></tr>
</table>
</body>
</html>