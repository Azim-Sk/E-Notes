package com.enotes.services;

import java.util.List;

import com.enotes.dto.CategoryDTO;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.exception.ResourceNotFound;

public interface CategoryService {
	
	public Boolean saveCategory(CategoryDTO categoryDto);
	
	public List<CategoryDTO> getAllCategory();

	public List<CategoryResponse> getActiveCategory();

	public CategoryDTO getCategoryDetailsById(Integer id) throws ResourceNotFound;

	public Boolean deleteCategoryDetailsById(Integer id);
	 
}
