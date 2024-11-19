package com.ssafy.snuggle.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.snuggle.model.dao.CommentDao;
import com.ssafy.snuggle.model.dto.Comment;
import com.ssafy.snuggle.model.dto.CommentInfo;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;

	@Override
	@Transactional
	public int addComment(Comment comment) {
		return commentDao.insert(comment);
	}

	@Override
	public Comment selectComment(int commentId) {
		return commentDao.select(commentId);
	}

	@Override
	@Transactional
	public int removeComment(int commentId) {
		return commentDao.delete(commentId);
	}

	@Override
	@Transactional
	public int updateComment(Comment comment) {
		return commentDao.update(comment);
	}

	@Override
	public List<CommentInfo> selectByProduct(int productId) {
		return commentDao.selectByProduct(productId);
	}

}
