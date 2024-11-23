package com.ssafy.snuggle.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.snuggle.model.dto.Category;
import com.ssafy.snuggle.model.dto.Product;
import com.ssafy.snuggle.model.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/snuggle/category")
@CrossOrigin("*")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/{cId}")
	@Operation(summary = "Category ID에 해당하는 카테고리 이름 조회")
	public ResponseEntity<?> selectCategory(@PathVariable int cId) {
		
		String categoryName = "";
		Category category = categoryService.select(cId);
		if (category != null) {
			categoryName = category.getCategoryName();
		}
		
		if (categoryName.isEmpty()) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(categoryName, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/{cId}/products")
	@Operation(summary = "Category ID에 해당하는 상품 리스트 조회")
	public ResponseEntity<?> selectProductsByCategory(@PathVariable int cId) {
		
		List<Product> productList = new ArrayList<>();
		productList = categoryService.selectProductsByCategory(cId);
		
		if (productList == null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}
	
	@GetMapping()
	@Operation(summary = "모든 Category 조회")
	public ResponseEntity<?> getAllCategory() {
		
		List<Category> categoryList = new ArrayList<>();
		categoryList = categoryService.selectAll();
		if (categoryList == null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}

}
