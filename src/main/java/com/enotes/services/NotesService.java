package com.enotes.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NotesDTO;


public interface NotesService {

	public boolean saveNotes(String notes, MultipartFile file) throws Exception;
	
	public List<NotesDTO> getAllNotes();
	
}
