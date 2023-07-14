<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
	<script>
		$(document).ready(function(){
			$('#addFileBtn').click(function(){
				if($('.file').last().val() == ''){
					alert('빈 파일업로드 태그가 있읍니다.');
				} else {
					let inputName = "multipartFile";
					$('#addFile').append("<div><input type=\"file\" name="
										+ inputName
										+ " class=\"file\"></div>");
				}
			})
			$('#removeFileBtn').click(function(){
				if($('.file').length != 1){
					$('.file').last().remove();
				}
			})
		})
	</script>
</head>
<body>
	<h1>modifyBoard</h1>
	<c:set var="b" value="${board}"></c:set>
	<form action="/board/modifyBoard" enctype="multipart/form-data" method="post" id="modifyBoard">
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
			<tr>
				<th>img</th>
				<td>
				<c:forEach var="bfl" items="${boardfileList}">
				<c:if test="${bfl.filetype.equals(\"image/jpeg\")}">
						<img width="150px" height="150px" src="/upload/${bfl.saveFilename}">
				</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<th>modifyFile</th>
				<td>
				<c:forEach var="bfl" items="${boardfileList}">
						<div>
							${bfl.originFilename}
							<a href="/board/removeFile?boardfileNo=${bfl.boardfileNo}">삭제</a>
						</div>
				</c:forEach>
					<div id="addFile"></div>
				</td>
			</tr>
		</table>
		<button id="addFileBtn" type="button">파일 추가</button>
		<button id="removeFileBtn" type="button">파일 제거</button>
		<button type="submit" form="modifyBoard">수정</button>
	</form>
</body>
</html>