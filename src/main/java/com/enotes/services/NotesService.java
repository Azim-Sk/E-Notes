package com.enotes.services;

import java.util.List;

import com.enotes.dto.NotesDTO;


public interface NotesService {

	public boolean saveNotes(NotesDTO notesDTO);
	
	public List<NotesDTO> getAllNotes();
	
	
}