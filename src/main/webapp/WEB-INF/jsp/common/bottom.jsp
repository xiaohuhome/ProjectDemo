<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 原始图片显示 -->
<div id="bigImg"  class="bigImg none">
	<div class="loginbox_up clearfix pt5 pr5" >
		<span class="fr">
			<a href="javascript:void(0)" title="点击关闭大图" onclick="closeImg();"></a>
		</span> 
	</div>
	<div class="picDiv realImg clearfix"  id="biggerImg">
<%--         <img id="Imgbox"  class="dragAble" style="border:1px solid blue;" alt="头像" src='<c:url value="/image/common/sup.png"/>'/> --%>
    </div>
	<div class="bigger_outer clearfix">
		<div style="height: 535px; color: white; font-size: 18px;" class="fl w40">
			<div style="margin-top: 220px;" class="cp" onclick="supImg();">
				<img id="imgSup" src='<c:url value="/image/common/sup.png"/>' style="width: 30px; height: 30px;"/>
			</div>
		</div>
		<div style="height: 535px; color: white; font-size: 18px;" class="fr w40">
			<div style="margin-top: 220px; text-align: right;" class="cp" onclick="nextImg();">
				<img id="imgNext" src='<c:url value="/image/common/next.png"/>' style="width: 30px; height: 30px;"/>
			</div>
		</div>		
	</div>
	<div id="bottonDiv"  style="-moz-opacity:0.5; margin: 0 auto; width: 136px; padding-top: 3px;"> 
	  <a href="javascript:void(0)">
	  	<img title="放大" alt="放大" src="<c:url value='/image/b.png'/>" class="w30 h30" onclick="ImageChange(true)" />
	  </a>
	  <a href="javascript:void(0)">
	  	<img title="缩小" alt="缩小" src="<c:url value='/image/s.png'/>" class="w30 h30" onclick="ImageChange(false)"/>
	  </a>
	  <a href="javascript:void(0)">
	  	<img title="左转" alt="左转" src="<c:url value='/image/l.png'/>" class="w30 h30" onclick="RotateImgMethod('left')"/>
	  </a>
	  <a href="javascript:void(0)">
	  	<img title="右转" alt="右转" src="<c:url value='/image/r.png'/>" class="w30 h30" onclick="RotateImgMethod('right')"/>
	  </a>
	</div>	
</div>

