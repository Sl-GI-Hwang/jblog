<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assets/css/jblog.css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<script>
$(function(){
	$(document).on("click", ".admin-cat img", function(){
		var categoryNo = $(this).attr('id');
		var thisObj = $(this);
		
		$.ajax({
			url: "${pageContext.servletContext.contextPath }/${authUser.id }/admin/delete",
			type: "POST",
			dataType: "json",
			data: {
				"blogId": "${authUser.id }",
				"categoryNo": categoryNo},
			success: function( result ){
			     if( result ){
			        // 삭제 버튼을 누른 row 제거
			        alert("카테고리 삭제가 완료되었습니다");
			        $(thisObj).parent().parent().remove();
			      }
			    },
			error: function( err ){
				console.log(err)
			}
		});	
		
	});
	
	$("#btn-add-category").click(function(){
		name, desc
		if($("#name").val() == "" || $("#desc").val() == "") {
			alert("값을 입력하세요");
			$("#name").val("");
			$("#desc").val("");
			
			return;
		}
		
		var categoryNo = $("#desc").val();
		
		$.ajax({
		    url: "${pageContext.servletContext.contextPath}/${authUser.id }/admin/insertCat",
		    type: "POST",
		    data: {
		      "blogId": "${authUser.id }",
		      "name": $("#name").val(),
		      "contents": $("#desc").val()
		    },
		    dataType: "json",
		    success: function(result){
		    	if(result.success) {
		    		alert("카테고리 추가가 완료되었습니다");
		    		categoryList = result.categoryList;
			    	getPostCount = result.getPostCount;
			    	
			    	removeTable();
				    createNewTable(categoryList, getPostCount);	    		
		    	}
		    },
		    error: function( err ){
		      	console.log(err)
		    }
		  })
	});
	
	function removeTable(){
		  // 원래 테이블 제거
		  $(".origin-tbody").remove();
		  // ajax로 추가했던 테이블 제거
		  $(".new-tbody").remove();
		  // 입력 form 초기화
		  $("#name").val("");
		  $("#desc").val("");
	}

	function createNewTable(categoryList, getPostCount){
		  var $newTbody = $("<tbody class='new-tbody'></tbody>")
		  $(".admin-cat").append($newTbody)
			
		  var a = categoryList.length;
		  for(let category in categoryList){
			  for(let postnum in getPostCount) {
				  if(categoryList[category].categoryNo == getPostCount[postnum].categoryNo) {
					  var postcount = getPostCount[postnum].count;
				  }
			  }
			  $newTbody.append(
			   	"<tr>" +
		        "<td>" + a + "</td>" +
		        "<td>" + categoryList[category].name + "</td>" +
		        "<td>" + postcount + "</td>"+
		        "<td>" + categoryList[category].contents + "</td>" +
		        "<td>" +
		        "<img src='${pageContext.servletContext.contextPath}/assets/images/delete.jpg'" +
			    "id="+categoryList[category].categoryNo+">" +	
		        "</td>" +
		        "</tr>");
			  a--;
		  }
	}
});
</script>
</head>
<body>
	<div id="container">
		<div id="header">
			<c:import url="/WEB-INF/views/includes/admin-header.jsp" />
		</div>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.servletContext.contextPath}/${authUser.id}/admin/basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.servletContext.contextPath}/${authUser.id}/admin/write">글작성</a></li>
				</ul>
		      	<table class="admin-cat">
		      		<thead>
			      		<tr>
			      			<th>번호</th>
			      			<th>카테고리명</th>
			      			<th>포스트 수</th>
			      			<th>설명</th>
			      			<th>삭제</th>      			
			      		</tr>
			      	</thead>
			      	<tbody class = "origin-tbody">
		      		<c:forEach var="category" items="${categoryList }" varStatus="status">
		      		<tr>
						<td>${fn:length(categoryList) - status.count + 1}</td>
						<td>${category.name}</td>
						
						<c:forEach var="postnum" items="${getPostCount }">
							<c:if test="${category.categoryNo == postnum.categoryNo }">
								<td>${postnum.count}</td>
							</c:if>
						</c:forEach>
						<td>${category.contents}</td>
						<td><img src="${pageContext.servletContext.contextPath}/assets/images/delete.jpg" id="${category.categoryNo}"></td>
					</tr>
					</c:forEach>	
					</tbody>				  
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input type="text" id = "name" name="name"></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input type="text" id = "desc" name="desc"></td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input id="btn-add-category" type="submit" value="카테고리 추가"></td>
		      		</tr>      		      		
		      	</table> 
			</div>
		</div>
		<div id="footer">
			<c:import url="/WEB-INF/views/includes/admin-footer.jsp" />
		</div>
	</div>
	</body>
</html>