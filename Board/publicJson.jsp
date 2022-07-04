<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSON 공공데이터</title>
</head>
<body>
	<table border=1>
		<thead>
			<tr>
				<th>축제명</th>
				<th>축제장소</th>
				<th>시작일</th>
				<th>홈페이지</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${data.FestivalBaseInfo}" var="item">
				<tr>
					<td>${item.festivalNm}</td>
					<td>${item.festivalVenue}(${item.addrRoad})</td>
					<td>${item.festivalBeginDate}</td>
					<td>${item.homePage}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>