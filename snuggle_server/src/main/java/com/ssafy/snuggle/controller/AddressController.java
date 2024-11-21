package com.ssafy.snuggle.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.snuggle.model.dto.Address;
import com.ssafy.snuggle.model.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/snuggle/address")
@CrossOrigin("*")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@GetMapping("/{userId}")
	@Operation(summary = "userId로 Address 객체를 조회한다.")
	public Address getAddress(@PathVariable String userId) {
		return addressService.select(userId);

	}

	@PutMapping
    @Operation(summary = "Address 객체를 사용하여 주소와 전화번호를 업데이트합니다.")
    public ResponseEntity<?> updateAddress(@RequestBody Address address) {
        int result = addressService.update(address);

        if (result > 0) {
            return ResponseEntity.ok("true");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("update failed");
        }
    }
}
