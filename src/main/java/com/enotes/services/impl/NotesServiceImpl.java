package com.enotes.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.NotesDTO;
import com.enotes.dto.NotesDTO.CategoryDTO;
import com.enotes.entity.Category;
import com.enotes.entity.Notes;
import com.enotes.exception.ResourceNotFound;
import com.enotes.repository.CategoryRepository;
import com.enotes.repository.NotesRepository;
import com.enotes.services.NotesService;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository repo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepository catRepo;

	@Override
	public boolean saveNotes(NotesDTO notesDTO) throws Exception {
		
		//Category Validation
		checkCategoryExists(notesDTO.getCategoryDTO());
		
		Notes notes = mapper.map(notesDTO, Notes.class);
		Notes saveNotes = repo.save(notes);
		if(!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}
		return false;
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
