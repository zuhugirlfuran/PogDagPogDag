package com.ssafy.snuggle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.snuggle.model.dto.Comment;
import com.ssafy.snuggle.model.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/snuggle/comment")
@CrossOrigin("*")
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	@PutMapping()
	@Operation(summary = "Comment 객체를 수정한다. 성공하면 comment_id를 반환한다.")
	public ResponseEntity<?> updateComment(@RequestBody Comment comment) {

		int result = -1;
		System.out.println(comment.getComment());
		result = commentService.updateComment(comment);
		if (result == 1) {
			return new ResponseEntity<Integer>(comment.getcId(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

	}
	
	@PostMapping()
	@Operation(summary = "Comment 객체를 추가한다. 성공하면 생성된 댓글 ID(commentId)를 리턴한다.")
	public ResponseEntity<?> addComment(@RequestBody Comment comment) {

		int result = commentService.addComment(comment);
		if (result == 1) {
			return new ResponseEntity<Integer>(comment.getcId(), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	@DeleteMapping("/{comment_id}")
	@Operation(summary = "{comment_id}에 해당하는 사용자 정보를 삭제한다. 성공하면 true를 리턴한다.")
	public ResponseEntity<?> deleteComment(@PathVariable int comment_id) {

		int result = commentService.removeComment(comment_id);
		if (result == 1) {
			return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
}
