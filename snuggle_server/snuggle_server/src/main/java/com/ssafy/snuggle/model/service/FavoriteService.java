package com.ssafy.snuggle.model.service;

import java.util.List;

import com.ssafy.snuggle.model.dto.Favorite;
import com.ssafy.snuggle.model.dto.Tagging;

public interface FavoriteService {

	 /**
     * Favorite를 등록한다.
     * @param Favorite
     */
    int addFavorite(Favorite favorite);
    

    /**
     * 사용자가 좋아요 한 favorite 조회
     * @param userId
     */
    List<Favorite> getUserFavorites(String userId);
    
    
    /**
     * 사용자가 좋아요 한 tagging 리스트 조회
     * @param userId
     */
    List<Tagging> getFavoriteTaggingList(String userId);
    
}
