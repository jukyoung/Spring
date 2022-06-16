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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public String sendMemo(StudentDTO dto) {
		System.out.println("sendMemo 요청");
		try {
			dao.insert(dto);
		}catch(Exception e) {
			e.printStackTrace();
			return "redirect:/toError";
		}
		return "redirect:/";
	}
	@RequestMapping(value = "/toError")
	public String toError() {
		return "error";
	}
	
	@RequestMapping(value = "toOutput")
	public String toOutput(Model model) {
		System.out.println("toOutput 요청");
		try {
			ArrayList<StudentDTO> list =dao.selectAll();
			model.addAttribute("list", list);
		}catch(Exception e) {
			e.printStackTrace();
			return "redirect:/toError";
		}
		return "output";
	}
}
