package com.ssafy.snuggle.model.service;

import java.util.List;

import com.ssafy.snuggle.model.dto.Order;
import com.ssafy.snuggle.model.dto.OrderDetail;
import com.ssafy.snuggle.model.dto.OrderDetailInfo;
import com.ssafy.snuggle.model.dto.OrderInfo;


public interface OrderService {
	/**
     * 새로운 Order를 생성한다.
     * Order와 OrderDetail에 정보를 추가한다.
     * [심화]User 테이블에 사용자의 Stamp 개수를 업데이트 한다.
     * [심화]Stamp 테이블에 Stamp 이력을 추가한다.
     * @param order
     */
    public void makeOrder(Order order);
    
    /**
     * orderId에 대한 Order를 반환한다.
     * 이때 Order에 해당하는 OrderDetail에 대한 내용까지 반환한다.
     * OrderDetail의 내용은 id에 대한 내림차순으로 조회한다.
     * 
     * 3 단계에서는 selectOrderTotalInfo를 사용하고, 이 메소드를 사용하지 않음. 
     * @param orderId
     * @return
     */
    public List<OrderDetail> getOrderWithDetails(int orderId);
    
    /**
     * id에 해당하는 사용자의 Order 목록을 주문 번호의 내림차순으로 반환한다.
     * 
     * @param id
     * @return
     */
    public List<Order> getOrderByUser(String userId);
    
    /**
     * 주문 정보를 수정한다. - 주문의 상태만 변경된다.
     * @param order
     */
    public void updateOrder(Order order);
    
    /**
     * orderId에 대한 Order를 반환한다.
     * 이때 Order에 해당하는 OrderDetail에 대한 내용까지 반환한다.
     * OrderDetail의 내용은 detail id의 오름차순으로 조회한다.
     * 토탈금액, 상품명 등의 추가적인 정보가 담긴 OrderWithInfo객체를 리턴한다. 
     * @param orderId
     * @return
     */
    public OrderInfo getOrderWithInfo(int orderId);
    
}