package com.goodee.mvcboard.restapi;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goodee.mvcboard.service.SignService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SignRest {
	@Autowired
	private SignService signService;
	
	@PostMapping("/addSign")
	public int addSign(HttpServletRequest request, String sign) {
		String signPath = request.getServletContext().getRealPath("/sign/");
		int row = signService.addSign(signPath, sign);
		log.debug("addBoard signRow : " + row);
		return row;
	}
}
