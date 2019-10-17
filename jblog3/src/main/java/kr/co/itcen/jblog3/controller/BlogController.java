package kr.co.itcen.jblog3.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.itcen.jblog3.dto.JSONResult;
import kr.co.itcen.jblog3.security.Auth;
import kr.co.itcen.jblog3.service.BlogService;
import kr.co.itcen.jblog3.vo.BlogVo;
import kr.co.itcen.jblog3.vo.CategoryVo;
import kr.co.itcen.jblog3.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	
	@RequestMapping(value={"", "/{pathNo1}", "/{pathNo1}/{pathNo2}"})
	public String index(
			@PathVariable String id,
			@PathVariable Optional<Long> pathNo1,
			@PathVariable Optional<Long> pathNo2,
			ModelMap modelMap
			) {
		long categoryNo = 0L;
		long postNo = 0L;
		if(pathNo2.isPresent()) {
			postNo = pathNo2.get();
			categoryNo = pathNo1.get();
		} else if(pathNo1.isPresent()) {
			categoryNo = pathNo1.get();
		}
		
		modelMap.putAll(blogService.getAll(id, categoryNo, postNo));
		return "blog/blog-main";
	}
	
	@Auth("ADMIN")
	@RequestMapping(value={ "/admin", "/admin/basic" }, method=RequestMethod.GET)
	public String adminBasic(@PathVariable String id,
			Model model
			){
		model.addAttribute("blogvo", blogService.getBlog(id));
		return "blog/blog-admin-basic";
	}
		
	@Auth("ADMIN")
	@RequestMapping(value="/admin/category", method=RequestMethod.GET)
	public String adminCategory(@PathVariable String id,
			ModelMap model){
		model.putAll(blogService.editCategory(id));
		return "blog/blog-admin-category";
	}
	
	@Auth("ADMIN")
	@RequestMapping(value="/admin/write", method=RequestMethod.GET)
	public String adminWrite(@PathVariable String id,
			Model model){
		// 카테고리 리스트
		model.addAttribute("categoryList", blogService.getCategory(id));
		return "blog/blog-admin-write";
	}
	
	@Auth("ADMIN")
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String adminWrite(@PathVariable String id,
			@ModelAttribute PostVo postVo,
			Model model	
			){
		// 빋은걸로 처리
		blogService.write(postVo);
		model.addAttribute("categoryList", blogService.getCategory(id));
		return "blog/blog-admin-write";
	}
	
	@Auth("ADMIN")
	@RequestMapping(value="/admin/editDefault", method=RequestMethod.POST) 
	public String modifyDefault(
			@PathVariable String id,
			@ModelAttribute BlogVo blogvo,
			@RequestParam(value="logomenu",required=false) MultipartFile multipartFile,
			Model model) {
		
		blogvo.setId(id);
		System.out.println(blogvo.toString());
		
		blogService.modifyDefault(blogvo, multipartFile);
		return "redirect:/"+id+"/admin";
	}
	
	@Auth("ADMIN")
	@ResponseBody
	@RequestMapping(value="/admin/delete", method=RequestMethod.POST)
	public JSONResult delete(@ModelAttribute CategoryVo categoryVo) {
		Boolean exist = blogService.delete(categoryVo);
		return JSONResult.success(exist);
	}
	
	@Auth("ADMIN")
	@ResponseBody
	@RequestMapping(value="/admin/insertCat", method=RequestMethod.POST)
	public Map<String, Object> insertCat(@ModelAttribute CategoryVo categoryVo) {
		Map<String, Object> result = blogService.insert(categoryVo);
		result.put("success", true);
		return result;
	}
	
	@Auth("ADMIN")
	@ResponseBody
	@RequestMapping(value="/admin/getTitle", method=RequestMethod.POST)
	public Map<String, Object> getTitle(@PathVariable String id){
		Map<String, Object> map = new HashMap<String, Object>();
		BlogVo result = blogService.getBlog(id);
		map.put("title", result.getTitle());
		return map;
	}

}
