<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>AddBoard</h1>
	<form action="${pageContext.request.contextPath}/board/addBoard" method="post">
		<table>
			<tr>
				<th>localName</th>
				<td>
					<input type="text" name="localName">
				</td>
			</tr>
			<tr>
				<th>boardTitle</th>
				<td>
					<input type="text" name="boardTitle">
				</td>
			</tr>
			<tr>
				<th>boardContent</th>
				<td>
					<input type="text" name="boardContent">
				</td>
			</tr>
			<tr>
				<th>memberId</th>
				<td>
					<input type="text" name="memberId">
				</td>
			</tr>
		</table>
		<button type="submit">추가</button>
	</form>
</body>
</html>