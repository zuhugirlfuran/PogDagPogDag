package com.ssafy.snuggle.model.dao;

import java.util.List;

import com.ssafy.snuggle.model.dto.Favorite;
import com.ssafy.snuggle.model.dto.Tagging;

public interface TaggingDao {

	
    /**
     * NFC 태깅된 영상에 대한 정보를 단건 조회한다.
     * @param taggingId
     * @return
     */
    Tagging selectByTaggingId(String taggingId);
    
    /**
     * 책갈피된 영상 중 선택된 항목을 단건 조회한다.
     * @param favoriteId
     * @return
     */
    Favorite selectByFavoriteId(Integer favoriteId);

    /**
     * 사용자가 책갈피한 전체 영상 목록을 리턴한다. 
     * 
     * @param userId
     * @return
     */
    List<Favorite> selectByUser(String userId);
    
}
