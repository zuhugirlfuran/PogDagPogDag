package com.ssafy.snuggle.model.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.snuggle.model.dao.UserDao;
import com.ssafy.snuggle.model.dto.User;

public class UserServiceImpl implements UserService{

	@Autowired
    private UserDao userDao;
	
	@Override
	public int join(User user) {
		return userDao.insert(user);
	}

	@Override
	public User login(String user_id, String password) {
		User user = userDao.selectById(user_id);
    	if (user.getPassword().equals(password)) {  // 비밀번호와 아이디가 일치할때
    		return user;
    	}
        return null;
	}

	@Override
	public boolean isUsedId(String user_id) {
		User user = userDao.selectById(user_id);
    	if (user != null) {
    		return true;
    	}
        return false;
	}

	@Override
	public User selectUser(String user_id) {
		return userDao.selectById(user_id);
	}

}
