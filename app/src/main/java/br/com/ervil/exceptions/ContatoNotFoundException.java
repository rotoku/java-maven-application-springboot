package br.com.ervil.exceptions;

public class ContatoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8713209376466501038L;

	public ContatoNotFoundException(String message) {
		super(message);
	}
}
