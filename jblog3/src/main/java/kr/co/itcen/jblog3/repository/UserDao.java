package kr.co.itcen.jblog3.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.itcen.jblog3.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlSession;	
	
	public void join(UserVo vo) {
		// 유저정보		
		sqlSession.insert("insert", vo);
		
		// 블로그 동시에 생성
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", vo.getId());		
		map.put("title", "기본 제목입니다.");
		sqlSession.insert("insertBlog", map);
		map.clear();
		
		// 기본 카테고리 생성
		map.put("blog_id", vo.getId());
		map.put("name", "미분류");
		map.put("contents", "카테고리를 지정하지 않은 경우.");
		sqlSession.insert("insertCategory", map);
	}

	public UserVo get(UserVo vo) {
		UserVo getUser = sqlSession.selectOne("get", vo);
		return getUser;
	}

}
