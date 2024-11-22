package com.ssafy.snuggle.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.snuggle.model.dao.OrderDao;
import com.ssafy.snuggle.model.dao.OrderDetailDao;
import com.ssafy.snuggle.model.dao.StampDao;
import com.ssafy.snuggle.model.dao.UserDao;
import com.ssafy.snuggle.model.dto.Order;
import com.ssafy.snuggle.model.dto.OrderDetail;
import com.ssafy.snuggle.model.dto.OrderInfo;
import com.ssafy.snuggle.model.dto.Stamp;
import com.ssafy.snuggle.model.dto.User;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDao oDao;

	@Autowired
	OrderDetailDao dDao;

	@Autowired
	StampDao sDao;

	@Autowired
	UserDao uDao;

	@Transactional
	@Override
	public void makeOrder(Order order) {
		// Order 추가
		int result = oDao.insert(order);

		if (result != -1) {
			// OrderDetail 추가
			int quantity = 0;
			for (OrderDetail detail : order.getDetails()) {
				detail.setOrderId(order.getOrderId());
				dDao.insert(detail);
				quantity += detail.getQuantity();
			}

			// stamp에 추가
			sDao.insert(new Stamp(order.getUserId(), order.getOrderId(), quantity));

			User user = uDao.selectById(order.getUserId());
			user.setStamps(user.getStamps() + quantity);

			// user 수정
			uDao.updateStamp(user);
		}

	}

	@Override
	public List<Order> getOrderByUser(String userId) {
		return oDao.selectByUser(userId);
	}

	@Override
	public void updateOrder(Order order) {
		
		oDao.update(order);
	}

	@Override
	public OrderInfo getOrderWithInfo(int order_id) {
		
		return oDao.selectOrderInfo(order_id);
	}

	@Override
	public Order getOrderWithDetails(int orderId) {
		// TODO Auto-generated method stub
		return null;
	}

}
