package com.goodee.mvcboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/home") // web.xml의 url 패턴 매핑 or 에노테이션 웹 서블릿
	public String home() {
		return "home"; // RequestDispatcher.forward()
	}
	
}
