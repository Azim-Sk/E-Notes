package com.enotes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enotes.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	List<Category> findByIsActiveTrueAndIsDeletedFalse();

	Optional<Category> findByIdAndIsDeletedFalse(Integer id);

	List<Category> findAllByIsDeletedFalse();

	Boolean existsByName(String name);
}
