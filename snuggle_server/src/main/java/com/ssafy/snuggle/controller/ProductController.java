package com.ssafy.snuggle.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.snuggle.model.dto.CommentInfo;
import com.ssafy.snuggle.model.dto.Product;
import com.ssafy.snuggle.model.dto.ProductWithComment;
import com.ssafy.snuggle.model.service.CommentService;
import com.ssafy.snuggle.model.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/snuggle/product")
@CrossOrigin("*")
public class ProductController {
//Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CommentService commentService;

	// 상품 목록
	@GetMapping()
	@Operation(summary = "전체 상품의 목록을 반환한다.") // swagger
	public List<Product> getList() {

		return productService.getProductList();

	}

	// productId에 해당하는 상품 정보를 comment와 함께 반환
	@GetMapping("/{productId}")
	@Operation(summary = "{productId}에 해당하는 상품 정보를 comment와 함께 반환한다. 이 기능은 상품의 comment를 조회할 때 사용된다.")
	public Map<String, Object> getComment(@PathVariable Integer productId) {

		Map<String, Object> map = new HashMap<>();
		// comment정보
		List<CommentInfo> selectByProduct = commentService.selectByProduct(productId);
		
		// product 정보
		ProductWithComment product = productService.selectWithComment(productId);
		
		
		if (product == null) {
			throw new RuntimeException("상품을 찾을 수 없습니다. productId: " + productId);
		}

		// logger.debug("product 정보 : {}", product.getImg());
		map.put("name", product.getProductName());
		map.put("price", product.getPrice());
//		map.put("totalSells", product.getTotalSells());
//		map.put("commentCount", product.getCommentCount());
//		map.put("averageStars", product.getAverageStars());
		map.put("img", product.getImg());
		map.put("comments", selectByProduct);

		return map;
	}

}