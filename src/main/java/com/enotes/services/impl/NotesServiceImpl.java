package com.enotes.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NotesDTO;
import com.enotes.dto.NotesDTO.CategoryDTO;
import com.enotes.entity.Category;
import com.enotes.entity.FileDetails;
import com.enotes.entity.Notes;
import com.enotes.exception.ResourceNotFound;
import com.enotes.repository.CategoryRepository;
import com.enotes.repository.FileRepository;
import com.enotes.repository.NotesRepository;
import com.enotes.services.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository repo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepository catRepo;
	
	@Autowired
	private FileRepository fileRepo;

	@Value("${file.upload.path}")	
	private String uploadPath;
	
	@Override
	public boolean saveNotes(String notes, MultipartFile file) throws Exception {
		
		ObjectMapper obj = new ObjectMapper();
		NotesDTO notesDTO = obj.readValue(notes, NotesDTO.class);
		
		//Category Validation
		checkCategoryExists(notesDTO.getCategoryDTO());
		
		Notes notesMap = mapper.map(notesDTO, Notes.class);
		
		FileDetails fileDetails = saveFile(file);
		
		if(!ObjectUtils.isEmpty(fileDetails)) {
			
			notesMap.setFileDetails(fileDetails);
		}else {
			notesMap.setFileDetails(null);
		}
		
		Notes saveNotes = repo.save(notesMap);
		if(!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}
		return false;
	}


	private FileDetails saveFile(MultipartFile file) throws IOException {
		
		if(!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
			
			String originalFileName = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFileName);
			List<String> allowedExtensions = Arrays.asList(".pdf",".jpg","xlsx",".png");
			if(!allowedExtensions.contains(extension)) {
				throw new IllegalArgumentException("Invalid file format !!!");
			}
			FileDetails fileDetails = new FileDetails();
			fileDetails.setOriginalFileName(originalFileName);
			fileDetails.setDisplayFileName(getDisplayName(originalFileName));
			
			String rndString = UUID.randomUUID().toString();
			String uploadFileName = rndString +"." +extension; // hbchbcjhbhjeb.pdf
			
			fileDetails.setUploadFileName(uploadFileName);
			fileDetails.setFileSize(file.getSize());
			
			File saveFile = new File(uploadPath);
			if(!saveFile.exists()) {
				saveFile.mkdir();
			}
			
			//path: enotesapiservice/notes/java.pdf
			String storePath = uploadPath.concat(uploadFileName);
			fileDetails.setPath(storePath);
			
			//upload file
			
			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			
			if(upload!=0) {
				FileDetails saveFileDetails = fileRepo.save(fileDetails);
				return saveFileDetails;
			}
		}
		return null;
	}


	private String getDisplayName(String originalFileName) {
		//original name : java-programming-tutorials.pdf
		//display name : java-prog.pdf
		String extension = FilenameUtils.getExtension(originalFileName);
		String fileName = FilenameUtils.removeExtension(originalFileName);
		
		if(fileName.length()>8) {
			fileName= fileName.substring(0,7);
		}
		fileName = fileName + "." + extension;
		return fileName;
	}


	private void checkCategoryExists(CategoryDTO categoryDTO) throws ResourceNotFound {
		
		catRepo.findById(categoryDTO.getId()).orElseThrow(() -> new ResourceNotFound("Category id is invalid !!!"));
	}

	@Override
	public List<NotesDTO> getAllNotes() {
		return repo.findAll().stream()
		.map(n -> mapper.map(n, NotesDTO.class)).toList();
	}

}
