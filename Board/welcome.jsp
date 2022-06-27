<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
 <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<title>welcome</title>
<style>
	#profileBox{
   		text-align : center;
   		background-color : #f5e2d7;
   	}
	.profile{
	 width: 100px;
	 height: 100px;
	 border-radius: 50%;
	 border : 0px;
	 display : inline-block;
	}
	#profile_default{
	 width: 100%;
	 height: 100%;
	 border-radius: 50%;
	}
	button{
		margin : 10px;
	}
	#profile_message{
		border : 0px;
		background-color : #f5e2d7;
	}
</style>
</head>
<body>
	<div class="container">
	<form id="profileForm">
		<div class="row m-auto">
			<div class="col" id="profileBox">
				<div class="col profile">
				<c:choose>
					<c:when test="${empty loginSession.profile_image}">
						<img src="/resources/images/default.png" id="profile_default">
					</c:when>
					<c:otherwise>
						<img src="/profile/${loginSession.profile_image}" id="profile_default">
					</c:otherwise>
				</c:choose>
				</div>
				<div class="col-12">
					<input type="file" id="file" name="file" class="form-control d-none w-50">
				</div>
				<div class="col d-flex justify-content-center">
				<c:choose>
					<c:when test="${empty loginSession.profile_message}">
						<input type="text" class="form-control" id="profile_message" name="profile_message" value="상태메세지가 없습니다." readonly>
					</c:when>
					<c:otherwise>
						<input type="text" class="form-control" id="profile_message" name="profile_message" value="${loginSession.profile_message}" readonly>
					</c:otherwise>
				</c:choose>
				</div>
			</div>
		</div>
		</form>
		<div class="row">
			<div class="col d-flex justify-content-center">
				<button type="button" id="changeProfile" class="btn btn-primary w-100">프로필 변경</button>
				<button type="button" id="saveProfile" class="btn btn-danger d-none w-100">프로필 저장</button>
				<button type="button" id="logoutBtn" class="btn btn-secondary w-100">로그아웃</button>
				<button type="button" class="btn btn-warning w-100" data-bs-toggle="modal"
					data-bs-target="#staticBackdrop">내정보</button>

				<!-- Modal -->
				<div class="modal fade" id="staticBackdrop"
					data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
					aria-labelledby="staticBackdropLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="staticBackdropLabel">내 정보</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"
									aria-label="Close"></button>
							</div>
							<div class="modal-body">
								<label>아이디</label>
								<input type="text" id="id" name="id" value="${loginSession.id}" class="form-control" readonly>
								<label>닉네임</label>
								<input type="text" id="nickname" name="nickname" value="${loginSession.nickname}" class="form-control" readonly>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-bs-dismiss="modal">닫기</button>
								<button type="button" id="modifyBtn" class="btn btn-warning">수정</button>
								<button type="button" id="completeBtn" class="btn btn-primary d-none">완료</button>
							</div>
						</div>
					</div>
				</div>
				<button type="button" id="boardBtn" class="btn btn-success w-100">게시판</button>
			</div>
		</div>
	</div>
	<script>
		// 게시판 버튼을 누르면
		$("#boardBtn").on("click", function(){
			location.href ="/board/toBoard";
		})
		//로그아웃 버튼
		document.getElementById("logoutBtn").onclick = function(){
			location.href="/member/logout";
		}
		// 내 정보 -> 수정 눌렀을때
		$("#modifyBtn").on("click", function(){
			$("#nickname").attr("readonly", false).focus();
			$("#modifyBtn").addClass("d-none");
			$("#completeBtn").removeClass("d-none");
		})
		// 내정보 -> 완료 버튼을 누르면
		$("#completeBtn").on("click", function(){
			let regexNickname = /^[a-zA-Z0-9ㄱ-흫]{4,10}$/;
			if(!regexNickname.test($("#nickname").val())){
				alert("형식에 맞지않는 닉네임 입니다. 4~10자 이내 영대소문자/숫자/한글로 입력해주세요.");
				return;
			}
			$("#completeBtn").addClass("d-none");
			$("#modifyBtn").removeClass("d-none");
			$("#nickname").attr("readonly", true);
			
			$.ajax({
				url : "/member/modify?nickname=" + $("#nickname").val()
				,type : "get"
				, success : function(data){
					console.log(data);
					if(data == "success"){
						alert("수정완료되었습니다.");
					}else if(data == "fail"){
						alert("수정 실패하였습니다. 다시 시도해주세요.");
					}
				}, error : function(e){
					console.log(e);
				}
			})
		})
		
		//프로필변경 누르면
		$("#changeProfile").on("click", function(){
			$("#file").removeClass("d-none");
			$("#profile_message").attr("readonly", false).focus();
			$("#changeProfile").addClass("d-none");
			$("#saveProfile").removeClass("d-none");

		})
		// 프로필 저장 버튼을 눌렀을때
		$("#saveProfile").on("click",function(){
			$("#saveProfile").addClass("d-none");// 프로필 저장버튼을 프로필 변경버튼으로 바꾸기
			$("#changeProfile").removeClass("d-none");
			$("#file").addClass("d-none");
			$("#profile_message").attr("readonly", true);
			/* jQuery의 serialize 함수를 이용해서 form을 전송할 수 있는 형태로 변환
				-> 파일을 전송해야하는 경우에는 serialize로 데이터를 변환해도 파일데이터가 정상적으로 변환x
				
				자바스크립트의 FormData 객체에 우리가 만들어둔 form을 자바스크립 객체로 넘겨서 만든 변수를
				ajax의 data 영역에 셋팅
				
				ajax로 파일데이터를 넘길때 설정해야할것들
				enctype, contentType : false , processData : false (true 로 주면 문자열로 변환이 되기때문에 false로 보낼것)
			*/
			console.log(document.getElementById("profileForm"));
			let data = new FormData(document.getElementById("profileForm"));
			$.ajax({
				url : "/member/modifyProfile"
				, type : "post"
				, enctype : "multipart/form-data"
				, contentType : false
				, processData : false
				, data : data
				, success : function(data){
					console.log(data);
					if(data == "success"){
						alert("수정에 성공했습니다.");
					}else if(data == "fail"){
						alert("수정에 실패했습니다. 다시 시도해 주세요.")
					}
				}, error : function(e){
					console.log(e);
				}
			})
			
		})
		// 사용자가 profile_image 파일태그를 이용해 프로필 사진을 선택했을 때
		document.getElementById("file").onchange = function(){
			let reader = new FileReader();
			
			reader.readAsDataURL(this.files[0]); 
			reader.onload = function(e){
				document.getElementById("profile_default").src = e.target.result;
			}
		}
	</script>
</body>
</html>