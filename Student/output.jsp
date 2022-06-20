<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Output</title>
</head>
<body>
	<table border="1px">
		<thead>
			<tr>
				<th>no</th>
				<th>name</th>
				<th>memo</th>
				<th>modify</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="dto">
			<tr>
				<td>${dto.no}</td>
				<td>${dto.name}</td>
				<td>${dto.memo}</td>
				<td><button type="button" class="modifyBtn" value="${dto.no}">수정</button></td>
				<td><button type="button" class="deleteBtn" value="${dto.no}">삭제</button></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<script>
		// 수정 버튼
		let modifyBtn = document.getElementsByClassName("modifyBtn");
		for(let i = 0; i < modifyBtn.length; i++){
			modifyBtn[i].addEventListener("click", function(){
				location.href="/toModify?no="+this.value; // 서버의 현재 눌린 수정 버튼 values 전송
			});
		}	
		//삭제 버튼
		let deleteBtn = document.getElementsByClassName("deleteBtn");
		for(let i = 0; i <deleteBtn.length; i++){
			deleteBtn[i].addEventListener("click", function(e){
				let answer = confirm("정말 삭제하시겠습니까?");
				if(answer){
					let val = e.target.value;
					location.href = "/delete?no="+val;
				}
			})
		}
	</script>
</body>
</html>