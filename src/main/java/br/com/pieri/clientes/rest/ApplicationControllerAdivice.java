package br.com.pieri.clientes.rest;

import br.com.pieri.clientes.rest.exceptions.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdivice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleValidationError(MethodArgumentNotValidException e){
		BindingResult bindingResult = e.getBindingResult();
		List<String> erros = bindingResult.getAllErrors()
				.stream()
				.map(objectError -> objectError.getDefaultMessage())
				.collect(Collectors.toList());

		return new ApiErrors(erros);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity handleResponseStatusException(ResponseStatusException e){
		String msgError = e.getMessage();
		HttpStatus status = e.getStatus();
		ApiErrors apiError = new ApiErrors(msgError);
		ResponseEntity responseEntity = new ResponseEntity(apiError, status);
		return responseEntity;
	}
}
