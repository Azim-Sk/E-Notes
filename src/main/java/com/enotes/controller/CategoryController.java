package com.enotes.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.enotes.dto.CategoryDTO;
import com.enotes.dto.CategoryResponse;
import com.enotes.exception.ResourceNotFound;
import com.enotes.services.CategoryService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

	@Autowired
	private CategoryService service;

	@PostMapping("/save-Category")
	public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDTO categoryDto) {
		Boolean saveCategory = service.saveCategory(categoryDto);

		if (saveCategory) {
			return new ResponseEntity<>("Saved Successfully", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Category Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/categories")
	public ResponseEntity<?> getAllCategory() {
		List<CategoryDTO> allCategory = service.getAllCategory();

		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}
	}

	@GetMapping("/active-categories")
	public ResponseEntity<?> getActiveCategory() {
		List<CategoryResponse> allCategory = service.getActiveCategory();

		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) {

		try {
			CategoryDTO getById = service.getCategoryDetailsById(id);

			if (ObjectUtils.isEmpty(getById)) {
				return new ResponseEntity<>("Id not found : " + id, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(getById, HttpStatus.OK);
		} 
		
		catch (ResourceNotFound e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} 
		
		catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoryDetailsById(@PathVariable Integer id) {
		Boolean deleted = service.deleteCategoryDetailsById(id);

		if (deleted) {
			return new ResponseEntity<>("Category deleted successfully !!!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Category not deleted !!!", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

}
