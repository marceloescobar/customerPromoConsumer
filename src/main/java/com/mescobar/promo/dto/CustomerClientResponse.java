package com.mescobar.promo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerClientResponse {

	private Integer id;

	private LocalDate createdAt;

	private String gender;

	private String phoneNumber;
}
