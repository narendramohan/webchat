<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<html>
<head>
<title>AJAX Driven Web Chat</title>
<script type="text/javascript" src="./js/ajax-chat.js"></script>
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
background-image:url('images/ShBZl.png');
background-color:#cccccc;
}
</style>
</head>
<body onload="return startChat()">
<h1>Welcome to Chat Zone</h1>
<%-- <div align="right" style="position:absolute;top: 0px;right: 0px">
	<img style="border: none;" src="images/user.png"/><font size="5"><%=session.getAttribute("userName") %></font> 
		<a style="text-decoration: none;" href="logout"><font style="padding-left : 20px;" size="3" color="white">Logout</font></a>
	</div> --%>
<div style="padding-left: 50px;">
<div id="chat" style="padding-left: 10px;padding-right: 10px; height: 300px;overflow: auto; background-color: #F6FFFC;">
</div>
<br />
<form onsubmit="return blockSubmit();">
	<input type="hidden" id="chatUser" value="<c:out value="${chatUser}"/>"/>
	<input type="hidden" id="user" value="<c:out value="${userName}"/>"/>
	<input type="text" id="chatMessage" name="chatMessage" style="width: 447px;" />
	<input type="button" name="btn_send_chat" id="btn_send_chat" value="Send" onclick='javascript:sendChatText();' />
	<input type="button" value="Refresh" onclick="javascript:getChatText();" /> 
	<input type="button" value="Clear" onclick="javascript:resetChat();" />
	<input type="button" value="Back" onclick="javascript:goBack();" /><br />
</form>
</div>


<!-- <div  class="container" >
	<div class="onlineUsersDIV" >
		<div class="boxTitle"><b>List of Online Users :</b></div>
		<ul class="onlineUsersUL"></ul>This will hold all the online users
	</div>
	<div class="groupChat">
		<div class="boxTitle" ><b>Group chat here:</b></div>
		<textarea rows="21" id="groupChatHistory" readonly="true" class="groupChat groupChatHistory" cols="92"></textarea><br/>
		<textarea rows="3" id="groupChatBox" class="groupChatBox" cols="92"></textarea>
	</div>
	<div class="chatRoom" style="float:right;width: 200px;"></div>	
</div>

<div class="allChatDivs">	
</div> -->
</body>
</html>