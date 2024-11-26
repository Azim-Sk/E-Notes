package com.enotes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.Category;
import com.enotes.entity.FileDetails;

public interface FileRepository extends JpaRepository<FileDetails, Integer>{

	Optional<Category> findById(FileDetails fileDtls);

}
