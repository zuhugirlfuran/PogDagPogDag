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
	private NotificationDao nDao;
	
	// userId로 알림 내역 불러오기
	@Override
	public List<Notification> getNotificationByUserId(String userId) {

		if (userId != null) {
			return nDao.select(userId);
		}
		
		// 유효한 userId가 없으면 null 반환
		return null;
		
	}
	
	// notice 저장
	public int addNotification(Notification notice) {
		if (notice != null ) {
			return nDao.insert(notice);
		}
		return -1;
	}

}
