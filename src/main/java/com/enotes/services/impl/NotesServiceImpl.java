package com.enotes.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.NotesDTO;
import com.enotes.entity.Notes;
import com.enotes.repository.NotesRepository;
import com.enotes.services.NotesService;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository repo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public boolean saveNotes(NotesDTO notesDTO) {
		Notes notes = mapper.map(notesDTO, Notes.class);
		Notes saveNotes = repo.save(notes);
		if(!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}
		return false;
	}

	@Override
	public List<NotesDTO> getAllNotes() {
		return repo.findAll().stream()
		.map(n -> mapper.map(n, NotesDTO.class)).toList();
	}

}
