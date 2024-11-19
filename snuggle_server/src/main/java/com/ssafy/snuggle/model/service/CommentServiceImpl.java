package com.ssafy.snuggle.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.snuggle.model.dao.CommentDao;
import com.ssafy.snuggle.model.dto.Comment;
import com.ssafy.snuggle.model.dto.CommentInfo;


@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;

	@Override
	public int addComment(Comment comment) {
		return commentDao.insert(comment);
	}

	@Override
	public Comment selectComment(Integer commentId) {
		return commentDao.select(commentId);
	}

	@Override
	public int removeComment(Integer commentId) {
		return commentDao.delete(commentId);
	}

	@Override
	public int updateComment(Comment comment) {
		return commentDao.update(comment);
	}

	@Override
	public List<CommentInfo> selectByProduct(Integer productId) {
		return commentDao.selectByProduct(productId);
	}

}
