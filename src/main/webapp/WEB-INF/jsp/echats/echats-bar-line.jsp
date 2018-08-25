<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>echats-bar-line</title>
		<jsp:include page="/WEB-INF/jsp/common/scriptIncEs.jsp"></jsp:include>
		<script type="text/javascript" src="<c:url value="/js/common/echats/echarts.js"/>"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				// 基于准备好的dom，初始化echarts实例
		        var myChart = echarts.init(document.getElementById("echats"));
		
		        // 指定图表的配置项和数据
		        var option = {
		        	//标题
		            title: {
		            	show:true,
		                text: 'ECharts 入门示例',
		                link:'http://www.baidu.com/',
		                target:'self',
		                subtext:'学习echarts就来慕课网',
		                sublink:'http://www.taobao.com/',
		                subtarget:'blank',
		                left:150,
		                borderColor:'blue',
		                borderWidth:3,
		                itemGap:20,
		                textStyle:{
		                    fontSize: 18,
		                    fontWeight: 'bolder',
		                    color: 'red'
		                },
		                subtextStyle:{
		                	color:'green'
		                }
		                
		            },
		            //工具箱
		            toolbox: {
		            	show:true,
		            	backgroundColor:'gray',
		            	borderColor:'red',
		            	borderWidth:'1',
		            	feature:{
		            		dataView : {
		            	        show : true
		            	    },
		            	    restore : {
		            	        show : true
		            	    },
		            	    dataZoom : {
		            	        show : true
		            	    },
		            		saveAsImage:{
		            			show : true
		            		},
		            		magicType:{
		            			show:true,
		            			type:['bar','line']
		            		}
		            	}
		            },
		            //弹窗组件
		            tooltip:{
		            	show:true,
		            	trigger:'axis'
		            },
		            //图例
		            legend: {
		                data:['销量']
		            },
		            //x轴
		            xAxis: {
		                data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
		            },
		            //y轴
		            yAxis: {},
		            //数据
		            series: [{
		                name: '销量',
		                type: 'bar',
		                data: [1, 5, 3, 4, 4, 6],
                        markPoint:{
                        	data : [
                        	    {type : 'max', name: '最大值'},    // 最大值
                        	    {type : 'min', name: '最小值'}     // 最小值
                        	]
		                },
		                markLine:{
		                	data : [
		                		{type : 'average', name: '平均值'}
                        	]
		                }	
		            }]
		        };
		
		        // 使用刚指定的配置项和数据显示图表。
		        myChart.setOption(option);
		        myChart.on('click', function (params) {
		            window.open('https://www.baidu.com/s?wd=孙允珠');
		        });
			});
		</script>
	</head>
	<body>
		<div id="echats" style="width: 800px;height:400px;"></div>
	</body>
</html>