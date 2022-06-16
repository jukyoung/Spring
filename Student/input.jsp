<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Input</title>
</head>
<body>
	<form action="/sendMemo" method="post">
		<label>name : </label>
		<input type="text" name="name" placeholder="이름을 입력하세요.">
		<label>memo : </label>
		<input type="text" name="memo" placeholder="메모를 입력하세요.">
		<button type="submit">전송</button>
	</form>
</body>
</html>