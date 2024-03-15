package com.laps.lapsfood.domain.exception;

public class EntidadeNaoEcontradaException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEcontradaException(String mensagem) {
		super(mensagem);
	}

}
