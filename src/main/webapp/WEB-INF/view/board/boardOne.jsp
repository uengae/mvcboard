<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:set var="b" value="${board}"></c:set>
	<h1>BoardOne</h1>
	<a href="/board/modifyBoard?boardNo=${b.boardNo}">수정</a>
	<a href="/board/removeBoard?boardNo=${b.boardNo}">삭제</a>
	<table>
		<tr>
			<th>localName</th>
			<td>${b.localName}</td>
		</tr>
		<tr>
			<th>boardTitle</th>
			<td>${b.boardTitle}</td>
		</tr>
		<tr>
			<th>boardContent</th>
			<td>${b.boardContent}</td>
		</tr>
		<tr>
			<th>memberId</th>
			<td>${b.memberId}</td>
		</tr>
		<tr>
			<th>createdate</th>
			<td>${b.createdate}</td>
		</tr>
		<tr>
			<th>updatedate</th>
			<td>${b.updatedate}</td>
		</tr>
	</table>
</body>
</html>