<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Add Node Webchat</title>
     <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
  <script type="text/javascript">
  function goBack()
  {
	  document.location.href="nodes";
  }
  </script>
 </head>
 <body>
  <section class="container">
    <div >
  <p>Add Node</p>
  <hr>
  <form:form method="POST" action="save">
      <table>
       <tr>
           <td><form:label path="id">Node ID:</form:label></td>
           <td><form:input path="id" value="${node.id}" readonly="true"/></td>
       </tr>
       <tr>
           <td><form:label path="nodeName">Node Name:</form:label></td>
           <td><form:input path="nodeName" value="${node.nodeName}"/></td>
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
  </div>
  </section>
 </body>
</html>
