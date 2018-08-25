<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/scriptIncEs.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/styleIncEs.jsp"></jsp:include>
		<script type="text/javascript" src="<c:url value='/js/test/test.js'/>"></script>
		<title>Insert title here</title>
		<script type="text/javascript">
			function date(){
				var url = $WEB_ROOT_PATH + "/user/date";
				window.open(url,"_blank");
			}
		</script>
	</head>
	<body>
		<!-- <button id="btn" onclick="date()">查看日历</button> -->
		<!-- 展开收起 -->
		<%-- <jsp:include page="/WEB-INF/jsp/utils/openAndClose.jsp"></jsp:include> --%>
		<!-- 拖拽调换位置 -->
		<jsp:include page="/WEB-INF/jsp/utils/divMove.jsp"></jsp:include>
	<!-- <h1>Hello JSP!!</h1> -->
	<!-- <div id="div"></div> -->
	<%-- 日期：<input type="text" id="date" readonly="readonly" value="<fmt:formatDate value="${datess}" pattern="yyyy-MM-dd"/>"/> --%>
	<%-- 
	<!-- 录音录像 -->
	<div class="bl fl ml10" style="width:500px;">
		<jsp:include page="/WEB-INF/jsp/upload/videoAndAudio.jsp">
			<jsp:param value="1234567" name="uuid"/>
			<jsp:param value="ZP" name="type"/>
		</jsp:include>
	</div> --%>
	<%-- 
	<!-- 拍照上传 -->
	<div class="bl fl ml10" style="width:500px;">
		<jsp:include page="/WEB-INF/jsp/upload/auploadPhoto.jsp">
			<jsp:param value="1234567" name="uuid"/>
			<jsp:param value="ZP" name="type"/>
		</jsp:include>
	</div> --%> 
	<%-- 
	<!-- 本地文件上传 -->
	<div class="bl fl ml10" style="width:500px;">
		<jsp:include page="/WEB-INF/jsp/upload/aupload.jsp">
			<jsp:param value="1234567" name="uuid"/>
			<jsp:param value="SF" name="type"/>
		</jsp:include>
	</div> --%>
	<%-- <div class="bu fl ml30">
		<jsp:include page="/WEB-INF/jsp/echats/echats-bar-line.jsp"></jsp:include>
	</div>
	<div class="bu fl ml30">
		<jsp:include page="/WEB-INF/jsp/echats/echats-pie.jsp"></jsp:include>
	</div>
	<div class="bu fl ml30">
		<jsp:include page="/WEB-INF/jsp/echats/echats-scatter.jsp"></jsp:include>
	</div>
	<div class="bu fl ml30">
		<jsp:include page="/WEB-INF/jsp/echats/echats-kline.jsp"></jsp:include>
	</div>
	<div class="bu fl ml30">
		<jsp:include page="/WEB-INF/jsp/echats/echats-radar.jsp"></jsp:include>
	</div>
	<div class="bu fl ml30">
		<jsp:include page="/WEB-INF/jsp/echats/echats-map.jsp"></jsp:include>
	</div> --%>
	
	<%-- <div class="bu">
		<jsp:include page="/WEB-INF/jsp/iframe/iframe.jsp"></jsp:include>
	</div> --%>
	</body>
</html>