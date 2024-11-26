package com.ssafy.snuggle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.snuggle.model.dto.Favorite;
import com.ssafy.snuggle.model.dto.Tagging;
import com.ssafy.snuggle.model.service.FavoriteService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin("*")
@RequestMapping("/snuggle/favorite")
public class FavoriteController {

	@Autowired
	private FavoriteService fService;

	@PostMapping("/insert")
	@Operation(summary = "favorite을 추가한다.", description = "userId와 taggingId가 동일한 favorite 객체가 이미 존재하면 해당 좋아요를 제거한다. (이때 결과값은 false), "
			+ "없으면 새로운 좋아요를 추가한다 (이때 결과값은 true). <br>"
			+ "좋아요 추가하면 tagging의 likeCount + 1, 제거 시 tagging의 likeCount - 1 된다.")
	public Boolean insertFavorite(@RequestBody Favorite favorite) {

		int result = 0;
		result = fService.addFavorite(favorite);
		return result == 1;

	}
	
	@GetMapping("/{userId}")
	@Operation(summary = "userId로 user가 좋아요 누른 favorite 리스트 조회한다.")
	public List<Favorite> getUserFavorites(@PathVariable String userId) {
		return fService.getUserFavorites(userId);
	}

	@GetMapping("/info")
	@Operation(summary = "userId로 user가 Tagging 데이터에서 좋아요 누른 favorite 리스트 조회한다.")
	public List<Tagging> getFavoriteTaggingList(String userId) {
		return fService.getFavoriteTaggingList(userId);
	}

}
