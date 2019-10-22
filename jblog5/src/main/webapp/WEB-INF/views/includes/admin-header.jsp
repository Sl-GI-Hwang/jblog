<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<script>
$(document).ready(function(){
	$.ajax({
    	type: "POST",
        url: "${pageContext.servletContext.contextPath }/${authUser.id }/admin/getTitle",
        dataType: "json",
        data: "",
        success: function(result) {
        	$(".titles").append(result.title);
        	$(".ending").append(result.title);
        }
     });
});

</script>
	<h1 class="titles">
	</h1>
	<ul>
		<c:choose>
		<c:when test="${empty authUser }">
			<li><a href="${pageContext.servletContext.contextPath }/user/login">로그인</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="${pageContext.servletContext.contextPath }/user/logout">로그아웃</a></li>
			<li><a href="${pageContext.servletContext.contextPath }/${authUser.id }/admin/basic">블로그 관리</a></li>
		</c:otherwise>
		</c:choose>
	</ul>