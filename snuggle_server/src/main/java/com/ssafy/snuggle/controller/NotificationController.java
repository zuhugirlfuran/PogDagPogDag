package com.ssafy.snuggle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.snuggle.model.dto.Notification;
import com.ssafy.snuggle.model.service.NotificationService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin("*")
@RequestMapping("/snuggle/notification")
public class NotificationController {

	
	@Autowired
	private NotificationService noticeService;
	
	@GetMapping("")
	@Operation(summary = "사용자 id에 따른 알림 정보 조회")
	public ResponseEntity<?> getNotificationByUserId(String userId) {
		List<Notification> noti = noticeService.getNotificationByUserId(userId);
		
		if (noti != null) {
			return new ResponseEntity<List<Notification>>(noti, HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("")
	@Operation(summary = "알림판에 알림 데이터를 저장한다.")
	public ResponseEntity<?> addNotification(@RequestBody Notification notice) {
		int result = noticeService.addNotification(notice);
		
		if (result > 0) {
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	} 
	
}
