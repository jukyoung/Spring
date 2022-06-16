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
				<th>name</th>
				<th>memo</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="dto">
			<tr>
				<td>${dto.name}</td>
				<td>${dto.memo}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>