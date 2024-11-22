package com.ssafy.snuggle.model.dao;

import com.ssafy.snuggle.model.dto.Address;

public interface AddressDao {

	/**
	 * 사용자 id에 따른 주소 조회
	 * 
	 * @param userId
	 * @return
	 */
	Address select(String userId);

	/**
	 * 사용자 id의 주소 수정
	 * 
	 * @param address
	 * @return
	 */
	int update(Address address);
}
