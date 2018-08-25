$WEB_ROOT_PATH = getRootPath();


function getRootPath(){
	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath=window.document.location.href;
	//获取主机地址之后的目录，如： /uimcardprj/share/meun.jsp
	var pathName=window.document.location.pathname;
	var pos=curWwwPath.indexOf(pathName);
	//获取主机地址，如： http://localhost:8083
	var localhostPaht=curWwwPath.substring(0,pos);
	//获取带"/"的项目名，如：/uimcardprj
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	return(localhostPaht+projectName);
}



function JO(objId) {
	return document.getElementById(objId);
}

function JV(id) {
	return JO(id).value;
}

//日期格式化
Date.prototype.format = function(format){ 
	var o = { 
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(), //day 
		"H+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds() //millisecond 
	} ;

	if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 

	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
	} 
	
	return format; 
};

var imgSrc;

// 放大图片
function biggerImg(img) {
	directionFlg = 0;
	$("#biggerImg").children().remove();
	$("#biggerImg").html("<img id='Imgbox' class='dragAble' src='" + $WEB_ROOT_PATH + "/image/doctor_def.png'  />");

	// 显示该图片的原始图片
	var img1 = document.getElementById("Imgbox");
	img1.src = img.src;
	
	JO("bigImg").style.display = "block";
	
	imgSrc = img.src;
	
	var im = document.createElement('img');
    im.src = img.src;
    var real_height = im.height;
    var real_width = im.width;
    if((real_height / real_width) > (510 / 800)){
   	 	img1.style.height = "510px";
   	 	var realWid = real_width * (510 / real_height);
   		img1.style.width = realWid + "px";
   		img1.style.left  =  ((800 - realWid) / 2)+"px";
   		
    } else {
    	img1.style.width = "800px";
    	var realHei = real_height * (800 / real_width);
   		img1.style.height = realHei + "px";
   		img1.style.top  =  ((510 - realHei) / 2)+"px";
    }
	
//	img1.style.height = "510px";
//	img1.style.width = "800px";
	setSupNextTitle();
	
//	img1.width("800");
//	img1.height("510");
  
	
	//设置按钮的div半透明
	$("#bottonDiv").css("background","#414040");
	$("#bottonDiv").css("opacity","0.3");

	//开启滚轮缩放效果
	var myimage = document.getElementById("bigImg");
	
	if (myimage.addEventListener) {
		// IE9, Chrome, Safari, Opera
		myimage.addEventListener("mousewheel", MouseWheelHandler, false);
		// Firefox
		myimage.addEventListener("DOMMouseScroll", MouseWheelHandler, false);
	}
	// IE 6/7/8
	else {
		myimage.attachEvent("onmousewheel", MouseWheelHandler);
	}

	document.documentElement.style.overflow = 'hidden';// 页面禁用滚轮
}

// 上一张图片
function supImg() {
	directionFlg = 0;
	var imgArray = $(".img");
	for (var i = 1; i < imgArray.length; i++) {
		if (imgSrc==imgArray[i].src) {
			$("#biggerImg").children().remove();
			biggerImg(imgArray[i-1]);
			//JO("Imgbox").src = imgArray[i-1].src;
//			$("#biggerImg").html("<img id='Imgbox' src='" + imgArray[i-1].src + "' class='dragAble' style='height:530px;width: 915px;'/>");
//			$("#biggerImg").html("<img id='Imgbox' class='dragAble' src='" + imgArray[i-1].src + "'");
			imgSrc = imgArray[i-1].src;
			break;
		}
	}	
	
	setSupNextTitle();
}

// 下一张图片
function nextImg() {
	directionFlg = 0;
	var imgArray = $(".img");

	for (var i = 0; i < imgArray.length - 1; i++) {
		if (imgSrc==imgArray[i].src) {
			$("#biggerImg").children().remove();
			biggerImg(imgArray[i+1]);
			//JO("Imgbox").src = imgArray[i+1].src;
//			$("#biggerImg").html("<img id='Imgbox' src='" + imgArray[i+1].src + "' class='dragAble' style='height:530px;width: 915px;'/>");
//			$("#biggerImg").html("<img id='Imgbox' class='dragAble' src='" + imgArray[i+1].src + "'");
			
			imgSrc = imgArray[i+1].src;
			break;
		}
	}	
	
	setSupNextTitle();
}

