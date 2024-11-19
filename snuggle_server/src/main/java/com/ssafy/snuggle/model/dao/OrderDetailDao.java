package com.ssafy.snuggle.model.dao;

import java.util.List;

import com.ssafy.snuggle.model.dto.OrderDetail;
import com.ssafy.snuggle.model.dto.OrderDetailInfo;



public interface OrderDetailDao {
    /**
     * 주문상세정보를 입력한다. (make order에서)
     * 
     * @param detail
     * @return
     */
    int insert(OrderDetail detail);
    
    OrderDetailInfo select(Integer id);
}
