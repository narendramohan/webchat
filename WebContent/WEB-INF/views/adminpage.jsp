<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.chat.application.domain.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath()%>/css/css-menu.css" rel="stylesheet" type="text/css" />
<title>Admin Page</title>
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

<script type="text/javascript">
function openTab(tab){
	if(tab=='user'){
		 document.getElementById('framepage').src = '/webchat/user/users';
	} else if(tab=='ActiveUsers'){
		 document.getElementById('framepage').src = '/webchat/user/activeUser';
	} else if(tab=='registernode'){
		 document.getElementById('framepage').src = '/webchat/node/nodes';
	} else if(tab=='report'){
		 document.getElementById('framepage').src = '/webchat/sybilreport';
	}
}
</script>
</head>
<body>
<h1><font color="#EEDD82">Admin Page</font></h1>
<div align="right" style="position:absolute;top: 0px;right: 0px">
	<img style="border: none;" src="<%=request.getContextPath()%>/images/user.png"/><font size="5"><%=session.getAttribute("userName") %></font> 
		<a style="text-decoration: none;" href="<%=request.getContextPath()%>/logout"><font style="padding-left : 20px;" size="3" color="white">Logout</font></a>
	</div>
<h6><font color="#EEDD82">Only administrator have access to this page.</font></h6>
<hr/>
<%
	User user = (User)request.getAttribute("user");
%>
<c:if test="${user.type==1}">
<div class="menu4">
    <a href="#1" onclick="openTab('user')" class="current"><span>Manage User</span></a>
    <a href="#2" onclick="openTab('ActiveUsers')"><span>Active Users</span></a>
    <a href="#2" onclick="openTab('registernode')"><span>Register Nodes</span></a>
    <a href="#3" onclick="openTab('report')"><span>Sybil Reports</span></a>

</div>
<div class="menu4sub">

 </div>
 <iframe id='framepage' width="100%" height="400" src="/webchat/user/users"> </iframe>
 </c:if>
</body>
</html>