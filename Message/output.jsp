<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Output</title>
</head>
<body>
	<table border="1">
		<thead>
			<tr>
				<th>no</th>
				<th>nickname</th>
				<th>message</th>
				<th>modify</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="dto">
				<tr>
					<td>${dto.no}</td>
					<td>${dto.nickname}</td>
					<td>${dto.message}</td>
					<td><button type="button" class="modifyBtn" value="${dto.no}">수정</button></td>
					<td><button type="button" class="deleteBtn" value="${dto.no}">삭제</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script>
		// 수정 버튼을 클릭했을 때 서버로 no 를 넘겨줌 -> 해당 no의 데이터를 DB로부터 가져와서 수정페이지에 뿌려줄 것
		let modifyBtn = document.getElementsByClassName("modifyBtn");
		for(let i = 0; i < modifyBtn.length; i++){
			modifyBtn[i].addEventListener("click", function(){
				location.href="/toModify?no="+this.value; // 서버의 현재 눌린 수정 버튼 values 전송
			});
		}
		// 삭제 버튼을 클릭했을 때 confirm 창을 띄워줌 -> 진짜 삭제하겠다라고 하면 그때 seq 번호를 서버에 전송해서 삭제
		// Elements -> 배열 즉 deleteBtn 에는 배열이 담김
		let deleteBtn = document.getElementsByClassName("deleteBtn");
		for(let i = 0; i <deleteBtn.length; i++){
			deleteBtn[i].addEventListener("click", function(e){
				let answer = confirm("정말 삭제하시겠습니까?");
				// 만약 answer에 true 값이 담겨있으면 if문이 실행
				// 만약 answer에 false 값이 담겨있으면 if문 건너뜀.
				if(answer){
					let val1 = e.target.value; // 콜백펑션의 매개변수를 통해 value를 가져오는 방식
					let val2 = this.value; // 자기자신을 참조하는 this 키워드를 통해 value를 가져오는 방식
					
					location.href = "/delete?no=" + val1; //서버에 지금 눌린 삭제 버튼의 value 값을 전송해 삭제
				}
			})
		}
		
			
	</script>
</body>
</html>