<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modify</title>
</head>
<body>
	<form method="post" action="/modify">
		<input type="text" name="no" value="${dto.no}" hidden> 
		<label>nickname : </label> 
		<input type="text" name="nickname" value="${dto.nickname}"> 
		<label>message : </label> 
		<input type="text" name="message" value="${dto.message}">
		<button type="submit">전송</button>
	</form>
</body>
</html>