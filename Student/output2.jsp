<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Output2</title>
</head>
<body>
	<table border="1px">
		<thead>
			<tr>
				<th>no</th>
				<th>name</th>
				<th>memo</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
	<script>
		// 페이지가 로드될때 뿌려주기
		$(document).ready(function(){
			makeDynamicEl();
		})
		function makeDynamicEl(){
			$.ajax({
				url : "/outputAjax"
				, success : function(data){
					console.log(data);
					
					$("tbody").empty();
					for(let dto of data){
						let td1 = $("<td>").append(dto.no);
						let td2 = $("<td>").append(dto.name);
						let td3 = $("<td>").append(dto.memo);
						let deleteBtn = $("<button>").attr({"class" : "deleteBtn", "value" : dto.no}).append("삭제");
						let td4 = $("<td>").append(deleteBtn);
						let tr = $("<tr>").append(td1, td2, td3, td4);
						$("tbody").append(tr);
					}
					
				}, error : function(e){
					console.log(e);
				}
				
			})
		}
		
		//삭제 버튼
		$("tbody").on("click", ".deleteBtn", function(e){
			let answer = confirm("정말 삭제하시겠습니까?");
			if(answer){
				let val = e.target.value;
				$.ajax({
					url : "/deleteAjax"
					, type : "post"
					, data : {no : val}
					, success: function(data){
						console.log(data);
						if(data == "ok"){
							makeDynamicEl();
						}else if(data == "fail"){
							alert("데이터 삭제에 실패했습니다. 다시 시도해 주세요.");
						}
					}, error : function(e){
						console.log(e);
					}
				})
			}
		})
		
	</script>
</body>
</html>