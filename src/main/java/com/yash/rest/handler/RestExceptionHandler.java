package com.yash.rest.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yash.rest.exception.EmployeeNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse badRequest(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("Bad Request");
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		return errorResponse;
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse employeeNotFoundHandler(EmployeeNotFoundException ex) {
		return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
	}

}
