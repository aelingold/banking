package org.olx.banking.exception;

public class InvalidAccountException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidAccountException() {
        super();
    }
    public InvalidAccountException(String s) {
        super(s);
    }
    public InvalidAccountException(String s, Throwable throwable) {
        super(s, throwable);
    }
    public InvalidAccountException(Throwable throwable) {
        super(throwable);
    }

}
