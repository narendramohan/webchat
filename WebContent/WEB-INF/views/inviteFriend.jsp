<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<title>Friend Invite</title>

<script type="text/javascript">
function closeme(){
	window.close();
}
</script>
</head>
<body>
<section class="container1"> 
<div>
<h1><font color="#EEDD82" size="5">Friend Invite</font></h1>
<c:if test="${msg!=null}">
		<div class="msg"><font color="black">${msg}</font></div></c:if>
<c:if test="${error!=null}">
		<div class="errorblock"><font color="red">${error}</font></div></c:if>
<input type="button" value="Close" onclick="closeme()"/>
</div>
</section>
</body>
</html>