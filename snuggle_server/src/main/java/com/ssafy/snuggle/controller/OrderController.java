package com.ssafy.snuggle.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.snuggle.model.dto.Order;
import com.ssafy.snuggle.model.dto.OrderInfo;
import com.ssafy.snuggle.model.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/snuggle/order")
@CrossOrigin("*")
public class OrderController {
	@Autowired
	private OrderService oService;

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@PostMapping
	@Operation(summary = "order 객체를 저장하고 추가된 Order의 id를 반환한다.")
	public int getOrder(@RequestBody Order order) {

		logger.debug("makeOrder", order);
		if (order.getDetails().size() <= 0) {
			return -1;
		} else {
			oService.makeOrder(order);
			return order.getOrderId();
		}
	}

	@GetMapping("/{orderId}")
	@Operation(summary = "{orderId}에 해당하는 주문의 상세 내역을 목록 형태로 반환한다." + "이 정보는 사용자 정보 화면의 주문 내역 조회에서 사용된다.")
	public OrderInfo getOrderDetail(@PathVariable Integer orderId) {
		return oService.getOrderWithInfo(orderId);
	}
	

}
