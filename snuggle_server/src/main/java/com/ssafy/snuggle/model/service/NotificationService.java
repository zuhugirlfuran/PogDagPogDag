package com.ssafy.snuggle.model.service;

import java.util.List;

import com.ssafy.snuggle.model.dto.Notification;

public interface NotificationService {

	/**
     * 사용자 id에 따른 알림 리스트 조회
     * @param userId
     */
	List<Notification> getNotificationByToken(String userId);
	
}
