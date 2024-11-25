package com.ssafy.snuggle.model.dao;

import java.util.List;

import com.ssafy.snuggle.model.dto.Stamp;

public interface StampDao {
	/**
     * stamp정보를 입력한다.(make order에서)
     * order detail에는 주문의 상세 내역이 들어가고, 
     * 이 테이블에는 해당 주문번호로 총 몇건이 주문되어 몇개의 stamp가 적립되었는지 기록된다. 
     * 
     * @param stamp
     * @return
     */
    int insert(Stamp stamp);
    
    /**
     * id 사용자의 Stamp 이력을 반환한다.
     * @param userId
     * @return
     */
    List<Stamp> selectByUserId(String userId);
    
    /**
     * 주어진 orderId에 대한 총 주문량을 반환한다.
     * @return 총 주문량
     */
    int selectTotalQuantityAll();
}
