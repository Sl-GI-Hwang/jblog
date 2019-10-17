package kr.co.itcen.jblog3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@RequestMapping({"", "index"})
	public String main() {
		return "main/index";		
	}
}
