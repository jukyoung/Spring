<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
 <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<title>회원가입</title>
<style>
    .row{
     margin-top : 20px;
    }
   	#profileBox{
   		text-align : center;
   	}
	.profile{
	 width: 100px;
	 height: 100px;
	 border-radius: 50%;
	 border : 1px solid grey;
	 display : inline-block;
	}
	#profile_default{
	 width: 100%;
	 height: 100%;
	 border-radius: 50%;
	}
</style>
</head>
<body>
<form id="signupForm" action="/member/signup" method="post" enctype="multipart/form-data">
	<div class="container">
		<div class="row">
			<div class="col d-flex justify-content-center"><h2>회원가입</h2></div>
		</div>
		<div class="row">
			<div class="col" id="profileBox">
				<div class="col-3 profile">
					<img src="/resources/images/default.png" id="profile_default">
				</div>
				<div class="col"><p>프로필 등록</p></div>
				<div class="col-6 d-flex justify-content-center">
					<input type="file" class="form-control" id="profile_image" name="file">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-4"><label>아이디</label></div>
			<div class="col-12"><input type="text" id="id" name="id" class="form-control"></div>
			<div class="col"><button type="button" class="btn btn-danger" id="checkId">중복확인</button></div>
		</div>
		<div class="row">
			<div class="col-4"><label>비밀번호</label></div>
			<div class="col-12"><input type="password" id = "pw" name="pw" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col-4"><label>닉네임</label></div>
			<div class="col-12"><input type="text" id="nickname" name="nickname" class="form-control"></div>
		</div>
		<div class="row">
			<div class="col d-flex justify-content-center">
				<button type="button" class="btn btn-secondary">취소</button>
				<button type="button" id="signupBtn" class="btn btn-primary">가입</button>
			</div>	
		</div>
	</div>
	</form>
	<script>
		// 사용자가 profile_image 파일태그를 이용해 프로필 사진을 선택했을 때
		// profile_default 이미지 태그에 선택된 사진을 띄워주는 작업
		document.getElementById("profile_image").onchange = function(){
			let reader = new FileReader();
			// 사용자가 파일태그를 이용해 파일을 선택했을 때 사용자의 로컬에 있는 파일의 정보를 브라우저에서 사용 가능하게끔 해주는 클래스
			
			//console.log(this.files[0]); // -> file 객체 0번째 : 지금 선택한 바로 그 객체 name : 원본 파일명 
			reader.readAsDataURL(this.files[0]); // -> 인자값으로 file태그
			
			// onload 함수가 트리거됨 -> onload 이벤트가 발생했을때 콜백펑션안에서 위에있는 이미지 태그의 src 에 이미지를 띄워줄 수 있는 경로값으로 대체
			reader.onload = function(e){
				// e.target.result -> 브라우저에서 바로 해석(로드)이 가능하게끔 변환된 이미지의 경로값
				console.log(e.target.result);
				// 위에 있는 이미지 태그의 src 속성값을 변환된 이미지 경로값으로 셋팅해주기(사용자가 선택한 이미지 띄우기)
				document.getElementById("profile_default").src = e.target.result;
			}
		}
		$("#checkId").on("click", function(){
			let id = $("#id").val();
			let regexId = /^[a-zA-Z0-9]{6,12}$/;
			if(id === ""){
				alert("아이디를 입력해주세요.");
				$("#id").focus();
				return;
			}else if( !regexId.test(id)){
				alert("조건에 맞지않는 아이디 입니다. 6~12자 이내 영대소문자/숫자로 입력해주세요.");
				$("#id").val("");
				$("#id").focus();
				return;
			}
			$.ajax({
				url: "/member/checkId?id="+id
				,type: "get"
				, success: function(data){
					console.log(data);
					if(data == "ok"){
						alert("사용가능한 아이디 입니다.");
						$("#id").attr("readonly", true);
					}else if(data == "nope"){
						alert("사용 불가능한 아이디 입니다.");
						$("#id").val("");
						$("#id").focus();
					}
				}, error: function(e){
					console.log(e);
				}
			})
		})
		$("#signupBtn").on("click", function(){
			let regexPw = /^[a-zA-Z0-9]{6,20}$/;
			let regexNickname = /^[a-zA-Z0-9ㄱ-흫]{4,10}$/;
			
			if($("#id").val === ""){
				alert("아이디를 입력해주세요.");
				return;
			}else if(!$("#id").is('[readonly]')){
				alert("아이디 중복검사를 해주세요.");
				return;
			}else if(!regexPw.test($("#pw").val())){
			
				alert("형식에 맞지않는 비밀번호 입니다.  6~20자 이내 영대소문자/숫자로 입력해주세요.");
				return;
			}else if(!regexNickname.test($("#nickname").val())){
				alert("형식에 맞지않는 닉네임 입니다. 4~10자 이내 영대소문자/숫자/한글로 입력해주세요.");
				return;
			}
			$("#signupForm").submit();
		})
		
	</script>
</body>
</html>