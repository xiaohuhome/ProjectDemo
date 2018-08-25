<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8" name="viewport" content="width=device-width,initial-scale=1.0">
		<title>日历</title>
		<jsp:include page="/WEB-INF/jsp/common/scriptIncEs.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/styleIncEs.jsp"></jsp:include>
		<link href="<c:url value="/js/fullcalendar/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
		<link href="<c:url value="/js/fullcalendar/fullcalendar/fullcalendar.min.css"/>" rel="stylesheet">
		<link href="<c:url value="/js/fullcalendar/fullcalendar/fullcalendar.print.min.css"/>" rel="stylesheet" media="print">
		<link href="<c:url value="/js/fullcalendar/jquery-ui-1.10.4.custom/css/base/jquery-ui-1.10.4.custom.min.css"/>" rel="stylesheet">
		<link href="<c:url value="/js/fullcalendar/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css"/>" rel="stylesheet">
		<link href="<c:url value="/js/fullcalendar/fullcalendar/lib/cupertino/jquery-ui.min.css"/>" rel="stylesheet">
		<link href="<c:url value="/js/fullcalendar/artDialog/css/ui-dialog.css"/>" rel="stylesheet">
		<link href="<c:url value="/js/fullcalendar/wickedpicker/dist/wickedpicker.min.css"/>" rel="stylesheet">
		<script src="<c:url value="/js/fullcalendar/jquery-ui-1.10.4.custom/js/jquery-1.10.2.js"/>"></script>
		<script src="<c:url value="/js/fullcalendar/fullcalendar/lib/jquery.min.js"/>"></script>
		<script src="<c:url value="/js/fullcalendar/fullcalendar/lib/jquery-ui.min.js"/>"></script>
		<script src="<c:url value="/js/fullcalendar/fullcalendar/lib/moment.min.js"/>"></script>
		<script src="<c:url value="/js/fullcalendar/fullcalendar/fullcalendar.js"/>"></script>
		<script src="<c:url value="/js/fullcalendar/jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.min.js"/>"></script>
		<script src="<c:url value="/js/fullcalendar/artDialog/dist/dialog-min.js"/>"></script>
		<script src="<c:url value="/js/fullcalendar/bootstrap/js/bootstrap.min.js"/>"></script>
		<script src="<c:url value="/js/fullcalendar/wickedpicker/dist/wickedpicker.min.js"/>"></script>
		<script src="<c:url value="/js/fullcalendar/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"/>"></script>
		<script src="<c:url value="/js/fullcalendar/bootstrap-datepicker/dist/locales/bootstrap-datepicker.zh-CN.min.js"/>"></script>
		<style>
			body{
				margin:40px 10px;
				padding:0;
				background:#ffddff;
				font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
				font-size: 14px;
			}
			#calendar{
				width:900px;
				margin:0 auto;
			}
			.taxt{
				width:440px;
			}
			.time{
				width:100px;
			}
			.sear{
				width:85px;
			}
			.select{
				width:150px;
			}
			.textarea{
				width:300px;
			}
			.check{
				padding:8px;
			}
			.slidertext{
				padding-top:40px;
			}
			.timepicki{
				position:relative;
				z-index: 999;
			}
			#edit{
				position:relative;
				width:300px;
			}
			#edittitle{
				padding-bottom:10px;
				font-weight: bold;
				font-size: 18px;
			}
			#edittype{
				position:absolute;
				top:5px;
				right:5px;
				width:80px;
			}
		</style>
		<script type="text/javascript" src="<c:url value="/js/fullcalendar/fullcalendar.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/fullcalendar/fullcalendardate.js"/>"></script>
	</head>
	<body>
		<div id="calendar"></div>
		<div id="set" style="display:none" class="taxt">
			<div id="slider"></div>
			<div style="text-align: center" class="slidertext">
				<input type="text" id="amount" style="border:0; color:#f6931f; font-weight:bold; text-align: center; font-size: 16px;">
			</div>
		</div>
		<div id="edit" style="display: none">
			<p id="edittitle"></p>
			<select id="edittype">
				<option value="">未进行</option>
				<option value="">进行中</option>
				<option value="">已完成</option>
				<option value="">已超时</option>
			</select>
			<p id="edittime"></p>
		</div>
		<div id="dialog-form" style="display:none">
			<form class="form-inline">
				<p>
					<label>事务标题：</label>
					<input type="text" class="form-control">
				</p>
				<p>
					<label>事务内容：</label>
					<textarea class="textarea" rows="4" placeholder="内容" id="titledetail"></textarea>
					<span>（必填）</span>
				</p>
				<p>
					<label>事务类型：</label>
					<select class="select">
						<option>工作事务</option>
						<option>个人事务</option>
					</select>
				</p>
				<p>
					<label>开始时间：</label>
					<input type="text" class="time datepicker" id="startdate">
					<input type="text" class="time timepicki" id="starttime">
				</p>
				<p style="display:none" id="enddate">
					<label>结束时间：</label>
					<input type="text" class="time datepicker" id="stopdate">
					<input type="text" class="time timepicki" id="endtime">
				</p>
				<p class="checkbox check">
					<label class="checkbox-inline"><input type="checkbox" id="isallday">全天</label>
					<label class="checkbox-inline"><input type="checkbox" id="end">结束时间</label>
					<label class="checkbox-inline"><input type="checkbox" id="repeat">重复</label>
				</p>
				<p id="repeattype" style="display: none">
					<label>重复类型：</label>
					<select class="select" id="repeatselect">
						<option value="1">按天重复</option>
						<option value="2">按周重复</option>
						<option value="3">按月重复</option>
						<option value="4">按年重复</option>
						<option value="5">按工作日重复</option>
					</select>
				</p>
				<p id="repeattime" style="display: none">
					<label>重复时间：</label>
					<select class="time" id="repeatmonth" style="display:none">
						<option value="">1月</option>
						<option value="">2月</option>
						<option value="">3月</option>
						<option value="">4月</option>
						<option value="">5月</option>
						<option value="">6月</option>
						<option value="">7月</option>
						<option value="">8月</option>
						<option value="">9月</option>
						<option value="">10月</option>
						<option value="">11月</option>
						<option value="">12月</option>
					</select>
					<select class="time" id="repeatday" style="display:none">
						<option value="">1日</option>
						<option value="">2日</option>
						<option value="">3日</option>
						<option value="">4日</option>
						<option value="">5日</option>
						<option value="">6日</option>
						<option value="">7日</option>
						<option value="">8日</option>
						<option value="">9日</option>
						<option value="">10日</option>
						<option value="">11日</option>
						<option value="">12日</option>
						<option value="">13日</option>
						<option value="">14日</option>
						<option value="">15日</option>
						<option value="">16日</option>
						<option value="">17日</option>
						<option value="">18日</option>
						<option value="">19日</option>
						<option value="">20日</option>
						<option value="">21日</option>
						<option value="">22日</option>
						<option value="">23日</option>
						<option value="">24日</option>
						<option value="">25日</option>
						<option value="">26日</option>
						<option value="">27日</option>
						<option value="">28日</option>
						<option value="">29日</option>
						<option value="">30日</option>
						<option value="">31日</option>
					</select>
					<select class="time" id="repeatweek" style="display:none">
						<option value="">星期一</option>
						<option value="">星期二</option>
						<option value="">星期三</option>
						<option value="">星期四</option>
						<option value="">星期五</option>
						<option value="">星期六</option>
						<option value="">星期日</option>
					</select>
					<input type="text" class="time timepicki" id="repeatclock">
				</p>
				<p>
					<label>&nbsp;&nbsp;&nbsp;参与者：</label>
					<textarea rows="3"></textarea>
				</p>
				<p></p>
			</form>
		</div>
		
		<div id="search" style="display:none">
			<form class="form-inline">
				<p>
					<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：</label>
					<input type="text" class="sear datepicker">
					<span>至</span>
					<input type="text" class="sear datepicker">
				</p>
				<p>
					<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;状态：</label>
					<select>
						<option value="">未进行</option>
						<option value="">进行中</option>
						<option value="">已完成</option>
						<option value="">已超时</option>
					</select>
				</p>
				<p>
					<label>事务类型：</label>
					<select>
						<option value="">工作事务</option>
						<option value="">个人事务</option>
					</select>
				</p>
				<p>
					<label>事务内容：</label>
					<input type="text">
				</p>
			</form>
		</div>
		<div class="none">
			minTime:<input id="minTime" type="text"/>
			maxTime:<input id="maxTime" type="text"/>
		</div>
	</body>
</html>