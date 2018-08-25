<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link type="text/css" rel="stylesheet" href="<c:url value='/js/common/jquery-ui-1.8.23.custom/jquery-ui-1.8.23.custom.css'/>"/>
<script src="<c:url value='/js/common/jquery-ui-1.8.23.custom/jquery-ui-1.8.23.custom.min.js'/>"></script>
<script type="text/javascript">
	$(function() {
		$( "#sortableForm" ).sortable({
		     revert: true,
		     containment: "body", scroll: false,
		     update: function( event, ui ) {
		   	  updateProForm();
		     }
		});
	});
	
	function updateProForm(){
		var sortedIDs = $( "#sortableForm" ).sortable( "toArray",{ attribute: "sortForm" } );
		//更改数据库排序序号
		//将div的id和对应序号传入后台，批量修改
		//updFormPro(sortedIDs);
	}
</script>

<span id="sortableForm">
	<c:forEach begin="1" end="5" var="i" varStatus="index">
		<div sortForm="${index.count }" style="width: 600px;height: 200px;" class="bl">
			<h1>DIV${index.count }</h1>
		</div>
	</c:forEach>
</span>