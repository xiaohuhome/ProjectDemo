<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="choosezt_ln pr clearfix" id="div_outer" style="height: 50px; overflow-y: hidden;">

    <div style="width:1100px;" class="fl" id="div_inner">	
    	<c:forEach var="i" begin="1" end="20">
    		<a href="javascript:void(0);" class="a-topic active">测试名称${i}
      		<span class="ml10" topicInfo="true">(${i})</span></a>
    	</c:forEach>	
    </div>
    <!--当专题超过两行时显示这个span-->
    <span class="zhaik_ln1 mt10 pa none" id="spn_open" style="right: 4px;">
        <span class="ztzk_ln vm a_blue_blue"><i class="icon vm iconfont icon-zhankai" onclick="openTopic(this);"></i></span>
    </span>
</div>
<script type="text/javascript">
    var outerHeight = parseInt($("#div_outer").css("height").replace("px",""));
    var innerHeight = parseInt($("#div_inner").css("height").replace("px",""));
    
    if (innerHeight > outerHeight) {
        $("#spn_open").css("top", (outerHeight-47)+"px");
        $("#spn_open").removeClass("none");
    }
    
    function openTopic(obj) {
        if ($(obj).hasClass("icon-zhankai")) {
            $("#div_outer").css("height", $("#div_inner").css("height"));
            $("#spn_open").css("top", (innerHeight-40)+"px");
            $(obj).removeClass("icon-zhankai");
            $(obj).addClass("icon-zhankai1");
            $(obj).parent().find("span").text("收起");
        }
        else if($(obj).hasClass("icon-zhankai1")){
            $("#div_outer").css("height", outerHeight);
            $("#spn_open").css("top", (outerHeight-45)+"px");
            $(obj).removeClass("icon-zhankai1");
            $(obj).addClass("icon-zhankai");
            $(obj).parent().find("span").text("展开");
        }
    }
</script>