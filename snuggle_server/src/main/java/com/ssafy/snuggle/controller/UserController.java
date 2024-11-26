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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.snuggle.model.dto.Order;
import com.ssafy.snuggle.model.dto.Stamp;
import com.ssafy.snuggle.model.dto.User;
import com.ssafy.snuggle.model.service.OrderService;
import com.ssafy.snuggle.model.service.StampService;
import com.ssafy.snuggle.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
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
    
    @Autowired
    StampService sService;

    // 회원 가입
    @PostMapping
    @Operation(summary = "사용자 정보를 추가한다. 성공하면 true 리턴")
    public Boolean insert(@RequestBody User user) {

        logger.info("Received User data: {}", user);
        int result = 0;

        try {
            result = uService.join(user);
        } catch (Exception e) {
        	logger.error("Error occurred while inserting user: {}", e.getMessage(), e);
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
    @Operation(summary = "로그인 처리 후 성공적으로 로그인 되었다면 loginId라는 쿠키를 내려보낸다.", description = "<pre>userId와 password 두개만 넘겨도 정상동작한다. \n 아래는 id, pass만 입력한 샘플코드\n"
            + "{\r\n" + "  \"userId\": \"aa12\",\r\n" + "  \"password\": \"aa12\"\r\n" + "}" + "</pre>")
    public User login(@RequestBody User user, HttpServletResponse response) throws UnsupportedEncodingException {
        User selected = uService.login(user.getUserId(), user.getPassword());
        if (selected != null) {
            Cookie cookie = new Cookie("loginId", URLEncoder.encode(selected.getUserId(), "utf-8"));

            cookie.setMaxAge(60 * 60 * 24 * 30); // 30일
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return selected;
    }

    // 위에 꺼 대신해서 이걸 만들었다.
    // password를 sharedpreference에 저장하면 안되니, id만 받는데,
    // 이 id와 쿠키에 있는 id가 같은지 확인해서 로그인 사용자를 조회해서 리턴함.
    @GetMapping("/info")
    @Operation(summary = "사용자의 정보와 함께 사용자의 주문 내역, 사용자 등급 정보를 반환한다.")
    public Map<String, Object> getInfo(HttpServletRequest request, String userId) {
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

        User selected = uService.selectUser(userId);

        if (!userId.equals(idInCookie)) {
            logger.info("different cookie value : inputValue : {}, inCookie:{}", userId, idInCookie);
            selected = null; // 사용자 정보 삭제.
        } else {
            logger.info("valid cookie value : inputValue : {}, inCookie:{}", userId, idInCookie);
        }

        if (selected == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("user", new User());
            map.put("totalStamps", 0);
            return map;
        } else {
            Map<String, Object> info = new HashMap<>();
            info.put("user", selected);
            
            List<Order> orders = oService.getOrderByUser(selected.getUserId());
            info.put("order", orders);
            
            // **스탬프 데이터 추가
            List<Stamp> stamps = sService.selectByUser(selected.getUserId());
            logger.info("조회된 스탬프 데이터: {}", stamps);

            int totalStamps = stamps.stream().mapToInt(Stamp::getQuantity).sum();
            logger.info("스탬프 총 개수: {}", totalStamps);

            selected.setStamps(totalStamps);
           
            
            return info;
        }
    }

    @PostMapping("/info")
    @Operation(summary = "사용자의 정보와 함께 사용자의 주문 내역, 사용자 등급 정보를 반환한다.", description = "아래 User객체에서 id, pass 두개의 정보만 json으로 넘기면 정상동작한다.")
    public Map<String, Object> getInfo(@RequestBody User user) {
        User selected = uService.login(user.getUserId(), user.getPassword());
        if (selected == null) {
            return null;
        } else {
            Map<String, Object> info = new HashMap<>();
            info.put("user", selected);
            List<Order> orders = oService.getOrderByUser(user.getUserId());
            info.put("order", orders);
            // info.put("grade", getGrade(selected.getStamps()));
            return info;
        }
    }

}
