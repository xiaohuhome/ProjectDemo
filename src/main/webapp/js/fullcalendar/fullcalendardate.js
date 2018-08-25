$(function(){
	init_install_date();
});

//初始化设置的时间
function init_install_date(){
	var url = $WEB_ROOT_PATH + "/fullcalendar/initInstallDate";
	$.ajax({
		url:url,
		type:'GET',
		dataType:'json',
		success:function(data){
			var minTime = data.data.start_time;
			var maxTime = data.data.end_time;
			$("#calendar").fullCalendar("option","minTime",minTime + ":00:00");
			$("#calendar").fullCalendar("option","maxTime",maxTime + ":00:00");
			$("#minTime").val(minTime);
			$("#maxTime").val(maxTime);
		},
		error:function(){
			alert("init_install_date error!!!");
		}
		
	});
}


//设置时间
function install_ok(){
	var minTime = $( "#slider" ).slider( "values", 0 );
	var maxTime = $( "#slider" ).slider( "values", 1 );
	$("#calendar").fullCalendar("option","minTime",minTime + ":00:00");
	$("#calendar").fullCalendar("option","maxTime",maxTime + ":00:00");
	
	var params = {
		'minTime':minTime,
		'maxTime':maxTime
	}
	
	var url = $WEB_ROOT_PATH + "/fullcalendar/installOk";
	$.ajax({
		url:url,
		type:'POST',
		data:params,
		dataType:'json',
		success:function(data){
			alert(data.msg);
		},
		error:function(){
			alert("install_ok error!!!");
		}
		
	});
}