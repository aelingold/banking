package org.olx.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
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
