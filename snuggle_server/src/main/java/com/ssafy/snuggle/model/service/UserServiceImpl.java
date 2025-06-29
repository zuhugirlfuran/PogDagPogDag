package com.ssafy.snuggle.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.snuggle.model.dao.UserDao;
import com.ssafy.snuggle.model.dto.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
    private UserDao userDao;
	
	@Override
	public int join(User user) {
//		System.out.println(user.toString());
		return userDao.insert(user);
	}

	@Override
	public User login(String userId, String password) {
		User user = userDao.selectById(userId);
    	if (user != null && user.getPassword().equals(password)) {  // 비밀번호와 아이디가 일치할때
    		return user;
    	}
        return null;
	}

	@Override
	public boolean isUsedId(String userId) {

        return userDao.selectById(userId) != null;
	}

	@Override
	public User selectUser(String userId) {
		return userDao.selectById(userId);
	}

}
