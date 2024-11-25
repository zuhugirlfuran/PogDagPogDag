package com.ssafy.snuggle.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.snuggle.model.dao.FavoriteDao;
import com.ssafy.snuggle.model.dto.Favorite;
import com.ssafy.snuggle.model.dto.Tagging;

@Service // Spring Bean 등록
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteDao fDao; // DAO 클래스 주입

    @Override
    @Transactional
    public int addFavorite(Favorite favorite) {
        String userId = favorite.getUserId();
        String taggingId = favorite.getTaggingId();
        String isValid = favorite.getIsValid() != null ? favorite.getIsValid() : "N";

        boolean exists = fDao.isFavorite(userId, taggingId); // 이미 존재 여부 확인
        int result = 0;

        if (exists) {
            fDao.delete(userId, taggingId);
            fDao.decreaseLikeCount(taggingId); // 좋아요 제거 시 카운트 감소
        } else {
            if ("N".equals(isValid)) {
                isValid = "Y";
            }
            result = fDao.insert(userId, taggingId, isValid);
            fDao.increaseLikeCount(taggingId); // 좋아요 추가 시 카운트 증가
        }

        fDao.updateLikeCount(taggingId); // 최종 카운트 업데이트
        return result;
    }

    @Override
    public List<Tagging> getFavoriteTaggingList(String userId) {
        return fDao.selectByUser(userId); // 좋아요 리스트 반환
    }
}
