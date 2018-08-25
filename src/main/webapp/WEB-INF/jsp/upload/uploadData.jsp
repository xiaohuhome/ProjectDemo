<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
	.div_img_self{width:72px; height: 90px; position: relative; float: left; margin-bottom: 15px; margin-right: 15px;text-align:center;}
	.img_self{width: 70px;height:70px;border: 1px dashed #BDC3C7; cursor: pointer;}
	.del_img_self{position: absolute; top: -5px; right: -8px;cursor: pointer;}
</style>

<div id="resultData_${uuid}" class="clearfix mt15 div_id" style="width: 100%;"> 
	<c:forEach items="${fileDataList}" var="f" varStatus="statu">
		<c:if test="${f.file_type == 'I'}">
			<div class="div_img_self" id="data_${f.id}">
				<img src='${f.url}' ondblclick="biggerImg(this);" class="img img_self" style="" title="双击查看原图"/>
				<div class="del_img del_img_self" style="color: red;" title="点击删除图片" onclick="del('${f.id}','${f.uuid}')"></div>		
			</div>		
		</c:if>
		<c:if test="${f.file_type=='A'}">
			<div class="div_img_self" id="data_${f.id}">
				<img src='<c:url value="/image/common/mp3.png"/>' ondblclick="displayMp3('${f.url}');" class="img_self"  title="双击收听音频"/>
				<div class="del_img del_img_self" title="点击删除文件" onclick="del('${f.id}','${f.uuid}')"></div>		
			</div>	
		</c:if>	
		<c:if test="${f.file_type=='V'}">
			<div class="div_img_self" id="data_${f.id}">
				<img src='<c:url value="/image/common/mp4.png"/>' ondblclick="displayMp4('${f.url}');" class="img_self" title="双击观看视频1"/>
				<div class="del_img del_img_self" title="点击删除文件" onclick="del('${f.id}','${f.uuid}')"></div>		
			</div>				
		</c:if>			
		<c:if test="${f.file_type=='P'}">
			<div class="div_img_self" id="data_${f.id}"> 
				<a href='${f.url}' target="_blank"><img src='<c:url value="/image/common/pdf.png"/>' class="img_self" title="点击下载文件"/></a>
				<div class="del_img del_img_self" title="点击删除文件" onclick="del('${f.id}','${f.uuid}')"></div>		
			</div>				
		</c:if>				
	</c:forEach>
</div>

