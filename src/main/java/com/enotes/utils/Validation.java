package com.enotes.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import com.enotes.dto.CategoryDTO;
import com.enotes.exception.ValidationException;

@Component
public class Validation {

	public void categoryValidation(CategoryDTO categoryDTO) {

		Map<String, Object> error = new LinkedHashMap<>();

		if (ObjectUtils.isEmpty(categoryDTO)) {
			throw new IllegalArgumentException("Category Object/JSON should not be null or empty");
		} else {
			// validation name field
			if (ObjectUtils.isEmpty(categoryDTO.getName())) {
				error.put("Name", "Name filed should not be empty or null");
			} else {
				if (categoryDTO.getName().length() < 10 && categoryDTO.getName().length() > 100) {
					error.put("name", "name length should be minimum 10 and maximum 100");
				}
			}

			// Validation description
			if (ObjectUtils.isEmpty(categoryDTO.getDescription())) {
				error.put("Description", "Description filed should not be empty or null");
			}

			// Validation isActive

			if (ObjectUtils.isEmpty(categoryDTO.getIsActive())) {
				error.put("IsActive", "IsActive filed should not be empty or null");
			} else {
				if (categoryDTO.getIsActive() != Boolean.TRUE && categoryDTO.getIsActive() != Boolean.FALSE) {
					error.put("IsActive", "Invalid value in IsActive Field !!");
				}
			}
			
			if(!error.isEmpty()) {
				throw new ValidationException(error);
			}
		}

	}
}
