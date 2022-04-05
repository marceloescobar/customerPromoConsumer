package com.mescobar.promo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mescobar.promo.dto.CreatePromotionalCodeRequest;
import com.mescobar.promo.service.PromotionalCodeService;
import org.springframework.http.HttpStatus;

@RestController
public class PromotionalCodeController {
	
	@Autowired
	private PromotionalCodeService promotionalCodeService;

	  @PostMapping("/v1/promotional-code")
	    @ResponseStatus(HttpStatus.CREATED)
	    public void createPromotionalCode(@RequestBody CreatePromotionalCodeRequest createPromotionalCodeRequest) {
	        promotionalCodeService.createPromotionalCode(createPromotionalCodeRequest);
	    }

	    @PostMapping("/v2/promotional-code")
	    @ResponseStatus(HttpStatus.CREATED)
	    public void createPromotionalCodeGettingCustomersWithTotalPagesHeader(@RequestBody CreatePromotionalCodeRequest createPromotionalCodeRequest) {
	        promotionalCodeService.createPromotionalCodeByGettingCustomersWithTotalPagesHeader(createPromotionalCodeRequest);
	    }
}
