package com.student.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.student.dao.StudentDAO;
import com.student.dto.StudentDTO;

@Controller
public class HomeController {
	@Autowired
	private StudentDAO dao;
	
	@RequestMapping(value = "/")
	public String home() {
		return "home";
	}
	@RequestMapping(value = "/toInput")
	public String toInput() {
		System.out.println("toInput 요청");
		return "input";
	}
	@RequestMapping(value = "/sendMemo")
	public String sendMemo(StudentDTO dto)throws Exception {
		System.out.println("sendMemo 요청");

			dao.insert(dto);
			return "redirect:/";
	}
	@RequestMapping(value = "/toError")
	public String toError() {
		return "error";
	}
	
	@RequestMapping(value = "toOutput")
	public String toOutput(Model model)throws Exception {
		System.out.println("toOutput 요청");
		
	 ArrayList<StudentDTO> list =dao.selectAll();
	 model.addAttribute("list", list);
	 return "output";
	}
	@RequestMapping(value = "delete")  // 데이터 삭제 요청
	public String delete(int no) throws Exception {
		System.out.println("삭제할 no : " + no);
		dao.delete(no);
		return "redirect:/toOutput";
	}
	@RequestMapping(value = "toModify")
	public String toModify(int no, Model model)throws Exception {
		StudentDTO dto =dao.selectByNo(no);
		model.addAttribute("dto", dto);
		return "modify";
	}
	@RequestMapping(value = "/modify") // 수정요청
	public String modify(StudentDTO dto)throws Exception {
		System.out.println("수정할 데이터 : " + dto.toString());
		dao.modify(dto);
		return "redirect:/toOutput";
	}
	@RequestMapping(value = "/toOutput2") //ajax toOutput2로 이동요청
	public String toOutput2() {
		return "output2";
	}
	@ResponseBody
	@RequestMapping(value = "outputAjax")
	public ArrayList<StudentDTO> outputAjax() throws Exception{
		ArrayList<StudentDTO> list = dao.selectAll();
		return list;
	}
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public String deleteAjax(int no) throws Exception{
		int rs = dao.delete(no);
		if(rs > 0) {
			return "ok";
		}else {
			return "fail";
		}
		
	}
	
	@ExceptionHandler
	public String handlerError(Exception e) {
		System.out.println("에러 발생");
		return "redirect:/toError";
	}
}
