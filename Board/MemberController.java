package kh.board.member;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kh.board.utils.EncryptionUtils;

// 클라이언트 요청중 /member로 시작하는 모든 요청은 이 컨트롤러가 받음
@RequestMapping("/member")
@Controller
public class MemberController {
	@Autowired
	private MemberService service;
	@Autowired
	private HttpSession session;

	
	@RequestMapping(value = "/toSignup") //회원가입 페이지 이동 요청
	public String toSignup() {
		return "member/signup";
	}
	
	/* 파일 데이터는 MultipartFile 형 변수로 받아줌.
	 * 
	 * */
	@RequestMapping(value = "/signup")
	public String signup(MemberDTO dto, MultipartFile file, HttpSession session) throws Exception{
		System.out.println(dto.toString());
		System.out.println("file : " + file);
		// 정해진 경로에 파일 저장 -> 세션을 이용해 접근
		
		// 폴더는 종류별로 나누는게 좋음..
		String realPath = session.getServletContext().getRealPath("profile");
		System.out.println("경로값 realPath : " + realPath);
		String profile_image = service.uploadProfile(file, realPath);
		dto.setProfile_image(profile_image);
		//pw 암호화 작업해야함
		dto.setPw(EncryptionUtils.getSHA512(dto.getPw()));
		service.signup(dto);
		return "redirect:/";
	}
	@ResponseBody
	@RequestMapping(value = "/checkId") //아이디중복검사
	public String checkId(String id) throws Exception{
		System.out.println("검사할 아이디 : " + id);
		boolean rs = service.checkId(id);
		if(rs) { // 사용가능
			return "ok";
		}else { // 중복
			return "nope";
		}
		
	}
	/* 컨트롤러는 클라이언트로부터 직접적으로 요청을 받고 그 요청에 따라
	 * 추가적인 데이터 가공이 필요하거나, 혹은 DB를 통해 데이터
	 * 수정/삭제/조회/추가해야 하는 경우 controller가 직접 dao를 호출하지 않고
	 * service 에게 그 작업을 전달함
	 * -> 그럼 service의 호출된 메서드가 추가적으로 처리해야하는 가공이나 dao 호출을 통해
	 * 비즈니스 로직을 수행하고 그에 따른 결과값만 바로 controller 에게 전달해 줌.
	 * -> controller는 결과값을 받아서 판단에 따라 응답값을 어떻게 클라이언트에 되돌려줄지 결정한다.
	 * 
	 *  즉 controller 는 클라이언트의 요청과 응답과 관련된 직접적인 일들만 처리
	 *  나머지 뒤에서 보이지 않는 일들은 서비스가 처리
	 * */
	@ResponseBody
	@RequestMapping(value = "/login") //로그인 요청
	public String login(String id, String pw)throws Exception {
		pw = EncryptionUtils.getSHA512(pw);
		System.out.println("로그인 요청 : " + id + " 암호화 비번 : "+ pw);
		MemberDTO dto = service.isLoginOk(id, pw);
		if(dto != null) { //로그인 성공
			session.setAttribute("loginSession", dto);
			return "success";
			
		}else {// 로그인 실패
			return "fail";
			
		}
	}
	@RequestMapping(value = "/toWelcome")
	public String toWelcome() {
		System.out.println("welcome 페이지 요청");
		return "member/welcome";
	}
	@RequestMapping(value="/logout") // 로그아웃
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	@ResponseBody
	@RequestMapping (value = "/modify") // 내정보 수정요청
	public String modify(String nickname) throws Exception{
		System.out.println("수정할 닉네임 : " + nickname);
		String id = ((MemberDTO)session.getAttribute("loginSession")).getId();
		int rs = service.modify(nickname, id);
		
		if(rs > 0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@ResponseBody
	@RequestMapping (value = "/modifyProfile") // 프로필 수정 요청
	public String modifyProfile(String profile_message, MultipartFile file) throws Exception{
		System.out.println("프로필 메세지 : " + profile_message);
		System.out.println("file : " +file);
		
		// 1. 서버의 profile 폴더에 새로운 프로필 사진을 업로드
		// 만약 사용자가 프로필 사진을 변경하지 않았다면(업로드x)
		// 새로운 프로필 사진을 업로드 x
		if(!file.isEmpty()) { // 넘어온 파일이 있다면
			String realPath = session.getServletContext().getRealPath("profile");
			// 파일을 업로드하는 service의 메서드를 호출하고 반환값으로 실제 저장된 파일명을 반환
			String profile_image = service.uploadProfile(file, realPath);
			// loginSession 안에 들어있는 dto의 profile_image 멤버필드 값을 새롭게 업로드된 파일명으로 변경
			((MemberDTO)session.getAttribute("loginSession")).setProfile_image(profile_image);
		}// 만약 사용자가 프로필 사진 수정을 안했다면(파일 업로드 x) == 원래의 값을 유지
		// loginSession에 DTO -> profile_image -> 사용자가 원래 가지고 있는 프로필사진의 이름값
		
		// 넘어온 변경된 프로필 메세지도 loginSession의 dto에 셋팅
		((MemberDTO)session.getAttribute("loginSession")).setProfile_message(profile_message);
		// 2. member테이블의 현재 프로필 수정중인 멤버의 데이터를 수정(프로필 메세지, 프로필 사진)
		// 결과적으로 member dto 를 넘겨주는것임
		int rs = service.modifyProfile((MemberDTO)session.getAttribute("loginSession"));
		if(rs > 0) {
			return "success";
		}else {
			return "fail";
		}
		
		
	}
	
	@ExceptionHandler
	public String toError(Exception e){
		System.out.println("예외 발생");
		e.printStackTrace();
		return "redirect:/toError";
	}

}
