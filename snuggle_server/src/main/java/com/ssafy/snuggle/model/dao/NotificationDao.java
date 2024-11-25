package com.ssafy.snuggle.model.dao;

import java.util.List;

import com.ssafy.snuggle.model.dto.Notification;

public interface NotificationDao {

	/**
     * 사용자 userId에 따른 알림 리스트틀 조회한다.
  	 *
     * @param userId
     * @return
     */
	List<Notification> select(String userId);
	
	/**
     * 사용자 userId에 해당하는 리스트에 알림 데이터를 저장한다.
  	 *
     * @param Notification
     * @return
     */
	int insert(Notification notice);
	
	
}
