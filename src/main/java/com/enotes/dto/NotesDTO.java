package com.enotes.dto;

import java.util.Date;

import com.enotes.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotesDTO {
	
	private Integer id;
	private String title;
	private String description;

	private CategoryDTO categoryDTO;
	
	private Integer createdBy;

	private Date createdOn;

	private Integer updatedBy;

	private Date updatedOn;

}
