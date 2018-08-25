$(function(){
//	error();
//	excel();
//	pageLoad();
//	openVideo();
});

function pageLoad(){
	//自定义时间
	var endtime=new Date();
    $("#date").bind('click', function() {
        WdatePicker({dateFmt : 'yyyy-MM-dd',maxDate : endtime});
        fixedDataPicker();
    });
}

function error(){
	$.ajax({
		url:$WEB_ROOT_PATH + '/user/json',
		type:"POST",
		async:true,
		dataType:'json',
		success:function(data){
			var html = "<h1>姓名："+data.name+"</h1>"
			$("#div").empty().append(html);
		},
		error:function(msg){
			alert("ERROR");
		}
	});
}

function excel(){
	$.ajax({
		url:$WEB_ROOT_PATH + '/excel/excel',
		type:"POST",
		async:true,
		dataType:'json',
		success:function(data){
			console.dir(data);
		},
		error:function(msg){
			alert("ERROR");
		}
	});
}