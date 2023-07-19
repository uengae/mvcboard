<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/signature_pad/1.5.3/signature_pad.min.js"></script>
	<script>
		$(document).ready(function(){
			/* 파일추가 */
			$('#addFileBtn').click(function(){
				if($('.file').last().val() == ''){
					alert('빈 파일업로드 태그가 있읍니다.');
				} else {
					let inputName = "multipartFile";
					$('#addFile').append("<div><input type=\"file\" name="
										+ inputName
										+ " class=\"file\"></div>");
				}
			});
			
			$('#removeFileBtn').click(function(){
				if($('.file').length != 1){
					$('.file').last().remove();
				}
			});
			
			/* 사인 */
			var canvas = $("#signature")[0];
			var signature = new SignaturePad(canvas, {
												minWidth : 2,
												maxWidth : 2,
												penColor : "rgb(0, 0, 0)"
			});
			
			$("#clear").on("click", function() {
				signature.clear();
			});
			
			$("#save").on("click", function() {
				if(signature.isEmpty()) {
					alert("내용이 없습니다.");
				} else {
					var data = signature.toDataURL("image/png");
				}
			});
			
			$('#send').click(function(){
				$.ajax({
					url : '/addSign',
					data : {sign : signature.toDataURL("image/png", 1.0)},
					type : 'post',
					success : function(jsonData){
						$('#addForm').submit();
					}
				})
			});
		})
	</script>
</head>
<body>
	<h1>AddBoard</h1>
	<form action="/board/addBoard" enctype="multipart/form-data" method="post" id="addForm">
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
					<input type="text" name="memberId" id="memberId">
				</td>
			</tr>
			<tr>
				<th>addSign</th>
				<td>
					<canvas style="border: 1px solid black;" id="signature" width="600" height="200"></canvas>
				</td>
			</tr>
			<tr>
				<th>addFile</th>
				<td>
					<input type="file" class="file" name="multipartFile">
					<div id="addFile"></div>
				</td>
			</tr>
		</table>
		<button id="addFileBtn" type="button">파일 추가</button>
		<button id="removeFileBtn" type="button">파일 제거</button>
		<button id="send" form="addForm" type="button">추가</button>
	</form>
</body>
</html>