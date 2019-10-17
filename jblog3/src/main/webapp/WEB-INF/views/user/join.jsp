<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<script>
$(function(){
	$("#blog-id").change(function(){
		$("#btn-checkemail").show();
		$("#img-checkemail").hide();
	});	
	
	$("#btn-checkemail").click(function(){
		if($("#blog-id").val == ""){
			return;
		}
		
		$.ajax({
		    url: "${pageContext.servletContext.contextPath}/user/idCheck",
		    type: "POST",
		    data: {
		      "id": $("#blog-id").val()
		    },
		    dataType: "json",
		    success: function(result){
		    	if(result.success){
		    		alert("이미 존재하는 메일입니다.");
					$("#blog-id").val("");
					$("#blog-id").focus();
		    		return;
			    } 
		    	if(result.fail) {
		    		$("#btn-checkemail").hide();
			    	$("#img-checkemail").show();
		    	}	
		    },
		    error: function( err ){
		      	console.log(err)
		    }
		  })
	});
});
</script>
</head>
<body>
	<div class="center-content">
		<c:import url="/WEB-INF/views/includes/navigator.jsp" />
		<form class="join-form" id="join-form" method="post" action="${pageContext.servletContext.contextPath}/user/join">
			<label class="block-label" for="name">이름</label>
			<input id="name"name="name" type="text" value=""  required>
			
			<label class="block-label" for="blog-id">아이디</label>
			<input id="blog-id" name="id" type="text"  required> 
			<input id="btn-checkemail" type="button" value="id 중복체크">
			<img id="img-checkemail" style="display: none;" src="${pageContext.servletContext.contextPath}/assets/images/check.png">

			<label class="block-label" for="password">패스워드</label>
			<input id="password" name="password" type="password"   required />

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y" required>
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form>
	</div>
</body>
</html>
