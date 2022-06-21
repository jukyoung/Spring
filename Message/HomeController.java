package com.message.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.message.dao.MessageDAO;
import com.message.dto.MessageDTO;

@Controller
public class HomeController {
	@Autowired
	private MessageDAO dao;
	
	public HomeController() {
		System.out.println("HomeController 인스턴스 생성");
	}
	
	@RequestMapping(value = "/")
	public String home() {
		return "home";
	}
	
	@RequestMapping(value = "/toInput")
	public String toInput() {
		System.out.println("toInput 요청");
		return "input";
		// /WEB-INF/views/input.jsp
	}
	
	
	/* 클라이언트로부터 데이터를 전송받는 방법
	 * 1. servlet에서처럼 HttpServletReqeust가 가지고 있는
	 * 		getParameter("")메서드로 데이터를 전달받을 수 있음.
	@RequestMapping(value = "/sendInput")
	public String sendInput(HttpServletRequest request) {
		System.out.println("sendInput 요청");
		
		String nickname = request.getParameter("nickname");
		String message = request.getParameter("message");
		
		System.out.println(nickname + " : " + message);
		
		return "";
	}*/
	
	/* 클라이언트로부터 데이터를 전송받는 방법
	 * 2. 요청을 받아주는 메서드의 매개변수로 클라이언트가 넘겨주는 데이터의
	 * name이나 key값과 동일한 이름의 변수를 만들어 데이터를 받아줄 수 있음 
	 *	name값과 다른 매개변수명을 사용하면 데이터를 받아주지 못함
	@RequestMapping(value = "/sendInput")
	public String sendInput(String nickname, String message) {
		System.out.println("sendInput 요청");
		System.out.println(nickname  + " : " + message);
		return "";
	}*/
	
	/* 클라이언트로부터 데이터를 전송받는 방법
	 * 3. command 객체로 데이터를 전달받는 방법
	 * -> dto 타입으로 데이터를 받을 수 있음
	 * 
	 * 클라이언트의 요청이 들어왔을 때 
	 * default -> DTO의 기본생성자로 인스턴스를 생성
	 * 클라이언트가 넘겨주는 데이터의 name값을 이용해서
	 * name값과 동일한 이름의 setter를 찾아 
	 * 그 세터를 이용해 클라이언트가 보낸 데이터를 인스턴스에 셋팅하는 작업
	 * -> 즉 기본생성자는 있지만 setter가 없는 경우에는 
	 *    데이터 셋팅이 제대로 이뤄지지 않음. 
	 * 
	 * -> command 객체를 사용할때는
	 * 기본생성자 / 세터게터 는 반드시 무조건 필수적으로 만듦.
	 */
	/* contorller에서 jsp로 데이터를 전달하는 방식
	 * Model 이라는 객체를 이용해 값을 셋팅
	 * model.addAttribute(키, 값);
	 * 
	 * controller의 RequestMapping된 메서드의 
	 * 모든 default 리턴 방식은 forward   */
	@RequestMapping(value = "/sendInput")
	public String sendInput(MessageDTO dto, Model model)throws Exception {
		System.out.println("sendInput 요청");
		System.out.println(dto.toString());
		
		dao.insert(dto);
		// 데이터 저장이 잘 이뤄졌다면 home.jsp 를 클라이언트가 다시 볼 수 있게끔. 
		return "redirect:/";
	}
	
	@RequestMapping(value= "/toError") // 에러페이지로 이동
	public String toError() {
		return "error";
	}
	
	
	//return "output"; // 기본이 forward
	// spring 의 redirect 사용 방법
	// return "redirect:요청url"
	/*
	 * RequestMapping 메서드에서 String 값을 반환
	 * -> D.S -> View Resolver를 통해 아래의 값이 완성
	    /WEB-INF/views/output.jsp 
	  -> redirect를 통해 위의 url 이 클라이언트에게 응답되면
	  클라이언트가 접근할 수 없는 경로이기 때문에 잘못된 값이 됨
	  -> 즉 redirect를 할때는 ViewResolver를 거치면 안됨. 
	  -> 결과적으로 클라이언트가 직접 요청하게 될 url은
	  /toOutput 
	   */
	
