package com.ssafy.snuggle.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.snuggle.model.dao.StampDao;
import com.ssafy.snuggle.model.dto.Stamp;
@Service
public class StampServiceImpl implements StampService{

	@Autowired
    StampDao sDao;
	
	@Override
	public List<Stamp> selectByUser(String sId) {
		return sDao.selectByUserId(sId);
	}

}
