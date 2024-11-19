package com.ssafy.snuggle.model.dao;

import java.util.List;

import com.ssafy.snuggle.model.dto.Favorite;

public interface TaggingDao {

	/**
     * favorite 테이블에 정보를 추가한다. 
     * auto_inctement된 id를 parameter로 전달되는 Favorite객체에 입력해야 한다.
     * 리턴되는 int는 JDBC에서 default로 리턴하는 값인 입력 건수이다. 
     *   
     * @param favorite
     * @return
     */
    int insert(Favorite favorite);
    
    /**
     * 파라미터로 전달받는 Favorite 객체에 들어있는 값 중에서, 
     * 책갈피 체크한 칼럼만 (is_valid)만 update한다. 
     * 리턴되는 int는 JDBC에서 default로 리턴하는 값인 수정건수이다. 
     * 
     * @param favorite
     * @return
     */
    int update(Favorite favorite);
    
    /**
     * 영상 정보를 단건 조회한다.
     * 책갈피하지 않은 영상 조회는 어떻게 하죠?
     * @param userId
     * @return
     */
    Favorite selectById(Integer favoriteId);

    /**
     * 사용자가 책갈피한 전체 영상 목록을 리턴한다. 
     * 
     * @param userId
     * @return
     */
    List<Favorite> selectByUser(String userId);
    
}
