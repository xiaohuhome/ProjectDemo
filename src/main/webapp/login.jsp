<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户登录</title>
		<jsp:include page="/WEB-INF/jsp/common/scriptIncEs.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/styleIncEs.jsp"></jsp:include>
		<script type="text/javascript" src="<c:url value='/js/login/login.js'/>"></script>
	</head>
	<body>
		用户名：<input type="text" id="username"/><p id="usernameAlert"  class="login_tishi"></p>	<br><br>
		密码：<input type="password" id="password"/><p id="passwordAlert"  class="login_tishi"></p>	<br><br>
		<input type="checkbox" id="rememberMe" value="记住密码"/>记住密码<br><br>
		<button id="userLogin" onclick="userLogin()">登录</button>
	</body>
</html>