package org.olx.banking.exception;

public class InsufficientFundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InsufficientFundException() {
        super();
    }
    public InsufficientFundException(String s) {
        super(s);
    }
    public InsufficientFundException(String s, Throwable throwable) {
        super(s, throwable);
    }
    public InsufficientFundException(Throwable throwable) {
        super(throwable);
    }	
}
