package com.hcl.bank.benef.app.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BeneficiaryGlobalExceptionHandler {

	@ExceptionHandler(BeneficiaryServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleStockServiceException(final BeneficiaryServiceException exception,
			final HttpServletRequest request) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorMsg(exception.getMessage());
		response.setRequestUri(request.getRequestURI());
		return response;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleGlobalException(final Exception exception,
			final HttpServletRequest request) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorMsg(exception.getMessage());
		response.setRequestUri(request.getRequestURI());
		return response;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody Map<String, String> handleValidateException(MethodArgumentNotValidException ex) {
		Map<String, String> map = new HashMap<>();
		BindingResult bindingResult = ex.getBindingResult();
		List<ObjectError> allErrors = bindingResult.getAllErrors();
		allErrors.forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String message = ((FieldError) error).getDefaultMessage();
			map.put(fieldName, message);

		});
		return map;

	}

	
}
