package com.ssafy.snuggle.model.dao;

import java.util.List;

import com.ssafy.snuggle.model.dto.Favorite;
import com.ssafy.snuggle.model.dto.Tagging;
import com.ssafy.snuggle.model.dto.User;

public interface FavoriteDao {

	/**
     * favorite 테이블에 정보를 추가한다. 
     * 좋아요를 누르면 해당 userId와 tagging_id를 받아 t_favorite에 추가한다.
     *   
     * @param favorite
     * @return
     */
    int insert(String userId, String taggingId, String isValid);
    
    
    /**
     * userId와 taggingId로 중복 체크 (이미 유효한 좋아요가 있는지 확인)
     * @param userId, taggingId
     * @return
     */
    boolean isFavorite(String userId, String taggingId);

    /**
     * favorite 삭제
     * @param userId, taggingId
     * @return
     */
    void delete(String userId, String taggingId);
    
    /**
     * 사용자가 책갈피한 전체 영상 목록(Tagging 목록)을 리턴한다. 
     * 
     * @param userId
     * @return
     */
    List<Tagging> selectByUser(String userId);
    
    /**
     * Tagging의 likeCount 증가
     * @param taggingId
     * @return
     */
    void increaseLikeCount(String taggingId);
    
    /**
     * Tagging의 likeCount 감소
     * @param taggingId
     * @return
     */
    void decreaseLikeCount(String taggingId);
    
    /**
     * 책갈피된 영상 중 선택된 항목을 단건 조회한다.
     * @param favoriteId
     * @return
     */
    Favorite selectByFavoriteId(Integer favoriteId);

   
    
}