	@RequestMapping(value = "/toOutput") // output페이지로 이동
	public String toOutput(Model model)throws Exception {
		System.out.println("toOutput 요청");
		// DB에서 msg 테이블에 있는 데이터를 모두 가져와 output.jsp 에 전달 
		ArrayList<MessageDTO> list = dao.selectAll();
		model.addAttribute("list", list);
		return "output";
	}
	
	@RequestMapping(value = "/delete") //데이터 삭제 요청
	public String delete(int no)throws Exception {
		System.out.println("삭제할 no : " + no);
		dao.delete(no);
		return "redirect:/toOutput";
	}
	@RequestMapping(value = "/toOutput2") // output2페이지로 이동
	public String toOutput2()throws Exception {
		System.out.println("toOutput 요청");
		// DB에서 msg 테이블에 있는 데이터를 모두 가져와 output2.jsp 에 전달 
//		ArrayList<MessageDTO> list = dao.selectAll();
//		model.addAttribute("list", list);
		return "output2";
	}
	@ResponseBody
	@RequestMapping(value = "/outputAjax") // ajax로 메세지 데이터 요청
	public ArrayList<MessageDTO> outputAjax() throws Exception{
		ArrayList<MessageDTO> list = dao.selectAll();
		return list;
	}
	// ajax 요청값에 대한 반환 -> annotation (@ResponseBody)
	// @ResponseBody를 이용하면 반환값이 ViewResolver 한테 가지않고
	// 이 controller 메서드를 요청한 jsp쪽 ajax쪽으로 그대로 반환값이 돌아간다.
	// 그래서 여기서 반환해주는 값은 반드시 요청한 곳에 되돌려주고 싶은 데이터를 그대로 반환해준다.
	@ResponseBody
	@RequestMapping(value = "/deleteAjax") // ajax 삭제요청
	public String deleteAjax(int no)throws Exception {
		System.out.println("삭제할 no : " + no);
		int rs = dao.delete(no);
		if(rs > 0) { // 삭제가 정상적으로 수행
			return "success";
		}else { //삭제가 정상적으로 수행되지 않음
			return "fail";
		}
		
	}
			//MessageDTO dto = dao.selectByNo(no);
			//ArrayList<MessageDTO> list = dao.selectAll();
			
			// dto와 list 를 한번에 반환해야 하는 경우 / 다른 종류의 list 2개를 한번에 반환해야 하는 경우
			// 결과적으로 몇개의 / 어떤 종류의 데이터를 반환하던 반환값은 딱 하나
			// Map 을 이용해 각각의 데이터를 담아 줄 것.
			// 모든 데이터형을 다 담아주는 Object 형 사용하기
			//HashMap<String, Object> map = new HashMap<>();
			//map.put("dto", dto);
			//map.put("list", list);
	
	
	@RequestMapping(value = "/toModify") //데이터 수정페이지 이동 요청
	public String toModify(int no, Model model)throws Exception {
		System.out.println("수정할 no : " + no);
		MessageDTO dto = dao.selectByNo(no);
		model.addAttribute("dto", dto);
		return "modify";
	}
	@RequestMapping(value = "/modify") // 수정 요청
	public String modify(MessageDTO dto) throws Exception {
		System.out.println("수정할 데이터 : " + dto.toString());
		dao.modify(dto);
		return "redirect:/toOutput";
	}
	// @ExceptionHandler이 붙은 메서드가 controller 내부에 있으면
	// 예외가 발생했을 경우 해당 메서드를 자동으로 실행해줌.
	// 해당 메서드의 매개변수로 전가된 예외가 받아짐.
	// 이 어노테이션이 위치한 컨트롤러 내부에서 발생한 예외에 대한 처리만 해줌.
	@ExceptionHandler
	public String handleError(Exception e){ 
		System.out.println("에러 발생");
		e.printStackTrace();
		return "redirect:/toError";
	}
	// @ExceptionHandler(처리해주고 싶은 예외명을 명시할 수 있음) 
	@ExceptionHandler(NullPointerException.class)
	public String handleNull(Exception e) {
		System.out.println("널포인터 익셉션 발생");
		e.printStackTrace();
		return "error2";
	}
	
	
	
	
}
