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
		        var myChart = echarts.init(document.getElementById("echats-radar"));
		
		     // 指定图表的配置项和数据
		        option = {
		            title: {
		                text: '基础雷达图'
		            },
		            tooltip: {},
		            legend: {
		                data: ['预算分配（Allocated Budget）', '实际开销（Actual Spending）']
		            },
		            radar: {
		                // shape: 'circle',
		                indicator: [
		                   { name: '销售（sales）', max: 6500},
		                   { name: '管理（Administration）', max: 16000},
		                   { name: '信息技术（Information Techology）', max: 30000},
		                   { name: '客服（Customer Support）', max: 38000},
		                   { name: '研发（Development）', max: 52000},
		                   { name: '市场（Marketing）', max: 25000}
		                ]
		            },
		            series: [{
		                name: '预算 vs 开销（Budget vs spending）',
		                type: 'radar',
		                // areaStyle: {normal: {}},
		                data : [
		                    {
		                        value : [4300, 10000, 28000, 35000, 50000, 19000],
		                        name : '预算分配（Allocated Budget）'
		                    },
		                     {
		                        value : [5000, 14000, 28000, 31000, 42000, 21000],
		                        name : '实际开销（Actual Spending）'
		                    }
		                ]
		            }]
		        };
		
		        // 使用刚指定的配置项和数据显示图表。
		        myChart.setOption(option);
			});
		</script>
	</head>
	<body>
		<div id="echats-radar" style="width: 800px;height:400px;"></div>
	</body>
</html>