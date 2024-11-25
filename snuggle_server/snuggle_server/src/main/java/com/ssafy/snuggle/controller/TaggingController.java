package com.ssafy.snuggle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.snuggle.model.dto.Tagging;
import com.ssafy.snuggle.model.service.TaggingService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/snuggle/tagging")
@CrossOrigin("*")
public class TaggingController {
	
	@Autowired
	private TaggingService tService;

	@GetMapping("/{tagId}")
	@Operation(summary = "tagging id로 태깅한 데이터 가져온다.")
	public ResponseEntity<?> getTagging(@PathVariable String tagId) {
		Tagging tagging =  tService.selectByTaggingId(tagId);
		
		if (tagging == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(tagging);
	}
	
}
