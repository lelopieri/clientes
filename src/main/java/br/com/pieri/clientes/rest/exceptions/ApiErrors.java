package br.com.pieri.clientes.rest.exceptions;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {

	@Getter
	private List<String> errors;

	public ApiErrors(List<String> errors){
		this.errors = errors;
	}

	public ApiErrors(String error){
		this.errors = Arrays.asList(error);
	}
}
