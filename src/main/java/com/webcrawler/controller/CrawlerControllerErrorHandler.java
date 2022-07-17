package com.webcrawler.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.webcrawler.exception.CrawlerError;

@RestControllerAdvice
public class CrawlerControllerErrorHandler {

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<CrawlerError> commonErrorHandler(Exception e) {
		CrawlerError error = new CrawlerError("ACB001", e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

}
