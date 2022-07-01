<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
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
				<td><input type="checkbox" class="deleteNo" name="no" value="${dto.no}"></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<button type="button" id="deleteBtn">선택 삭제</button>
	
	<form id="searchForm">
		<h2>검색</h2>
		<span>
			<select name="category">
				<option value="all">전체</option>
				<option value="no">번호</option>
				<option value="name">닉네임</option>
				<option value="memo">내용</option>
			</select>
			<input type="text" name="keyword" placeholder="검색">
			<button type="button" id="search">검색</button>
		</span>
	</form>
	<script>
	// 선택한 메모 삭제
	$("#deleteBtn").on("click", function(){
		let deleteArr = [];
		let noArr = $(".deleteNo:checked");
		
		for(let no of noArr){
			deleteArr.push(no.value);
		}
		if(deleteArr.length > 0){
			$.ajax({
				url : "/deleteCheck"
				, type: "post"
				, data : {"no[]" : deleteArr}
				, success : function(){
					location.href = "/toOutput";
				}, error : function(e){
					console.log(e);
				}
			})
		}else{
			alert("선택한 메모가 없습니다.");
		}
	})
	
	
	// 검색
	$("#search2").on("click", function(){
		let data = $("#searchForm2").serialize();
		console.log(data);
		
		$.ajax({
			url: "/search2"
				, type : "get"
				, data : data
				, success: function(data){
					console.log(data);
					mkElement(data);
					
				}, error : function(e){
					console.log(e);
				}
			});
		})
		// 검색 ver2
		$("#search").on("click", function(){
			let data = $("#searchForm").serialize();
			console.log(data);
			
			$.ajax({
				url: "/search"
					, type : "get"
					, data : data
					, success: function(data){
						console.log(data);
						mkElement(data);
						
					}, error : function(e){
						console.log(e);
					}
				});
			})
			
			function mkElement(data){
			$("tbody").empty();
			if(data.length == 0){ // 검색 결과 없음
				let tr = $("<tr>");
				let td = $("<td colspan=5>").append("검색 결과가 없습니다.");
				tr.append(td);
				tr.appendTo("tbody");
			}else{ // 검색 결과 있음
				for(let dto of data){
					let tr = $("<tr>");
					let td1 = $("<td>").append(dto.no);	
					let td2 = $("<td>").append(dto.name);
					let td3 = $("<td>").append(dto.memo);
					let modifyBtn = $("<button>").attr({
						type : "button", class : "modifyBtn", value : dto.no
					}).append("수정")
					let td4 = $("<td>").append(modifyBtn);
					let deleteCheck = $("<input>").attr({
						type : "checkbox", class : "deleteNo", value : dto.no, name: "no"
					})
					let td5 = $("<td>").append(deleteCheck);
					tr.append(td1, td2, td3, td4, td5);
					tr.appendTo("tbody");
				}
				
			}
		}

		// 수정 버튼
		let modifyBtn = document.getElementsByClassName("modifyBtn");
		for(let i = 0; i < modifyBtn.length; i++){
			modifyBtn[i].addEventListener("click", function(){
				location.href="/toModify?no="+this.value; // 서버의 현재 눌린 수정 버튼 values 전송
			});
		}	
		/*삭제 버튼
		let deleteBtn = document.getElementsByClassName("deleteBtn");
		for(let i = 0; i <deleteBtn.length; i++){
			deleteBtn[i].addEventListener("click", function(e){
				let answer = confirm("정말 삭제하시겠습니까?");
				if(answer){
					let val = e.target.value;
					location.href = "/delete?no="+val;
				}
			})
		}*/
	</script>
</body>
</html>