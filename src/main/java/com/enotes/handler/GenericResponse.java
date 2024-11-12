package com.enotes.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import lombok.Builder;

@Builder
public class GenericResponse {

	private HttpStatus responseStatus;
	private String status; // success or failed
	private String message; // saved successfully
	private Object data; // data

	public ResponseEntity<?> create() {
		Map<String, Object> map = new HashMap<>();
		map.put("status", status);
		map.put("message", message);

		if (!ObjectUtils.isEmpty(data)) {
			map.put("data", status);
		}
		return new ResponseEntity<>(map, responseStatus);

	}

	public GenericResponse(HttpStatus responseStatus, String status, String message, Object data) {
		super();
		this.responseStatus = responseStatus;
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public GenericResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HttpStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(HttpStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
