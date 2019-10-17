package kr.co.itcen.jblog3.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.itcen.jblog3.dto.JSONResult;
import kr.co.itcen.jblog3.service.UserService;
import kr.co.itcen.jblog3.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "/user/join";		
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(
			@ModelAttribute UserVo vo) {
		userService.join(vo);		
		return "/user/joinsuccess";		
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "/user/login";		
	}
	
	@ResponseBody
	@RequestMapping(value="/idCheck", method=RequestMethod.POST)
	public Map<String, Object> idCheck(
			@ModelAttribute UserVo vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserVo uservo = userService.checkId(vo);
		if(uservo == null) {
			map.put("fail", true);
			return map;	
		} 		
		if(uservo.getId() != null) {
			map.put("success", true);
			return map;	
		} 
		map.put("fail", false);
		return map;		
	}
}
