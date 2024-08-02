package com.app.database.exceptions;

public class DaoException extends Throwable {

    public DaoException() {super();}

    public DaoException(String message) {super(message);}

    public DaoException(Throwable cause) {super(cause);}

    public DaoException(String message, Throwable cause) {super(message, cause);}
}
