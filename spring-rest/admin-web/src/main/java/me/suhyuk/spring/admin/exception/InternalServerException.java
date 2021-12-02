package me.suhyuk.spring.admin.exception;

public class InternalServerException extends RuntimeException {

    private static final long serialVersionUID = 0l;
    private int errorCode;

    public InternalServerException() {
        super();
        this.errorCode = 0;
    }

    public InternalServerException(String s) {
        super(s);
        this.errorCode = 0;
    }

    public InternalServerException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public InternalServerException(String s, int errorCode) {
        super(s);
        this.errorCode = errorCode;
    }

    public int errorCode() {
        return errorCode;
    }

}
