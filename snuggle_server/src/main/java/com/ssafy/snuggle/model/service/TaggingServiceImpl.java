package com.ssafy.snuggle.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.snuggle.model.dao.TaggingDao;
import com.ssafy.snuggle.model.dto.Tagging;

@Service
public class TaggingServiceImpl implements TaggingService {

	@Autowired
	TaggingDao tDao;
	
	@Override
	public Tagging selectByTaggingId(String taggingId) {
		return tDao.selectByTaggingId(taggingId);
	}

}
