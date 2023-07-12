<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>RemoveBoard</h1>
	<c:set var="b" value="${board}"></c:set>
	<form action="${pageContext.request.contextPath}/board/removeBoard" method="post">
	<input type="hidden" name="boardNo" value="${b.boardNo}">
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
				<th>boardId</th>
				<td>
					<input type="text" name="memberId" value="삭제하실려면 아이디를 입력하시오">
				</td>
			</tr>
		</table>
		<button type="submit">삭제</button>
	</form>
</body>
</html>