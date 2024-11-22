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

import com.ssafy.snuggle.model.dto.Like;
import com.ssafy.snuggle.model.dto.Product;
import com.ssafy.snuggle.model.service.LikeService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin("*")
@RequestMapping("/snuggle/like")
public class LikeController {

	@Autowired
	private LikeService lService;

	@PostMapping("/insert")
	@Operation(summary = "like을 추가한다.", description = "userId와 productId가 동일한 like 객체가 이미 존재하면 해당 좋아요를 제거한다. (이때 결과값은 false), "
			+ "없으면 새로운 좋아요를 추가한다 (이때 결과값은 true). <br>"
			+ "좋아요 추가하면 product의 likeCount + 1, 제거 시 product의 likeCount - 1 된다.")
	public Boolean insertLike(@RequestBody Like like) {

		int result = 0;
		result = lService.addLike(like);
		return result == 1;

	}

	@GetMapping("/info/{userId}")
	@Operation(summary = "userid로 user가 좋아요 누른 like 리스트 조회한다.")
	public List<Product> getLikeProductList(@PathVariable String userId) {
		return lService.getLikeProductList(userId);
	}

}
