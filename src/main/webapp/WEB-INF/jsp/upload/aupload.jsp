<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
	.div_img_self{width:72px; height: 90px; position: relative; float: left; margin-bottom: 15px; margin-right: 15px;text-align:center;}
	.img_self{width: 70px;height:70px;border: 1px dashed #BDC3C7; cursor: pointer;}
	.del_img_self{position: absolute; top: -5px; right: -8px;cursor: pointer;}
</style>
<script type="text/javascript" src="<c:url value='/js/common/jquery/ajaxfileupload.js'/>"></script>
<script type="text/javascript">
	$(function(){
		var uuid = JV("uuid");
		var type = JV("type");
		$(".zb_img1").bind("click",function(){
			$("#upload_"+uuid).click();
		});
		load(uuid,type);
	});

	function load(uuid,type){
		var url = $WEB_ROOT_PATH + '/mupload/fileList?uuid='+uuid+'&type='+type;
		$.ajax({
			url:url,
			type:'GET',
			dataType:'html',
			success:function(data){
				$("#result_"+uuid).empty().append(data);
			}
		});
	}
	//上传附件
	function ajaxFileUpload(uuid, type) {
		if (uuid == '') {
			return;
		}
		
		var fileElementId = 'upload_' + uuid;
		var fileType=$("#"+fileElementId).val();
		var fileType = fileType.substring(fileType.lastIndexOf(".")).toLowerCase();
	
		if (fileType == '') {
			alert("请选择要上传的文件");
			return;
		}
		
		var uploadUrl = $WEB_ROOT_PATH + '/mupload/msave?uuid='+uuid+'&type='+type;
		$.ajaxFileUpload({
			url : uploadUrl,
			secureuri : false,
			fileElementId : fileElementId, 
			dataType : 'json', 
			success : function(data) {
				if (data.mResult.msg != '0') {
					alert(data.mResult.msg);
				}
				else {
					for (var i = 0; i < data.mResult.data.length; i++) {
						var htmlStr='';				
					 	if (data.mResult.data[i].fileType=='I') {
							htmlStr = '<div class="div_img_self" id="data_'+data.mResult.data[i].id+'">' + 
												'	 <img src="'+data.mResult.data[i].url+'"' + ' ondblclick="biggerImg(this);" class="img img_self"  title="双击查看原图"/>' +
												'  <div title="点击删除图片" class="del_img del_img_self" style="color:red;" onclick="del(\'' +data.mResult.data[i].id+'\',\''+ uuid+'\')"></div>' +	
												'</div>';					
						}         
		 				else if (data.mResult.data[i].fileType=='A') {
							htmlStr = '<div class="div_img_self" id="data_'+data.mResult.data[i].id+'">' + 
												'  <img src="<c:url value="/image/common/mp3.png"/>" ondblclick="displayMp3(\''+data.mResult.data[i].url+'\')" class="img_self" title="双击收听音频"/>' +
												'  <div class="del_img del_img_self" title="点击删除文件" onclick="del(\'' +data.mResult.data[i].id+'\',\''+ uuid+'\')"></div>' +	
												'</div>';					
						}	 
		 				else if (data.mResult.data[i].fileType=='V') {
							htmlStr = '<div class="div_img_self" id="data_'+data.mResult.data[i].id+'">' + 
												'  <img src="<c:url value="/image/common/mp4.png"/>" ondblclick="displayMp4(\''+data.mResult.data[i].url+'\')" class="img_self" title="双击观看视频"/>' +
												'  <div class="del_img del_img_self" title="点击删除文件" onclick="del(\'' +data.mResult.data[i].id+'\',\''+ uuid+'\')"></div>' +	
												'</div>';					
						}					
						else if (data.mResult.data[i].fileType=='P') {
							htmlStr = '<div class="div_img_self" id="data_'+data.mResult.data[i].id+'">' + 
												'  <a href="'+data.mResult.data[i].url+'" target="_blank"><img src=\'<c:url value="/image/common/pdf.png"/>\' class="img_self" title="点击下载文件"/></a>' +
												'  <div class="del_img del_img_self" title="点击删除文件" onclick="del(\'' +data.mResult.data[i].id+'\',\''+ uuid+'\')"></div>' +	
												'</div>';	
						}					 
	
						$('#resultData_'+uuid).append(htmlStr);	
					}
				}
				
				$("#"+fileElementId).val('');
			},
			error : function(data, status, e) {
				$('#resultData_'+uuid).empty().append(data.responseText);
				console.dir(data.responseText);
				console.dir(status);
				console.dir(e);
			}
		});
	}
	
	// 删除附件
	function del(id, uuid) {
		$UI.confirm('确定删除此文件?', null, function(){
			$.ajax({
				data:{
					"id": id
				},
				url:$WEB_ROOT_PATH + '/mupload/deleteById',
				type:'POST',
				async:false,
				dataType:'json',
				success:function(data) {
					if (data.msg =='0') {
						$('#data_'+id).remove();
					}
					else {
						alert("删除失败");
					} 
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					alert(errorThrown);
				}
			});				
		});
	}
	
	function displayMp3(url) {
		$("#divMp3").html(getAudioHtml(url));
		openWindow1("winMp3");
	}
	
	function closeMp3() {
		closeWindow('winMp3');
		$("#divMp3").html("");
	}
	
	function displayMp4(url) {	
		$("#divMp4").html(getVideoHtml(url));
		JO("aMp4").href=url;
	
		openWindow1("winMp4");
	}
	
	function closeMp4() {
		closeWindow('winMp4');
		$("#divMp4").html("");
	}
</script>
<div class="clearfix" style="width: 100%;">
	<div id="divUpLoad_${param.uuid }" form="true" method="post" style="height: 30px; width:35px;position: relative;" class="fl"> 
		<div class="zb_img1">
		</div> 
		<div class="display_n cp" form="true" method="post" enctype="multipart/form-data" style="width:100%;height:100%;top:0px;left:0px; position: absolute;" >
			<input type="file" multiple="multiple" id="upload_${param.uuid }" name="upload" onchange="ajaxFileUpload('${param.uuid }','${param.type }')" style="height: 100%;top:0px;left:0px;filter:alpha(opacity=0); cursor: pointer;"/>
		</div>
	</div>
</div>
<div id="result_${param.uuid }" style="background-color: yellow;height: 300px; width:350px;">
	
</div>

<div class="display_n">
	<input type="text" id="uuid" value="${param.uuid }"/>
	<input type="text" id="type" value="${param.type }"/>
</div>


<!-- 音频播放窗口 -->
<div id="winMp3" style="width: 330px; height: 94px; display: none; border: 0px solid blue;">
	<div class="bg_color_white p10 font_black2" style="height: 80px; border: 0px solid red; padding-bottom: 1px; background-color: #EFEFEF;">
		<div class="clearfix">
			<span class="f16 fl">音频播放</span> 
			<span class="cp small_icon close fr" onclick="closeMp3();"></span>
		</div>
		<div class="borderb_1s_grey mt5"></div>
		<div id="divMp3" class="mt15"></div>
	</div>
</div> 

<!-- 视频播放窗口 -->
<div id="winMp4" style="width: 600px; height: 520px; display: none; border: 0px solid blue;">
	<div class="bg_color_white p10 font_black2" style="height: 520px; border: 0px solid red; padding-bottom: 1px; background-color: #EFEFEF;">
		<div class="clearfix">
			<span class="f16 fl">视频播放</span> 
			<a class="fl ml10 cp" target="_blank" style="color: blue; text-decoration: underline;" id="aMp4">我的浏览器无法播放视频，我要下载</a> 
			<span class="cp small_icon close fr" onclick="closeMp4();"></span>
		</div>
		<div class="borderb_1s_grey mt5"></div>
		<div id="divMp4" class="mt15 pl5"></div>
	</div>
</div>
<jsp:include page="/WEB-INF/jsp/common/bottom.jsp"></jsp:include>