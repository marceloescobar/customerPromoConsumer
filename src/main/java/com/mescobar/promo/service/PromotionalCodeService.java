package com.mescobar.promo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mescobar.promo.client.CustomerServiceClient;
import com.mescobar.promo.dto.CreatePromotionalCodeRequest;
import com.mescobar.promo.dto.CustomerClientPageableResponse;
import com.mescobar.promo.dto.CustomerClientResponse;

@Service
public class PromotionalCodeService {

	private static int LIMIT = 50;

	@Value("${message.sms}")
	private String promotionalSMS;

	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionalCodeService.class);

	@Autowired
	private CustomerServiceClient customerServiceClient;

	public void createPromotionalCode(CreatePromotionalCodeRequest createPromotionalCodeRequest) {
		LOGGER.info("Creating promotional code...");
		int currentPage = 0;

		List<CustomerClientResponse> customersResponseCompleteCollection = new ArrayList<>();
		CustomerClientPageableResponse customerClientPageableResponse;

		do {

			LOGGER.info("Getting customers from a page {} with a limit {}", currentPage, LIMIT);
			customerClientPageableResponse = customerServiceClient.getCustomers(currentPage++, LIMIT);

			List<CustomerClientResponse> customerClientResponseFromSinglePage = customerClientPageableResponse
					.getContent();

			if (!CollectionUtils.isEmpty(customerClientResponseFromSinglePage)) {
				customersResponseCompleteCollection.addAll(customerClientResponseFromSinglePage);
			}
		} while (customerClientPageableResponse.hasNext());

		LOGGER.info("Received all customers from total of {} page(s)", customerClientPageableResponse.getTotalPages());

		sendMessage(customersResponseCompleteCollection, createPromotionalCodeRequest);
	}
	
	 public void createPromotionalCodeByGettingCustomersWithTotalPagesHeader(CreatePromotionalCodeRequest createPromotionalCodeRequest) {
		 LOGGER.info("Creating promotional code by getting customers with total pages header...");

	        int currentPage = 0;
	        int limit = 50;
	        int totalPages;
	        List<CustomerClientResponse> customersResponseCompleteCollection = new ArrayList<>();
	        ResponseEntity<List<CustomerClientResponse>> responseEntity;
	        
	        do {
	        	
	        	LOGGER.info("Getting customers from a page {} with a limit {}", currentPage, limit);
	        	
	            responseEntity = customerServiceClient.getCustomerListWithTotalPagesHeader(currentPage++, limit);
	            List<CustomerClientResponse> customerClientResponseFromSinglePage = responseEntity.getBody();

	            if (!CollectionUtils.isEmpty(customerClientResponseFromSinglePage)) {
	                customersResponseCompleteCollection.addAll(customerClientResponseFromSinglePage);
	            }
	            
	            totalPages = responseEntity.getHeaders().get("total-pages").stream().findFirst()
	                    .map(Integer::parseInt).orElse(0);
	        } while (currentPage < totalPages);

	        LOGGER.info("Received all customers from total of {} page(s)", currentPage);
	        sendMessage(customersResponseCompleteCollection, createPromotionalCodeRequest);
	    }

	private void sendMessage(List<CustomerClientResponse> customersEligibleForDiscount,
			CreatePromotionalCodeRequest createPromotionalCodeRequest) {
		LOGGER.info("Sending messages...");

		customersEligibleForDiscount.stream()
				.filter(customer -> (LocalDate.now().getYear()
						- customer.getCreatedAt().getYear()) >= createPromotionalCodeRequest.getYearsOfLoyalty())
				.forEach(c -> System.out
						.println(String.format(promotionalSMS, LocalDate.now().getYear() - c.getCreatedAt().getYear(),
								createPromotionalCodeRequest.getPromoCode(),
								createPromotionalCodeRequest.getDiscountPercentage(), c.getPhoneNumber())));
	}

}
