package kr.co.itcen.jblog3.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import kr.co.itcen.jblog3.repository.BlogDao;
import kr.co.itcen.jblog3.vo.BlogVo;
import kr.co.itcen.jblog3.vo.CategoryVo;
import kr.co.itcen.jblog3.vo.PostVo;

@Service
public class BlogService {
	@Autowired
	private BlogDao blogDao;

	public ModelMap getAll(String id, long categoryNo, long postNo) {
		ModelMap modelMap = new ModelMap();
		modelMap.put("blogInfo", getBlog(id));

		modelMap.addAllAttributes(blogDao.getAll(id, categoryNo, postNo));
		
		return modelMap;
	}
	
	private static final String SAVE_PATH = "/uploads";
	private static final String URL_PREFIX = "/assets/img";

	public List<CategoryVo> getCategory(String id) {
		return blogDao.getCategory(id);
	}

	public void write(PostVo postVo) {
		blogDao.write(postVo);
	}

	public ModelMap editCategory(String id) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAllAttributes(blogDao.editCategory(id));
		return modelMap;
	}

	public Boolean delete(CategoryVo categoryVo) {
		return blogDao.delete(categoryVo) == 1;
	}

	public Map<String, Object> insert(CategoryVo categoryVo) {
		Map<String, Object> map = blogDao.insert(categoryVo);
		return map;
	}

	public void modifyDefault(BlogVo blogvo, MultipartFile multipartFile) {
		try {
			blogvo.setLogo(multipartFile.getBytes());
			
			String originalFilename = multipartFile.getOriginalFilename();
			String saveFileName = generateSaveFilename(originalFilename.substring(originalFilename.lastIndexOf('.')+1));
			
			String url = saveFileName;
			blogvo.setPath(url);
			
			blogDao.modifyDefault(blogvo);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
	
	private String generateSaveFilename(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		
		return filename;
	}

	public BlogVo getBlog(String id) {
		BlogVo result = null;
		OutputStream os;
		
		try {
			result = blogDao.getDefault(id);
			if(result.getLogo() == null) {
				result.setPath("/assets/images/logo.jpg");
				return result;
			}
			
			os = new FileOutputStream(SAVE_PATH + "/" +result.getPath());
			os.write(result.getLogo());
			os.close();
			
			result.setPath(URL_PREFIX + "/" + result.getPath());
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	

}
