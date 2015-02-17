<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
  <title>webchat - Register User</title>
 <%--  <style>
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
 </style> --%>
 <script type="text/javascript">
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
  <section class="container">
    <div class="login">
     <h1>Register User to Web chat</h1>
  <form:form method="POST" action="/webchat/user/register" onsubmit="return validateForm();">
  <p><form:label path="name">User Name:</form:label></p>
           <p><form:input path="name" value="${user.name}"/></p>
  <p><form:label path="loginId">Login Id:</form:label></p>
           <p><form:input path="loginId" value="${user.loginId}"/></p> 
  <p><form:label path="password">User Password:</form:label></p>
           <p><form:password path="password" value="${user.password}"/></p>
            <p><form:label path="email">User email:</form:label></p>
           <p><form:input path="email" value="${user.email}"/></p>
           <form:hidden path="type" value="0"/>
  <c:if test="${error!=null}">
		<p><div class="errorblock"><font color="red">${error}</font></div></p></c:if>
		
<p><input type="submit" value="Register"/>&nbsp;<a href="<%=request.getContextPath()%>/auth/login-user" shape="rect" >Login</a><p>
 <%--      <table border="1" style="border-color: teal; border-width: 1pt;border-spacing: 0pt">
       <tr style="background: teal;">
           <td><form:label path="name">User Name:</form:label></td>
           <td><form:input path="name" value="${user.name}"/></td>
       </tr>
       <tr style="background: teal;">
           <td><form:label path="loginId">Login Id:</form:label></td>
           <td><form:input path="loginId" value="${user.loginId}"/></td>
       </tr>       
       <tr style="background: teal;">
           <td><form:label path="password">User Password:</form:label></td>
           <td><form:password path="password" value="${user.password}"/></td>
       </tr>
       <tr style="background: teal;">
           <td><form:label path="email">User email:</form:label></td>
           <td><form:input path="email" value="${user.email}"/></td>
           <form:hidden path="type" value="0"/>
       </tr>
       <tr style="background: teal;">
			<td colspan="2" align="left">
		<c:if test="${error!=null}">
		<div class="errorblock"><font color="red">${error}</font></div></c:if></td></tr>
          <tr>
         <td colspan="2" style="background: teal;"><input type="submit" value="Register"/>&nbsp;<a href="<%=request.getContextPath()%>/auth/login-user" shape="rect" style="color:buttonface;">Login</a></td>
        </tr>
   </table>  --%>
  </form:form>
     </div>
  </section> 
 </body>
</html>
