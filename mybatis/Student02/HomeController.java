package kh.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.student.dto.StudentDTO;
import kh.student.service.StudentService;


@Controller
public class HomeController {
	@Autowired
	private StudentService service;
	
	
	@RequestMapping(value = "/")
	public String home() {
		return "home";
	}
	@RequestMapping(value = "/toInput")
	public String toInput() {
		System.out.println("toInput 요청");
		return "input";
	}
	@RequestMapping(value="/sendMemo")
	public String sendMemo(StudentDTO dto)throws Exception{
		service.insert(dto);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/toError")
	public String toError() {
		return "error";
	}
	
	@RequestMapping(value = "/toOutput")
	public String toOutput(Model model)throws Exception {
		System.out.println("toOutput 요청");
		List<StudentDTO> list = service.selectList();
		model.addAttribute("list", list);
		return "output";
	}
	@RequestMapping(value = "delete")  // 데이터 삭제 요청
	public String delete(int no) throws Exception {
		service.delete(no);
		return "redirect:/toOutput";
	}
	@RequestMapping(value="/toModify")
	public String toModify(int no, Model model) throws Exception{
		StudentDTO dto = service.selectOne(no);
		model.addAttribute("dto", dto);
		return "modify";
	}
	@RequestMapping(value="/modify")
	public String modify(StudentDTO dto) throws Exception{
		service.update(dto);
		return "redirect:/toOutput";
	}
	
	@ResponseBody
	@RequestMapping(value="/search")
	public List<StudentDTO>search(String category, String keyword) throws Exception{
		List<StudentDTO> list = service.search(category, keyword);
		return list;
	}
	
	
	@ExceptionHandler
	public String handlerError(Exception e) {
		System.out.println("에러 발생");
		return "redirect:/toError";
	}
}