function setSupNextTitle() {
	var imgArray = $(".img");
	var img1 = JO("Imgbox");
	
	JO("imgSup").title = "";
	JO("imgNext").title = "";
	
	if (img1.src == imgArray[0].src) {
		JO("imgSup").title = "已是第一张图片";
	}
	
	if (img1.src == imgArray[imgArray.length-1].src) {
		JO("imgNext").title = "已是最后一张图片";
	}	
}

//滚轮事件
function MouseWheelHandler(e) {
	// cross-browser wheel delta
	var e = window.event || e; // old IE support
	var delta = Math.max(-1, Math.min(1,
			(e.wheelDelta || -e.detail)));
	if(delta == 1){
		ImageChange(true);
	} else {
		ImageChange(false);
	}
	return false;
}
// 关闭大图
function closeImg() {
	JO("bigImg").style.display = "none";
	document.documentElement.style.overflow='';//页面开启滚轮
}



//获得音频播放器
function getAudioHtml(audioUrl) {
	var audioHtml = "";

	if (typeof (Worker) != "undefined" || window.applicationCache) {
		audioHtml += '<audio src="' + audioUrl + '" controls="controls" preload="auto">您的浏览器不支持语音消息</audio>';
	} 
	else if (navigator.userAgent.toLowerCase().indexOf('trident') > -1) { // ie
		audioHtml += '<EMBED src="' + audioUrl + '" width="70" height="70" type="audio/mpeg" autostart="false" ShowPositionControls="false" EnableContextMenu="false"> ';
	} 
	else {
		audioHtml += '<audio src="' + audioUrl + '" controls="controls" preload="auto">您的浏览器不支持语音文件</audio>';
	}

	return audioHtml;
}

// 获得视频播放器
function getVideoHtml(audioUrl) {
	return '<video src="' + audioUrl + '" controls="controls" preload="auto" width="570px" height="450px;">您的浏览器不支持视频文件</video>';
}	

// 获得要上传附件的大小
function getUploadFileSize(fileElementId) {
	var fileName = $('#' + fileElementId).val();
	var fs = 0;
	
	// IE浏览器
	if (window.ActiveXObject) {  
		var img = document.createElement("img");
    img.dynsrc= fileName;
    fs = img.fileSize;
  } 
	else {  
    fs = $('#' + fileElementId)[0].files[0].size;  
  } 
	
	return fs;
}

//打开加载提示窗口
function openLoadWin() {
	var div = "<div id='o'><div class='loaddiv'></div><img class='loadimg' src='"+$WEB_ROOT_PATH+"/image/loading.gif'/></div>";
	$("body").append(div);
	$(".loaddiv").height($(document).height()+60);
}

// 关闭加载提示窗口
function closeLoadWin() {
	if ($("#o").length > 0) {
		$("#o").remove();
	}	
}

// 打开弹出窗口

function openWindow(winId, title, frmId) {
	$("#" + winId).show();
	
	if (title) {
		$CommonUI.getWindow("#" + winId).window("setTitle", title);
	}
	
	$CommonUI.getWindow("#" + winId).window("center");
	$CommonUI.getWindow("#" + winId).window("open");
	
	if (frmId) {
		$CommonUI.getForm('#' + frmId).form('clear');
	}
	
	$(".window-mask").height($(document).height());	
} 

//打开弹出窗口
function openWindow1(winId) { 
	if ($("#"+winId).html().trim()=='') {
		var h = $("#"+winId).css("height");
		var top = 0;
		
		if (h != '0px') {
			top = parseInt(h.replace("px", ""))/2 - 20 + "px";
		}

		$("#"+winId).html('<div style="text-align: center; padding-top:'+top+'">加载中，请稍候...</div>');		
	}
	
	$UI.openWindow(winId, null, false);
}

// 关闭弹出窗口
function closeWindow(winId, frmId, clearContent) {

	if ($("#_outer_" + winId).length > 0) {
		$UI.closeWindow(winId, null, clearContent);
	}
	else if ($("#" + winId + " .resizeL").length > 0) {
		$("#" + winId).css("display","none");
		$("#dialog_background_layer_"+winId).remove();
	}	
	else {
		if ($("#" + winId)[0].style.display != 'none') {
			$CommonUI.getWindow("#" + winId).window("close");
		}
	
		if (frmId) {
			$CommonUI.getForm('#' + frmId).form('clear');
		}			
	}
}


function createElement(tagName) {
	return document.createElement(tagName);
}

//将某button置为不可用
function disabledButton(obj, text) {
	obj.disabled = true;
	obj.style.color = "#FFEAB9";
	
	if(text != null && text != '') {
		if (obj.value) {
			obj.value = text;
		} else {
			obj.title = text;
		}
		
	}
}