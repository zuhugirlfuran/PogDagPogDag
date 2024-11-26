package com.ssafy.snuggle.model.service;

import java.util.List;

import com.ssafy.snuggle.model.dto.Comment;
import com.ssafy.snuggle.model.dto.CommentInfo;

public interface CommentService {
    /**
     * Comment를 등록한다.
     * @param comment
     */
    int addComment(Comment comment);
    
    /**
     * commentId에 해당하는 comment를 반환한다.
     * @param commentId
     * @return
     */
    Comment selectComment(int cId);
    
    /**
     * commentId에 해당하는 Comment를 삭제한다.
     * @param commentId
     */
    int removeComment(int cId);
    
   /**
    * Comment를 수정한다. 수정 내용은 rating과 comment이다.
    * @param comment
    */
    int updateComment(Comment comment);
    
    /**
     * productId에 해당하는 Comment의 목록을 Comment id의 내림차순으로 반환한다.
     * @param productId
     * @return
     */
    List<CommentInfo> selectByProduct(int productId);
}
