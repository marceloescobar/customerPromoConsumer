package com.mescobar.promo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class CustomerClientPageableResponse extends PageImpl<CustomerClientResponse>{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerClientPageableResponse(@JsonProperty("content") List<CustomerClientResponse> content,
	            @JsonProperty("number") int number, @JsonProperty("size") int size,
	            @JsonProperty("totalElements") Long totalElements) {
	        super(content, PageRequest.of(number, size), totalElements);
	    }
}
