package com.ssafy.snuggle.controller;

import java.util.List;

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
		boolean result = false;
		logger.debug("order : {}", order);
		oService.makeOrder(order);
		int id = order.getOrderId();

		if (result) {
			return id;
		}
		return -1;
	}

}
