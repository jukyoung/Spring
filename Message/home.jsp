<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
	<button type="button" id="toInput">toInput</button>
	<button type="button" id="toOutput">toOutput</button>
	<button type="button" id="toOutput2">toOutput2(ajax)</button>
	
	<!-- toInput버튼을 클릭했을 때는 클라이언트가 input.jsp 페이지를 응답
		toOutput버튼을 클릭했을 때는 output.jsp 페이지를 응답
	 -->
	 
	<script>
		document.getElementById("toInput").onclick = function(){
			location.href = "/toInput";
		}
		document.getElementById("toOutput").onclick = function(){
			location.href = "/toOutput";
		}
		document.getElementById("toOutput2").onclick = function(){
			location.href = "/toOutput2";
		}
	</script>
</body>
</html>
