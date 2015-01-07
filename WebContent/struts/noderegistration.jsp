<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="/struts-tags" prefix="s" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>

<title>Node Registration</title>
</head>
<body>
<s:form action="../struts/nodeRegistrationAction"> 
 	<tr><tH>Node Registration</tH></tr>
	<tr><td>
	<table>
    <tr><td>
    	<s:textfield name="noOfNode" label="Enter no of Nodes"   disabled="%{myFlag2}"/></td></tr>  <tr><td></td></tr>
    <tr><td>
    	<s:textfield name="nodeName" label="Peer Name"  disabled="%{myFlag1}" />  </td></tr><tr><td></td></tr>
    <tr><td>
    	<s:select name="sourceNode" label="Source Node" headerKey="0" headerValue="--Select--"
                      list="sourceNodeList" listKey="id" listValue="name"/></td></tr><tr><td></td></tr>
    <tr><td>
    	<s:select name="neighbourNode" label="Neighbour Node" headerKey="0" headerValue="--Select--"
                      list="sourceNodeList" listKey="id" listValue="name"/></td></tr>  <tr><td></td></tr>
    </table>
    </td><td>
	
	<table>
    <tr><td>
    	<s:submit width="20" value="Ok" name="ok" action="enableFieldAction"  disabled="%{myFlag2}"/></td></tr>  
    <tr><td>
    	<s:submit width="20" value="Submit" name="submit" action="addNodeAction" disabled="%{myFlag1}" />  </td></tr>
    <tr><td>
    	<s:submit width="20" value="Clear" name="clear" /></td></tr>
    <tr><td align="right">
    	<input type="button" width="20" value="Cancel" name="cancel" onclick="javascript:winow.clsoe();" /></td></tr>
    <tr><td>
    	<s:submit width="20" value="Connection" name="connection" action="addConnectionAction" disabled="%{myFlag1}" /></td></tr>
    </table>
    
    </td></tr>
     <tr>
   <td >
   <s:actionmessage />
   </td>
</s:form>  


</body>
</html>