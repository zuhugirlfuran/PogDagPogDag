package com.ssafy.snuggle.model.dao;

import com.ssafy.snuggle.model.dto.Favorite;

public interface FavoriteDao {

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
    
    
}
