<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>modifyBoard</h1>
	<c:set var="b" value="${board}"></c:set>
	<form action="${pageContext.request.contextPath}/board/modifyBoard" method="post">
	<input type="hidden" name="boardNo" value="${b.boardNo}">
		<table>
			<tr>
				<th>localName</th>
				<td>
					<input type="text" name="localName" value="${b.localName}">
				</td>
			</tr>
			<tr>
				<th>boardTitle</th>
				<td>
					<input type="text" name="boardTitle" value="${b.boardTitle}">
				</td>
			</tr>
			<tr>
				<th>boardContent</th>
				<td>
					<input type="text" name="boardContent" value="${b.boardContent}">
				</td>
			</tr>
			<tr>
				<th>boardId</th>
				<td>
					<input type="text" readonly="readonly" name="memberId" value="${b.memberId}">
				</td>
			</tr>
		</table>
		<button type="submit">수정</button>
	</form>
</body>
</html>