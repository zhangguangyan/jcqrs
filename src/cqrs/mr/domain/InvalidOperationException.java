package cqrs.mr.domain;

public class InvalidOperationException extends RuntimeException {

	public InvalidOperationException(String error) {
		super(error);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
