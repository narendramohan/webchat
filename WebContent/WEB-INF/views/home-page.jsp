<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>User Home Page</title>
<link href="<%=request.getContextPath()%>/css/css-menu.css" rel="stylesheet" type="text/css" />
 <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<%-- <style>
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
</style> --%>
<script type="text/javascript">

function openTab(obj, tab){
	var els = document.getElementsByTagName("a");
	for (var i = 0, l = els.length; i < l; i++) {
	    var el = els[i];
	    if (el.innerText == obj.innerText) {
	       el.className='current';
	    } else {
	    	el.className='';
	    }
	}
	if(tab=='friends'){
		 document.getElementById('framepage').src = '<%=request.getContextPath()%>/user/friends';
	} else if(tab=='onlineuser'){
		 document.getElementById('framepage').src = '<%=request.getContextPath()%>/user/onlineUsers';
	} else if(tab=='invite'){
		 document.getElementById('framepage').src = '<%=request.getContextPath()%>/user/invites';
	} else if(tab=='user'){
		 document.getElementById('framepage').src = '<%=request.getContextPath()%>/user/users';
	} else if(tab=='ActiveUsers'){
		 document.getElementById('framepage').src = '<%=request.getContextPath()%>/user/activeUser';
	} else if(tab=='registernode'){
		 document.getElementById('framepage').src = '<%=request.getContextPath()%>/node/nodes';
	} else if(tab=='report'){
		 document.getElementById('framepage').src = '<%=request.getContextPath()%>/sybilreport';
	}else if(tab=='sendfile'){
		 document.getElementById('framepage').src = '<%=request.getContextPath()%>/sendFile';
	}
}

</script>
</head>
<body>
<div align="left" style="position:absolute;top: 0px;left: 0px"><font size="5" color="#EEDD82" face="bold">Welcome to the Web chat.</font></div>

<div align="right" style="position:absolute;top: 0px;right: 0px">
	<img style="border: none;" src="<%=request.getContextPath()%>/images/user.png"/><font size="5"><%=session.getAttribute("userName") %></font> 
		<a style="text-decoration: none;" href="/webchat/logout"><font style="padding-left : 20px;" size="3" color="white">Logout</font></a>
	</div>
<%
	String userName = (String) session.getAttribute("userName");
	System.out.println(userName);
%>
<%-- <div style="background: buttonface;">
	<c:if test="${user.type==1}" >
		<a style="text-decoration: none; background: buttonface;padding-left: 10px;" href="/webchat/main/admin?user=${user.name}">Administrator</a>&nbsp;|
	</c:if>  &nbsp;<a style="text-decoration: none; background: buttonface;padding-left: 10px;" href="/webchat/struts/nodeAction">Send File</a><br/>
</div> --%>
<div class="menu4">
	<c:if test="${user.type!=1}" >
    <a href="#1" onclick="openTab(this, 'friends')" class="current"><span>Friends</span></a>
    <a href="#2" onclick="openTab(this, 'onlineuser')"><span>Available Online User</span></a>
    <a href="#3" onclick="openTab(this, 'invite')"><span>Friend Request</span></a>
    
    <a href="#4" onclick="openTab(this, 'sendfile')"><span>Send File</span></a>
    </c:if>
	<c:if test="${user.type==1}" >
	<a href="#5" onclick="openTab(this, 'user')" class="current"><span>Manage User</span></a>
    <a href="#6" onclick="openTab(this, 'ActiveUsers')"><span>Active Users</span></a>
    <a href="#7" onclick="openTab(this, 'registernode')"><span>Register Nodes</span></a>
    <a href="#8" onclick="openTab(this, 'report')"><span>Sybil Reports</span></a>
    </c:if>
</div>
<iframe id='framepage' width="100%" height="500" src="<c:if test="${user.type==1}" ><c:out value="/webchat/user/users"/></c:if><c:if test="${user.type!=1}" ><c:out value="/webchat/user/friends"/></c:if>"> </iframe>
<%-- <table border="1" style="border-color: black;border-width:1pt;border-spacing: 0px">

<c:if test="${user.type!=1}" >
<tr><td height="10px"><h3><font color="#EEDD82">Available Online User</font></h3></td>
	<td height="10px"><h3><font color="#EEDD82">Fried Request</font></h3></td></tr>
<tr><td height="300px" width="300px">
<iframe src="<%=request.getContextPath()%>/user/onlineUsers" width="100%" height="100%" style="border-width: 0"></iframe>
</td>

<td height="300px" width="300px">
<iframe src="<%=request.getContextPath()%>/user/invites" width="100%" height="100%" style="border-width: 0"></iframe>


</td>
</tr>
</c:if>
</table> --%>
</body>
</html>