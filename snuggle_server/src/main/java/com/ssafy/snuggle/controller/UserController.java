package com.ssafy.snuggle.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.snuggle.model.dto.Order;
import com.ssafy.snuggle.model.dto.User;
import com.ssafy.snuggle.model.service.OrderService;
import com.ssafy.snuggle.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/snuggle/user")
@CrossOrigin("*")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService uService;

	@Autowired
	OrderService oService;

	// 회원 가입
	@PostMapping
	@Operation(summary = "사용자 정보를 추가한다. 성공하면 true 리턴")
	public Boolean insert(@RequestBody User user) {

		int result = 0;

		try {
			result = uService.join(user);
		} catch (Exception e) {
			result = -1;
		}

		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

	@GetMapping("/isUsed")
	@Operation(summary = "request parameter로 전달된 id가 이미 사용중인지 반환한다.")
	public Boolean isUsedId(String userId) {
		return uService.isUsedId(userId);
	}

	@PostMapping("/login")
	@Operation(summary = "로그인 처리 후 성공적으로 로그인 되었다면 loginId라는 쿠키를 내려보낸다.")
	public User login(@RequestBody User user, HttpServletResponse response) throws UnsupportedEncodingException {
		User selected = uService.login(user.getEmail(), user.getPassword());
		if (selected != null) {
			Cookie cookie = new Cookie("loginId", URLEncoder.encode(selected.getEmail(), "utf-8"));

			cookie.setMaxAge(60 * 60 * 24 * 30); // 30일
			response.addCookie(cookie);
		}
		return selected;
	}

	// 위에 꺼 대신해서 이걸 만들었다.
	// password를 sharedpreference에 저장하면 안되니, id만 받는데,
	// 이 id와 쿠키에 있는 id가 같은지 확인해서 로그인 사용자를 조회해서 리턴함.
	@GetMapping("/info")
	@Operation(summary = "사용자의 정보와 함께 사용자의 주문 내역, 사용자 등급 정보를 반환한다.")
	public Map<String, Object> getInfo(HttpServletRequest request, String id) {
		String idInCookie = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				try {
					if ("loginId".equals(cookie.getName())) {
						idInCookie = URLDecoder.decode(cookie.getValue(), "utf-8");
						System.out.println("value : " + URLDecoder.decode(cookie.getValue(), "utf-8"));
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		User selected = uService.selectUser(id);

		if (!id.equals(idInCookie)) {
			logger.info("different cookie value : inputValue : {}, inCookie:{}", id, idInCookie);
			selected = null; // 사용자 정보 삭제.
		} else {
			logger.info("valid cookie value : inputValue : {}, inCookie:{}", id, idInCookie);
		}

		if (selected == null) {
			Map<String, Object> map = new HashMap<>();
			map.put("user", new User());
			return map;
		} else {
			Map<String, Object> info = new HashMap<>();
			info.put("userW", selected);
			List<Order> orders = oService.getOrderByUser(selected.getEmail());
			info.put("order", orders);
			return info;
		}
	}

}
