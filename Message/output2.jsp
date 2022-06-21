<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- jQuery cdn -->
    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Output2</title>
</head>
<body>
	<table border="1">
		<thead>
			<tr>
				<th>no</th>
				<th>nickname</th>
				<th>message</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
	<script>
		// output2.jsp 가 로드되면 자동으로 ajax요청을 보내서 데이터를 가져온 후
		// 동적으로 데이터를 뿌려줄 것.
		$(document).ready(function(){
			makeDynamicEl();
		})
		function makeDynamicEl(){ //out[itAjax 요청을 통해 데이터 가져와 동적으로 뿌려주는 함수
			$.ajax({
				url:"/outputAjax"
				, success : function(data){
					console.log(data);
					$("tbody").empty();
					
					for(let dto of data){
						let td1 = $("<td>").append(dto.no);
						let td2 = $("<td>").append(dto.nickname);
						let td3 = $("<td>").append(dto.message);
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
		// 부모 요소를 기준으로 동적으로 만들어진 삭제 버튼에게 이벤트 처리
		$("tbody").on("click", ".deleteBtn", function(e){
			let answer = confirm("정말 삭제하시겠습니까?");
			
			if(answer){
				let val = e.target.value;

				$.ajax({
					url : "/deleteAjax"
					, type : "post"
					, data : {no : val}
					, success : function(data){
						console.log(data);
						if(data == "success"){
							// 삭제된 데이터를 반영한 테이블 뿌려줄것
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