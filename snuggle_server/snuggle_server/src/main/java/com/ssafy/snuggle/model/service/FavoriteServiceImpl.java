package com.ssafy.snuggle.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.snuggle.model.dao.FavoriteDao;
import com.ssafy.snuggle.model.dto.Favorite;
import com.ssafy.snuggle.model.dto.Tagging;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteDao fDao;

    @Override
    @Transactional
    public int addFavorite(Favorite favorite) {

        String userId = favorite.getUserId();
        String taggingId = favorite.getTaggingId();
        String isValid = favorite.getIsValid();
        int result = 0;

        System.out.println(isValid);
        // userId와 taggingId로 중복 확인 + 이미 Y인지
        boolean exists = fDao.isFavorite(userId, taggingId);
        if (exists) {
            // 이미 유효한 좋아요가 존재하면 제거
            fDao.delete(userId, taggingId);
            fDao.decreaseLikeCount(taggingId); // 삭제 시 likeCount -1

        } else {
            // 'isValid'가 'N'이면 'Y'로 설정하여 좋아요 추가
            if(isValid == null) {
                isValid = "N";
            }else if (isValid.equals("N")) {
                isValid = "Y"; // 'N'이면 'Y'로 변경
            }
            result = fDao.insert(userId, taggingId, isValid);
        }

        // 좋아요가 추가되면 Tagging의 likeCount 증가
        if (result > 0) {
            fDao.increaseLikeCount(taggingId); // Tagging의 likeCount 증가
        }
        
        fDao.updateLikeCount(taggingId);

        return result;
    }

    
    @Override
    public List<Tagging> getFavoriteTaggingList(String userId) {
        return fDao.selectByUser(userId);
    }


    @Override
    public List<Favorite> getUserFavorites(String userId) {
        return fDao.selectUserFavorites(userId);
    }
}
