<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
 <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
	<title>메인</title>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col d-flex justify-content-center"><h2>로그인</h2></div>
		</div>
		<div class="row">
			<div class="col-4"><label>아이디</label></div>
			<div class="col-12"><input type="text" id="id" name="id" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col-4"><label>비밀번호</label></div>
			<div class="col-12"><input type="password" id="pw" name="pw" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col">
				<input type="checkbox" class="form-check-input">
				<label class="form-check-label" for="flexCheckDefault">
				    아이디 기억하기
				  </label>
				</div>
		</div>
		<div class="row">
			<div class="col d-flex justify-content-center">
				<button type="button" id="loginBtn" class="btn btn-primary">로그인</button>
				<button type="button" id="signupBtn" class="btn btn-warning">회원가입</button>
			</div>
		</div>
	</div>
	<script>
		document.getElementById("signupBtn").onclick = function(){
			location.href = "/member/toSignup";
		}
		// /member/~ 시작점에 어떤것과 관련된 기능인지를 표시해서 요청값 보내기
		// /board/~
		// /reply/~
	  
		// 로그인 버튼
		$("#loginBtn").on("click", function(){
			if($("#id").val() === "" || $("#pw").val() === ""){
				alert("아이디 혹은 비밀번호를 입력하세요.");
				return;
			}
			$.ajax({
				url : "/member/login"
				,type : "post"
				,data : {id : $("#id").val(), pw : $("#pw").val()}
				, success: function(data){
					console.log(data);
					if(data == "success"){
						location.href = "/member/toWelcome";
					}else if(data == "fail"){
						alert("아이디 혹은 비밀번호가 일치하지 않습니다.");
					}
				}, error : function(e){
					console.log(e);
				}
			})
		})
	  </script>
</body>
</html>
