<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공공데이터 홈</title>
</head>
<body>
	<button type="button" id="toJSON">JSON 데이터 요청</button>
	<button type="button" id="toXML">XML 데이터 요청</button>
	<button type="button" id="toAJAX">AJAX 페이지 요청</button>
	
	<script>
		document.getElementById("toJSON").onclick = function(){
			location.href ="/public/toJSON";
		}
		document.getElementById("toXML").onclick = function(){
			location.href ="/public/toXML";
		}
		document.getElementById("toAJAX").onclick = function(){
			location.href ="/public/toAJAX";
		}
	
	</script>
	
</body>
</html>