package com.enotes.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NotesDTO;
import com.enotes.entity.FileDetails;
import com.enotes.exception.ResourceNotFound;


public interface NotesService {

	public boolean saveNotes(String notes, MultipartFile file) throws Exception;
	
	public List<NotesDTO> getAllNotes();

	public byte[] downloadFile(FileDetails fileDtls) throws Exception;

	public FileDetails getFileDetails(Integer id);
	
}
