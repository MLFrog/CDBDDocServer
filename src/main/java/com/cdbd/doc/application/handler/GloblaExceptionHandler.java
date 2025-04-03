package com.cdbd.doc.application.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GloblaExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handlerException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							 .body("서버 오류 : " + e.getMessage());
	}
	
}
