<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Spring MVC Form Handling</title>
  <script type="text/javascript">
  function goBack()
  {
	  document.location.href="users";
  }
  function validateForm()
 {
	 if(document.forms[0]["name"].value.length<=0 ){
		 alert("Please enter user name");
		 return false;
	 }
	 if(document.forms[0]["loginId"].value.length<=0){
		 alert("Please enter login id");
		 return false;
	 }		
	 if(document.forms[0]["password"].value.length<=0){
		 alert("Please enter password");
		 return false;
	 }			 
	 var x=document.forms[0]["email"].value;
	 var atpos=x.indexOf("@");
	 var dotpos=x.lastIndexOf(".");
	 if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length)
	   {
	   alert("Not a valid e-mail address");
	   return false;
	   }
 }
  </script>
 </head>
 <body>
  <h2>Add User</h2>
  <hr>
  <form:form method="POST" action="save" onsubmit="return validateForm();">
      <table>
       <tr>
           <td><form:label path="id">User ID:</form:label></td>
           <td><form:input path="id" value="${user.id}" readonly="true"/></td>
       </tr>
       <tr>
           <td><form:label path="name">User Name:</form:label></td>
           <td><form:input path="name" value="${user.name}"/></td>
       </tr>
       <tr>
           <td><form:label path="loginId">Login Id:</form:label></td>
           <td><form:input path="loginId" value="${user.loginId}"/></td>
       </tr>       
       <tr>
           <td><form:label path="password">User Password:</form:label></td>
           <td><form:password path="password" value="${user.password}"/></td>
       </tr>
       <tr>
           <td><form:label path="email">User email:</form:label></td>
           <td><form:input path="email" value="${user.email}"/></td>
       </tr>
       
       <tr>
           <td><form:label path="type">User Type:</form:label></td>
                    <td><form:input path="type" value="${user.type}"/></td>
       </tr>
        <tr>
			<td colspan="2" align="left">
		<c:if test="${error!=null}">
		<div class="errorblock"><font color="red">${error}</font></div></c:if></td></tr>
          <tr>
         <td colspan="2"><input type="submit" value="Submit"/>&nbsp;<input type="button" value="Cancel" onclick="goBack()"/> </td>
        </tr>
   </table> 
  </form:form>
  
  <%-- <c:if test="${!empty users}">
  <h2>List Users</h2>
 <table align="left" border="1">
  <tr>
   <th>User ID</th>
   <th>User Name</th>
   <th>User Login id</th>
   <th>User Email</th>
   <th>User Type</th>
   <th>Actions on Row</th>
  </tr>

  <c:forEach items="${users}" var="user">
   <tr>
    <td><c:out value="${user.id}"/></td>
    <td><c:out value="${user.name}"/></td>
    <td><c:out value="${user.loginId}"/></td>
    <td><c:out value="${user.email}"/></td>
    <td><c:out value="${user.type}"/></td>
    <td align="center"><a href="edit.html?id=${user.id}">Edit</a> | <a href="delete.html?id=${user.id}">Delete</a></td>
   </tr>
  </c:forEach>
 </table>
</c:if> --%>
 </body>
</html>
