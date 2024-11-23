package com.ssafy.snuggle.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<Product>> getProductList() {
		return new ResponseEntity<List<Product>>(productService.getProductList(), HttpStatus.OK);
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

		map.put("productName", product.getProductName());
		map.put("price", product.getPrice());
		map.put("content", product.getContent());
		map.put("img", product.getImg());
		map.put("likeCount", product.getLikeCount());
		map.put("comments", selectByProduct);

		return map;
	}

	@GetMapping("/bestProduct")
	@Operation(summary = "best Product를 조회한다. 상품 likeCount가 많은 순서대로 5개만 보여준다.")
	public ResponseEntity<List<Product>> getBestProduct() {

		return new ResponseEntity<List<Product>>(productService.getBestProduct(), HttpStatus.OK);
	}

	@GetMapping("/newProduct")
	@Operation(summary = "New Product를 조회한다. 새로 등록된 상품 5개만 보여준다.")
	public ResponseEntity<List<Product>> getNewProduct() {

		return new ResponseEntity<List<Product>>(productService.getNewProduct(), HttpStatus.OK);
	}


	@GetMapping("/sortDesc")
	@Operation(summary = "낮은 가격순으로 정렬해 조회한다.")
	public ResponseEntity<List<Product>> getSortDesc() {

		return new ResponseEntity<List<Product>>(productService.getSortDesc(), HttpStatus.OK);
	}

	@GetMapping("/sortAsc")
	@Operation(summary = "높은 가격순으로 정렬해 조회한다.")
	public ResponseEntity<List<Product>> getSortAsc() {

		return new ResponseEntity<List<Product>>(productService.getSortAsc(), HttpStatus.OK);
	}

}