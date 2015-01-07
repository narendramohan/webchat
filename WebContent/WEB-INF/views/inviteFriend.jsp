<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Friend Invite</title>
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
function closeme(){
	window.close();
}
</script>
</head>
<body>
<h1><font color="#EEDD82">Friend Invite</font></h1>
<c:if test="${msg!=null}">
		<div class="msg"><font color="black">${msg}</font></div></c:if>
<c:if test="${error!=null}">
		<div class="errorblock"><font color="red">${error}</font></div></c:if>
<input type="button" value="Close" onclick="closeme()"/>
</body>
</html>