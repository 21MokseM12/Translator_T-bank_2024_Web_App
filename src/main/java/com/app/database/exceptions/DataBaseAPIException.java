package com.app.database.exceptions;

public class DataBaseAPIException extends Throwable{

    public DataBaseAPIException() {super();}

    public DataBaseAPIException(String message) {super(message);}

    public DataBaseAPIException(Throwable cause) {super(cause);}

    public DataBaseAPIException(String message, Throwable cause) {super(message, cause);}
}
