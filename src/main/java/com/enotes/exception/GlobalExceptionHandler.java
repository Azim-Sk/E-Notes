package com.enotes.exception;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.enotes.utils.CommonUtils;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception e){
		return CommonUtils.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	//	return new ResponseEntity<> (e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	
		
	}
	
	@ExceptionHandler(ResourceNotFound.class)
	public ResponseEntity<?> handleResourceNotFound(Exception e){
		return CommonUtils.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);

	//	return new ResponseEntity<> (e.getMessage(), HttpStatus.NOT_FOUND);	
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
		Map<String, Object> error = new LinkedHashMap<>();
		allErrors.stream().forEach(er -> {
			String msg = er.getDefaultMessage();
			String field = ((FieldError) (er)).getField();
			error.put(field, msg);
		});
		
		return CommonUtils.createErrorResponse(error, HttpStatus.BAD_REQUEST);

	//	return new ResponseEntity<> (error, HttpStatus.BAD_REQUEST);	
		
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException e){
		return CommonUtils.createErrorResponse(e.getErrors(), HttpStatus.BAD_REQUEST);

//		return new ResponseEntity<> (e.getErrors(), HttpStatus.BAD_REQUEST);	
		
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e){
		return CommonUtils.createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);

//		return new ResponseEntity<> (e.getErrors(), HttpStatus.BAD_REQUEST);	
		
	}
	
}
