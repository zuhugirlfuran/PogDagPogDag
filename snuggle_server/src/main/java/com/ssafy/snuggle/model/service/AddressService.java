package com.ssafy.snuggle.model.service;

import com.ssafy.snuggle.model.dto.Address;

public interface AddressService {

	/**
	 * 사용자 id로 주소 조회한다.
	 * 
	 * @param userId
	 */
	Address select(String userId);

	/**
	 * 사용자의 주소 수정 userId로 접근
	 * 
	 * @param userId
	 */
	public int update(Address address);
}