<script type='text/javascript'>
var directionFlg = 0;
function RotateImgMethod(direction){
	if(direction == "left"){
		if(directionFlg == 0){
			if(nn6){
				$("#Imgbox").css({
					'transform' : 'rotate('+'270'+'deg)',
					'-moz-transform':'rotate('+'270'+'deg)',
					'-o-transform' : 'rotate('+'270'+'deg)'
				});
			} else {
				document.getElementById("Imgbox").style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation=3)';
			}
			directionFlg = 3;
		} else if(directionFlg == 1){
			if(nn6){
				$("#Imgbox").css({
					'transform' : 'rotate('+'0'+'deg)',
					'-moz-transform':'rotate('+'0'+'deg)',
					'-o-transform' : 'rotate('+'0'+'deg)'
				});
			} else {
				document.getElementById("Imgbox").style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation=0)';
			}
			JO("Imgbox").className = "dragAble";
			directionFlg = 0;
		} else if(directionFlg == 2){
			if(nn6){
				$("#Imgbox").css({
					'transform' : 'rotate('+'90'+'deg)',
					'-moz-transform':'rotate('+'90'+'deg)',
					'-o-transform' : 'rotate('+'90'+'deg)'
				});
			} else {
				document.getElementById("Imgbox").style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation=1)';
			}
			directionFlg = 1;
		} else if(directionFlg == 3){
			if(nn6){
				$("#Imgbox").css({
					'transform' : 'rotate('+'180'+'deg)',
					'-moz-transform':'rotate('+'180'+'deg)',
					'-o-transform' : 'rotate('+'180'+'deg)'
				});
			} else {
				document.getElementById("Imgbox").style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation=2)';
			}
			JO("Imgbox").className = "dragAble";
			directionFlg = 2;
		}
	} else if(direction == "right"){
		if(directionFlg == 0){
			if(nn6){
				$("#Imgbox").css({
					'transform' : 'rotate('+'90'+'deg)',
					'-moz-transform':'rotate('+'90'+'deg)',
					'-o-transform' : 'rotate('+'90'+'deg)'
				});
			} else {
				document.getElementById("Imgbox").style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation=1)';
			}
			JO("Imgbox").className = "dragAble";
			directionFlg = 1;
		} else if(directionFlg == 1){
			if(nn6){
				$("#Imgbox").css({
					'transform' : 'rotate('+'180'+'deg)',
					'-moz-transform':'rotate('+'180'+'deg)',
					'-o-transform' : 'rotate('+'180'+'deg)'
				});
			} else {
				document.getElementById("Imgbox").style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation=2)';
			}
			directionFlg = 2;
		} else if(directionFlg == 2){
			if(nn6){
				$("#Imgbox").css({
					'transform' : 'rotate('+'270'+'deg)',
					'-moz-transform':'rotate('+'270'+'deg)',
					'-o-transform' : 'rotate('+'270'+'deg)'
				});
			} else {
				document.getElementById("Imgbox").style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation=3)';
			}
			JO("Imgbox").className = "dragAble";
			directionFlg = 3;
		} else if(directionFlg == 3){
			if(nn6){
				$("#Imgbox").css({
					'transform' : 'rotate('+'0'+'deg)',
					'-moz-transform':'rotate('+'0'+'deg)',
					'-o-transform' : 'rotate('+'0'+'deg)'
				});
			} else {
				document.getElementById("Imgbox").style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation=0)';
			}
			JO("Imgbox").className = "dragAble ";
			
			directionFlg = 0;
		}
	}
	
}

	var size=0;
	//放大缩小图片
	function imgToSize(size) {
		var img = $("#Imgbox");
		var oWidth=img.width(); //取得图片的实际宽度
		var oHeight=img.height(); //取得图片的实际高度
		var bl = oHeight/oWidth;
		
		var rWidth=Math.max(50, Math.min(800, oWidth + size));
		var rHeight=Math.max(50*bl, Math.min(800*bl, oWidth + size*bl));
		
		img.width(rWidth);
		img.height(rHeight);
	}
	
	 //图片放大和缩小（兼容IE和火狐，谷歌）
    function ImageChange(args) {
        var oImg = document.getElementById("Imgbox");
        var wid = parseInt(oImg.style.width);
        var hei = parseInt(oImg.style.height);
        if (args) {
        	wid = wid * 1.2;
        	hei = hei * 1.2;
        }
        else {
        	wid = wid / 1.2;
        	hei = hei / 1.2;
        }
        oImg.style.width = wid + "px";
        oImg.style.height = hei + "px";
        
    	oImg.style.left  =  ((800 - wid) / 2)+"px";
    	oImg.style.top  =  ((510 - hei) / 2)+"px";
    }
	
	function confirmFunc(message, okText, okFunc, cancelText, cancelFunc, parentWinId){
		openWindow("confirmWindow", "确认信息", null);
		JO("confirmMessage").innerHTML = message;
		
		JO("okButton").onclick = function(){okFunc()};
		JO("cancelButton").onclick = function(){cancelFunc()};
		JO("closeConfirmWinA").onclick = function(){cancelFunc()};
		
		if(okText != null){
			JO("okButton").innerHTML = okText;
		}
		if(cancelText != null){
			JO("cancelButton").innerHTML = cancelText;
		}
		closeWindow(parentWinId);
	}
	
	function alertFunc(message){
		openWindow("alertWindow", "确认信息", null);
		JO("alertMessage").innerHTML = message;
	}
	
	var ie=document.all;
	var nn6=document.getElementById&&!document.all;
	var isdrag=false;
	var y,x;
	var oDragObj;
	document.onmousedown=initDrag;
	document.onmouseup=new Function("isdrag=false");

	function initDrag(e) {
	    var oDragHandle = nn6 ? e.target : event.srcElement;
	    var topElement = "HTML";
	    while (oDragHandle.tagName != topElement && oDragHandle.className != "dragAble") {
	    	oDragHandle = nn6 ? oDragHandle.parentNode : oDragHandle.parentElement;
	    }
	    if (oDragHandle.className=="dragAble") {
	        isdrag = true;
	        oDragObj = oDragHandle;
	        nTY = parseInt(oDragObj.style.top+0);
	        y = nn6 ? e.clientY : event.clientY;
	        nTX = parseInt(oDragObj.style.left+0);
	        x = nn6 ? e.clientX : event.clientX;
	        document.onmousemove=moveMouse;
	        return false;
	    }
	}

    function moveMouse(e) {
         if (isdrag) {
        	 
        	 if(oDragObj.height > 510){
		         oDragObj.style.top  =  (nn6 ? nTY + e.clientY - y : nTY + event.clientY - y)+"px";
		         if(parseInt(oDragObj.style.top) > 0){
		        	 oDragObj.style.top = "0px";
		         }
		         var dd = (document.getElementById("Imgbox").height+parseInt(oDragObj.style.top));
		         if(dd < 510){
		        	 oDragObj.style.top  =  (510 - document.getElementById("Imgbox").height)+"px";
		         }
 	        }
        	 
        	if(oDragObj.width > 800){
		         oDragObj.style.left  =  (nn6 ? nTX + e.clientX - x : nTX + event.clientX - x)+"px";
		         if(parseInt(oDragObj.style.left) > 0){
		        	 oDragObj.style.left = "0px";
		         }
		         var cc = (document.getElementById("Imgbox").width+parseInt(oDragObj.style.left));
		         if(cc < 800){
		        	 oDragObj.style.left  =  (800 - document.getElementById("Imgbox").width)+"px";
		         }
        	 }
	         return false;
         }
    }
</script>
<style>
.dragAble {
	position: absolute;
	cursor: move;
}

.picDiv {
	margin: 0px 0px 0px 0;
	border: 1px solid #666666;
	padding: 0;
	width: 100%;
	height: 100%;
	float: left;
	overflow: hidden;
	position: relative;
	cursor: move;
}

.realImg {
	position: fixed;
	z-index: 21474841;
	top: 50%;
	left: 50%;
	width: 800px;
	height: 510px;
	margin-left: -400px;
	margin-top: -255px;
}

.none{
	display:none;
}
</style>