package com.ssafy.snuggle.model.service;

import com.ssafy.snuggle.model.dto.User;

public interface UserService {

	/**
	 * 사용자 정보를 DB에 저장하고 저장된 건수를 리턴한다.
	 * 
	 * @param user
	 */
	public int join(User user);

	/**
	 * id, pass에 해당하는 User 정보를 반환한다.
	 * 
	 * @param user_id
	 * @param password
	 * @return 조회된 User 정보를 반환한다.
	 */
	public User login(String user_id, String password);

	/**
	 * 해당 아이디가 이미 사용 중인지를 반환한다.
	 * 
	 * @param user_id
	 * @return
	 */
	public boolean isUsedId(String user_id);

	/**
	 * id 에 해당하는 User 정보를 반환한다.
	 * 
	 * @param user_id
	 * @return 조회된 User 정보를 반환한다.
	 */
	public User selectUser(String user_id);

}