package com.mescobar.promo.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.mescobar.promo.dto.CustomerClientPageableResponse;
import com.mescobar.promo.dto.CustomerClientResponse;

@Component
public class CustomerServiceClient {

	@Value("${external.service.customer.get-customers-url}")
	private String externalServiceCustomerGetCustomersUrl;

	@Value("${external.service.customer.get-customers-url-with-header}")
	private String externalServiceCustomerGetCustomersUrlWithHeader;

	

	public CustomerClientPageableResponse getCustomers(Integer page, Integer size) {
		return WebClient.create().get()
				.uri(builder -> builder.path(externalServiceCustomerGetCustomersUrl).queryParam("page", page)
						.queryParam("size", size).build())
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).retrieve()
				.bodyToMono(CustomerClientPageableResponse.class).block();

	}

	public ResponseEntity<List<CustomerClientResponse>> getCustomerListWithTotalPagesHeader(Integer page,
			Integer size) {
		return WebClient.create().get()
				.uri(builder -> builder.path(externalServiceCustomerGetCustomersUrlWithHeader).queryParam("page", page)
						.queryParam("size", size).build())
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).retrieve()
				.toEntity(new ParameterizedTypeReference<List<CustomerClientResponse>>() {
				}).block();
	}
}
