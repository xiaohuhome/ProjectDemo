function Dialog(){
}

function getWindowMaxIndex(){
	var maxIndex = 982;
	$("div[id^=_outer_],div[class=telescopic]").each(function(){
		if($(this).css("display") == "none"){
			return;
		}
		var index = $(this).css("zIndex");
		if(maxIndex < index){
			maxIndex = index;
		}
	});
	
	return maxIndex;
}

function getDialogMaxIndex(){
	var maxIndex = 980;
	$("div[id^=dialog_background_layer_]").each(function(){
		if($(this).css("display") == "none"){
			return;
		}
		var index = $(this).css("zIndex");
		if(maxIndex < index){
			maxIndex = index;
		}
	});
	return maxIndex;
}

Dialog.prototype = {
	alert : function(msg, title, okFun, showCloseBut) {
		this.addbackground(0);
		this.appendAlert(msg, title, null, okFun, null, null, null,showCloseBut); 
	},
	 
	confirm : function(msg, title, okFun, okText, cancleFun, cancleText, msg1) {  
		this.addbackground(0);
		// this.appendAlert(msg, title, okText, okFun, '1', cancleText, cancleFun, false); 
		
		var div = '<div class="_outer_alert bg_color_gray2 border_r2" style="width:360px;">';  
		
		div += '<p class="pl15 pr15 clearfix">&nbsp;</p>';
		
		if (title) {
			div += '<p class="tc f16 font_black2 mt10 fb">' + title + '</p>';
		}
		
		if (msg) {
			div += '<div class="clearfix tc pl10 pr10 f16">';  
			div += msg;
			div += '</div>';			
		}
		else {
			div += '<div class="clearfix tc pl10 pr10 f16">';    
			div += '确定删除?'; 
			div += '</div>';
		}

		if (msg1) {
			div += '<div class="bl0 tc mt10" style="color:#8B9191;">';  
			div += msg1; 
			div += '</div>';			
		}
		
		div += '<div class="pt20 pb20 tc">';
		
		if (okText) {
			div += '<a class="_alert_ok btn btn_blue display_i w80 f14" href="javascript:void(0)">' + okText + '</a>';
		}
		else {
			div += '<a class="_alert_ok btn btn_blue display_i w80 border_r2 f14" href="javascript:void(0)">确定</a>';
		}
		
		if (cancleText) {
			div += '<a class="_alert_cancle btn btn_gray display_i w80 border_r2 f14 ml20" href="javascript:void(0)">' + cancleText + '</a>';
		}
		else {
			div += '<a class="_alert_cancle btn btn_gray display_i w80 border_r2 f14 ml20" href="javascript:void(0)">取消</a>';
		}
		
		div += '</div></div>';

		var o = $(div).appendTo($("body"));
		
		$("._outer_alert .close1,._outer_alert ._alert_ok,._outer_alert ._alert_cancle").click(function() {
			$(o).remove();
			
			var flag = 0;
			
			$("._outer_alert").each(function() {
				if (!$(this).is(":hidden")) {
					flag = 1;
					return false;
				}
			});
			
			if (flag == 0) {
				$("#alert_background_layer").fadeOut(200);
			}
		});
		
		if (typeof okFun === 'function') {
			o.find("._alert_ok").click(function() {
				okFun.call();
			});
		}
		
		if (typeof cancleFun === 'function') {
			o.find("._alert_cancle").click(function() {
				cancleFun.call();
			});
		}
		
		var modal_height=o.outerHeight();
		var modal_width=o.outerWidth();
		
		$("#alert_background_layer").css({"display":"block",opacity:0});
		$("#alert_background_layer").fadeTo(200, 0.5);
		o.css({"display":"block","position":"fixed","opacity":0,"z-index":996,"left":50+"%","top":90+"%","margin-left":-(modal_width/2)+"px","margin-top":-(modal_height/2)+"px"});o.fadeTo(200,1);															
	},
	
	addbackground : function (flag,id) {
		if (flag == 0 && $("#alert_background_layer").length == 0) {
			$("body").append("<div id='alert_background_layer'></div>");
			$("#alert_background_layer").css({"position":"fixed","z-index":"992","top":"0px","left":"0px","width":"100%","height":"100%","background":"#000","display":"none"});
		}
		
		if (flag == 1 && $("#dialog_background_layer_"+id).length == 0) {
			$("body").append("<div id='dialog_background_layer_"+id+"'></div>");
			var maxWindowIndex = getWindowMaxIndex();
			maxWindowIndex = parseInt(maxWindowIndex)+3;
			$("#dialog_background_layer_"+id).css({"position":"fixed","z-index":maxWindowIndex,"top":"0px","left":"0px","width":"100%","height":"100%","background":"#000","display":"none"});
		}
	},
	
	appendAlert : function (msg, title, okText, okFun, flag, cancleText, cancleFun, showCloseBut) {
		
		if (showCloseBut == undefined) {
			showCloseBut = true;
		}
		
		var div = '<div class="_outer_alert w350 bg_color_gray2 border_r2">';
		
		if(showCloseBut){
			div += '<p class="pl15 pr10 pt10 clearfix"><a class="small_img close1 cp fr"></a></p>';
		}
		else{
			div += '<p class="pl15 pr15 clearfix">&nbsp;</p>';
		}
		
		if (title) {
			div += '<p class="tc f16 font_black2 mt10 fb">' + title + '</p>';
		}
		
		div += '<div class="clearfix tc pl10 pr10 f15">';
		div += msg;
		div += '</div><div class="pt20 pb20 tc">';
		
		if (okText) {
			div += '<a class="_alert_ok btn btn_blue display_i w80 f14" href="javascript:void(0)">' + okText + '</a>';
		}
		else {
			div += '<a class="_alert_ok btn btn_blue display_i w80 border_r2 f14" href="javascript:void(0)">确定</a>';
		}
		
		if (flag == '1') {
			if (cancleText) {
				div += '<a class="_alert_cancle btn btn_gray display_i w80 border_r2 f14 ml20" href="javascript:void(0)">' + cancleText + '</a>';
			}
			else {
				div += '<a class="_alert_cancle btn btn_gray display_i w80 border_r2 f14 ml20" href="javascript:void(0)">取消</a>';
			}
		}
		
		div += '</div></div>';
		//$("body").append(div);
		
		//var o = $("#_alert_div");
		var o = $(div).appendTo($("body"));
		
		$("._outer_alert .close1,._outer_alert ._alert_ok,._outer_alert ._alert_cancle").click(function() {
			$(o).remove();
			
			var flag = 0;
			
			$("._outer_alert").each(function() {
				if (!$(this).is(":hidden")) {
					flag = 1;
					return false;
				}
			});
			
			if (flag == 0) {
				$("#alert_background_layer").fadeOut(200);
			}
		});
		
		if (typeof okFun === 'function') {
			o.find("._alert_ok").click(function() {
				okFun.call();
			});
		}
		
		if (typeof cancleFun === 'function') {
			o.find("._alert_cancle").click(function() {
				cancleFun.call();
			});
		}
		
		var modal_height=o.outerHeight();
		var modal_width=o.outerWidth();
		
		$("#alert_background_layer").css({"display":"block",opacity:0});
		$("#alert_background_layer").fadeTo(200, 0.5);
		o.css({"display":"block","position":"fixed","opacity":0,"z-index":996,"left":50+"%","top":50+"%","margin-left":-(modal_width/2)+"px","margin-top":-(modal_height/2)+"px"});o.fadeTo(200,1);													
	},
	
	openWindow : function(id, title, showCloseBut) {

		if ($("#_outer_" + id).length>0 && $("#_outer_" + id).css("display")!='none') {		
			return;
		}
		
		if (showCloseBut == undefined) {
			showCloseBut = true;
		}
		
		this.addbackground(1,id);
		var o = null;
		
		if ($("#_outer_" + id).length == 0) {
			$("#" + id).show().wrap('<div id="_outer_' + id + '" class="_outer_win_'+id+' bg_color_gray2 display_n border_r2"></div>');
			o = $("#_outer_" + id);
			
			if (title) {
				o.prepend('<p class="tc f26 font_black2">' + title + '</p>');
			}
		
			if (showCloseBut) {			
				o.prepend('<p class="pl15 pr15 pt10 clearfix"><a class="small_img close1 cp fr"></a></p>');
			}
			
			o.width($("#" + id).outerWidth(true));
		}
		else {
			o = $("#_outer_" + id);
		}
		
		var ui = this;
		o.find(".close1").click(function() {

			ui.closeWindow(id);
			/*$(o).fadeOut(200);
			$("#dialog_background_layer").fadeOut(200);*/
		});
		
		var modal_width=o.outerWidth();
		var modal_height=o.outerHeight();
		
		o.css("min-height", modal_height + 2);
		
		if (modal_height > $(window).height()) {
			modal_height = $(window).height();
		}
		$("#dialog_background_layer_"+id).css({"display":"block",opacity:0.5});
//		$("#dialog_background_layer").css({"display":"block",opacity:0});
//		$("#dialog_background_layer").fadeTo(200, 0.5);
		var maxIndexDialog = getDialogMaxIndex();
		maxIndexDialog = parseInt(maxIndexDialog)+2;
		o.css({"display":"block","position":"fixed","opacity":0,"z-index":maxIndexDialog,"left":50+"%","top":50+"%","margin-left":-(modal_width/2)+"px","margin-top":-(modal_height/2)+"px"});o.fadeTo(200,1);
	},
	
	closeWindow : function(id, closeFun, clearContent) {	
		var o = $("#_outer_" + id);
		o.hide();
		
		var flag = 0;
		
		$("._outer_win_"+id).each(function() {
			if (!$(this).is(":hidden")) {
				flag = 1;
				return false;
			}
		});
		
		if (flag == 0) {
//			$("#dialog_background_layer").fadeOut(200);
//			$("#dialog_background_layer_"+id).css("display","none");
			$("#dialog_background_layer_"+id).remove();
		}
		
		if (closeFun != undefined && (typeof closeFun === 'function')) {
			closeFun.call();
		}
		
		if (clearContent) {		
			$("#" + id).html("");
		}
	}
};

var $UI = new Dialog();