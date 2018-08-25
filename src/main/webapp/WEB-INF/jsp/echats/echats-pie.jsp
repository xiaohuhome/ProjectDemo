<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>echats-pie</title>
		<jsp:include page="/WEB-INF/jsp/common/scriptIncEs.jsp"></jsp:include>
		<script type="text/javascript" src="<c:url value="/js/common/echats/echarts.js"/>"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				// 基于准备好的dom，初始化echarts实例
		        var myChart = echarts.init(document.getElementById("echats-pie"));
		
		        // 指定图表的配置项和数据
		        var option = {
		        	//标题
		            title: {
		            	show:true,
		                text: '某网店用户访问来源',
		                subtext:'纯属虚构',
		                left:'center'
		            },
		            //弹窗组件
		            tooltip:{
		            	show:true,
		            	trigger:'item',
		            	formatter:'{a}<br/>{b} : {c}({d}%)'
		            },
		            //图例
		            legend: {
		            	orient:'vertical',
		            	left:'left',
		                data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
		            },
		            //数据
		            series: [{
		                name: '访问来源',
		                type: 'pie',
		                radius:'55%',
		                center:['50%','60%'],
		                data: [
		                	{name:'直接访问',value:335},
		                	{name:'邮件营销',value:665},
		                	{name:'视频广告',value:221},
		                	{name:'联盟广告',value:432},
		                	{name:'搜索引擎',value:1876}
		                ]	
		            }]
		        };
		
		        // 使用刚指定的配置项和数据显示图表。
		        myChart.setOption(option);
		        myChart.on('click', function (params) {
		            window.open('https://www.baidu.com/s?wd=孙允姝');
		        });
			});
		</script>
	</head>
	<body>
		<div id="echats-pie" style="width: 800px;height:400px;"></div>
	</body>
</html>