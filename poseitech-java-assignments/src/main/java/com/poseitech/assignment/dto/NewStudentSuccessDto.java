package com.poseitech.assignment.dto;

import org.apache.commons.beanutils.PropertyUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NewStudentSuccessDto extends StudentDto {

	private String baseUrl;

	public NewStudentSuccessDto(StudentDto stuDto, String baseUrl) {
		super();

		try {
			PropertyUtils.copyProperties(this, stuDto);
			this.setBaseUrl(baseUrl);
		} catch (Exception e) {
			throw new RuntimeException("copy properties StudentDto to NewStudentSuccessDto failed", e);
		}
	}

	public String getUrl() {
		return String.format(baseUrl + "/assignments/api/v1/students/%s/", this.getId());
	}

	@JsonIgnore
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

}
