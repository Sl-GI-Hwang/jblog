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
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>${blogInfo.title}</h1>
			<c:choose>
				<c:when test="${authUser.id eq blogInfo.id}">
					<li><a id="gotoBlog" href="${pageContext.servletContext.contextPath }/${authUser.id }/admin/basic">블로그 관리</a></li>
				</c:when>
				<c:when test="${empty authUser.id}">
					<li><a href="${pageContext.servletContext.contextPath }/user/login">로그인</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${pageContext.servletContext.contextPath }/user/logout">로그아웃</a></li>
				</c:otherwise>
			</c:choose>
		</div>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
				<c:choose>
					<c:when test="${empty postInfo }">
						<h4> 내용이 없습니다. </h4>
						<p>	내용이 없습니다. <p>
					</c:when>
					<c:otherwise>
						<h4>${postInfo.title }</h4>
						<p>
							 ${postInfo.contents }						
						<p>
					</c:otherwise>
				</c:choose>
					
				</div>
				<ul class="blog-list">
					<c:choose>
					<c:when test="${empty postList }">
						<li> 목록이 없습니다. </li>
					</c:when>
					<c:otherwise>
						<c:forEach var="posts" items="${postList }">
						<li><a href="${pageContext.servletContext.contextPath }/${blogInfo.id }/${posts.categoryNo }/${posts.postNo }">${posts.title}</a> <span>${posts.regDate}</span>	</li>
						</c:forEach>
					</c:otherwise>
				</c:choose>
					
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.servletContext.contextPath}${blogInfo.path }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach var="category" items="${categoryList }">
					<li><a href="${pageContext.servletContext.contextPath }/${blogInfo.id }/${category.categoryNo }">${category.name}</a></li>
				</c:forEach>
			</ul>
		</div>
		
		<div id="footer">
			<p>
				<strong>${blogInfo.title}</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>