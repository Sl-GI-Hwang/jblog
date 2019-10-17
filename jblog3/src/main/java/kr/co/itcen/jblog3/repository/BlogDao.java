package kr.co.itcen.jblog3.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.jblog3.vo.BlogVo;
import kr.co.itcen.jblog3.vo.CategoryVo;
import kr.co.itcen.jblog3.vo.PostVo;

@Repository
public class BlogDao {
	@Autowired
	private SqlSession sqlSession;

	public Map<String, Object> getAll(String id, long categoryNo, long postNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("categoryList", sqlSession.selectList("categoryList", id));
		
		Map<String, Object> buff = new HashMap<String, Object>();
		buff.put("blogId", id);
		buff.put("categoryNo", categoryNo);
		buff.put("postNo", postNo);
		
		map.put("postList", sqlSession.selectList("postList", buff));
		map.put("postInfo", sqlSession.selectOne("postInfo", buff));
		
		return map;
	}
	
	public List<CategoryVo> getCategory(String id) {
		List<CategoryVo> categoryvo = sqlSession.selectList("categoryList", id);
		return categoryvo;
	}

	public void write(PostVo postVo) {
		sqlSession.update("writeBlog", postVo);
	}

	public Map<String, Object> editCategory(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("categoryList", sqlSession.selectList("categoryList", id));
		map.put("getPostCount", sqlSession.selectList("getPostCount", id));
		
		return map;
	}

	public int delete(CategoryVo categoryVo) {
		//우선 삭제할 글들을 미분류로 넘기고
		sqlSession.update("movetoDefault", categoryVo);
		//삭제를 진행
		sqlSession.delete("deleteCategory", categoryVo);
		
		return 1;
	}

	public Map<String, Object> insert(CategoryVo categoryVo) {
		sqlSession.insert("insertNewCategory", categoryVo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		String id = categoryVo.getBlogId();
		
		map.put("categoryList", sqlSession.selectList("categoryList", id));
		map.put("getPostCount", sqlSession.selectList("getPostCount", id));
				
		return map;
	}

	public void modifyDefault(BlogVo blogvo) {
		sqlSession.update("modifyDefault", blogvo);
	}
	
	public BlogVo getDefault(String id) {
		return sqlSession.selectOne("getDefault", id);
	}
	
}
