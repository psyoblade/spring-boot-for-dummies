package me.suhyuk.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MissedException extends RuntimeException {

    private String message;

    public MissedException() {
        super();
    }

    public MissedException(String message, Throwable cause) {
        super(message, cause);
    }
    public MissedException(String message) {
        super(message);
    }

    public MissedException(Throwable cause) {
        super(cause);
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }

}

