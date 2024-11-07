package com.enotes.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.CategoryDTO;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.exception.ResourceNotFound;
import com.enotes.repository.CategoryRepository;
import com.enotes.services.CategoryService;
import com.enotes.utils.Validation;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repo;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Validation validation;

	@Override
	public Boolean saveCategory(CategoryDTO categoryDto) {
		//Validation Checking
		validation.categoryValidation(categoryDto);	
		
		//check existing category
		
		Boolean exist = repo.existByName(categoryDto.getName().trim());
		
		Category category = mapper.map(categoryDto, Category.class);

		if (ObjectUtils.isEmpty(category.getId())) {
			category.setIsDeleted(false);
			//category.setCreatedBy(1);
			category.setCreatedOn(new Date());
		} else {
			updateCategory(category);
		}
		Category saveCategory = repo.save(category);
		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	private void updateCategory(Category category) {

		Optional<Category> findById = repo.findById(category.getId());
		if (findById.isPresent()) {
			Category existingCategory = findById.get();
			category.setCreatedBy(existingCategory.getCreatedBy());
			category.setCreatedOn(existingCategory.getCreatedOn());
			category.setIsDeleted(existingCategory.getIsDeleted());
			
//			category.setUpdatedBy(1);
//			category.setUpdatedOn(new Date());
	}
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
		List<Category> categories = repo.findAllByIsDeletedFalse();

		List<CategoryDTO> categoryDtoList = categories.stream().map(cat -> mapper.map(cat, CategoryDTO.class)).toList();
		return categoryDtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		List<Category> categories = repo.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> categoryList = categories.stream().map(cat -> mapper.map(cat, CategoryResponse.class))
				.toList();
		return categoryList;
	}

	@Override
	public CategoryDTO getCategoryDetailsById(Integer id) throws ResourceNotFound {
		Category findById = repo.findByIdAndIsDeletedFalse(id).orElseThrow(()-> 
		new ResourceNotFound("Category Not Found with id : " + id	));

		if (ObjectUtils.isEmpty(findById)) {
			return mapper.map(findById, CategoryDTO.class);
		}
		return null;
	}

	@Override
	public Boolean deleteCategoryDetailsById(Integer id) {
		Optional<Category> findById = repo.findById(id);

		if (findById.isPresent()) {
			Category category = findById.get();
			category.setIsDeleted(true);
			repo.save(category);
			return true;
		}
		return false;
	}

}
