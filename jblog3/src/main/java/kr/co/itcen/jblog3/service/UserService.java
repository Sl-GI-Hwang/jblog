package kr.co.itcen.jblog3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.jblog3.repository.UserDao;
import kr.co.itcen.jblog3.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public void join(UserVo vo) {
		userDao.join(vo);
	}

	public UserVo getUser(UserVo vo) {
		UserVo authUser= userDao.get(vo);
		return authUser;
	}

	public UserVo checkId(UserVo vo) {
		return userDao.get(vo);
	}
}
