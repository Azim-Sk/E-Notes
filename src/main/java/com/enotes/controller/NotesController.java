package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NotesDTO;
import com.enotes.services.NotesService;
import com.enotes.utils.CommonUtils;

import jakarta.servlet.annotation.MultipartConfig;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	
	@Autowired
	private NotesService service;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveNotes(@RequestParam String notes, 
			@RequestParam(required = false) MultipartFile file) throws Exception{
		boolean saveNotes = service.saveNotes(notes,file);
		
		if(saveNotes) {
			CommonUtils.createBuildResponseMessage("notes saved successfully !!!", HttpStatus.CREATED);
		}
		return CommonUtils.createErrorResponseMessage("Notes not saved !!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllNotes(){
		List<NotesDTO> allNotes = service.getAllNotes();
		
		if(CollectionUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtils.createBuildResponse(allNotes, HttpStatus.OK);
	}

}
