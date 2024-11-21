package com.ssafy.snuggle.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.snuggle.model.dao.NotificationDao;
import com.ssafy.snuggle.model.dao.UserDao;
import com.ssafy.snuggle.model.dto.Notification;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private UserDao uDao;
	
	@Autowired
	private NotificationDao nDao;
	
	@Override
	public List<Notification> getNotificationByToken(String token) {
		
		// token으로 userId 찾음
		String userId = uDao.findUserIdByToken(token);
		
		if (userId != null) {
			return nDao.select(userId);
		}
		
		// 유효한 userId가 없으면 null 반환
		return null;
		
	}


}
