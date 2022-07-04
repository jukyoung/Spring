<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AJAX 공공데이터</title>
</head>
<body>
	<button type="button" id="getJSON">getJSON</button>
	<button type="button" id="getXML">getXML</button>
	<table border=1>
		<thead>
			<tr>
				<th>축제명</th>
				<th>축제장소</th>
				<th>시작일</th>
				<th>종료일</th>
				<th>홈페이지</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
	<script>
		$(document).ready(function(){
			$("#getJSON").click(function(){
				$.ajax({
					url: "/public/getJSON"
					, success : function(data){
						console.log(data);
						console.log(data.FestivalBaseInfo);
						for(let item of data.FestivalBaseInfo){
							let tr =$("<tr>");
							let td1 = $("<td>").append(item.festivalNm);
							let td2 = $("<td>").append(item.festivalVenue);
							let td3 = $("<td>").append(item.festivalBeginDate);
							let td4 = $("<td>").append(item.festivalEndDate);
							let td5 = $("<td>").append(item.homePage);
							
							tr.append(td1, td2, td3, td4, td5);
							tr.appendTo("tbody");
						}
					}, error : function(e){
						console.log(e);
					}
				})
			})
			$("#getXML").click(function(){
				$.ajax({
					url: "/public/getXML"
						, success : function(data){
							//console.log($(data));
							let items = $(data).find("item") //item 이라는 요소를 찾기
							//console.log(items);
							
							for (let item of items){
								//console.log($(item).find("festivalNm").html());
								let tr =$("<tr>");
								let td1 = $("<td>").append($(item).find("festivalNm").html());
								let td2 = $("<td>").append($(item).find("festivalVenue").html());
								let td3 = $("<td>").append($(item).find("festivalBeginDate").html());
								let td4 = $("<td>").append($(item).find("festivalEndDate").html());
								let td5 = $("<td>").append($(item).find("homePage").html());
								
								tr.append(td1, td2, td3, td4, td5);
								tr.appendTo("tbody");
							}
						}, error : function(e){
							console.log(e);
						}
				})
			})
		})
	</script>
</body>
</html>