package com.ssafy.snuggle.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.snuggle.model.dao.AddressDao;
import com.ssafy.snuggle.model.dto.Address;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao aDao;

	@Override
	public Address select(String userId) {
		return aDao.select(userId);
	}

	@Override
	public int update(Address address) {
		return aDao.update(address);
	}

}
