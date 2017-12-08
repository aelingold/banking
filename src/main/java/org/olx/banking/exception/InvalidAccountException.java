package org.olx.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
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
