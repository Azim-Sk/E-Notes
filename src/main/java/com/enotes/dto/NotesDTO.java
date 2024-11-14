package com.enotes.dto;

import java.util.Date;

import com.enotes.entity.Category;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

	private FileDTO fileDetails;

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	public static class FileDTO {

		private Integer id;
		private String originalFileName;
		private String displayFileName;

	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	public static class CategoryDTO {

		private Integer id;
		private String name;
	}
}
