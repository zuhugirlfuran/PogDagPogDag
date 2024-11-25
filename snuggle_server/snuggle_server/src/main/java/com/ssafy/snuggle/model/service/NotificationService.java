package com.ssafy.snuggle.model.service;

import java.util.List;

import com.ssafy.snuggle.model.dto.Notification;

public interface NotificationService {

	/**
     * 사용자 id에 따른 알림 리스트 조회
     * @param userId
     */
	List<Notification> getNotificationByUserId(String userId);
	
	/**
     * 알림 발생 시, 알림 리스트에 추가
     * @param Notification
     */
	int addNotification(Notification notice);
	
}
