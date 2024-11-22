package com.ssafy.snuggle.model.service;

import com.ssafy.snuggle.model.dto.Tagging;

public interface TaggingService {

	 /**
     * NFC 태깅된 영상에 대한 정보를 단건 조회한다.
     * @param taggingId
     * @return
     */
    Tagging selectByTaggingId(String taggingId);
    
}
