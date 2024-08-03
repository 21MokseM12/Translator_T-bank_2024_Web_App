package com.app.translator.exceptions;

public class TranslatorAPIException extends Throwable{

    public TranslatorAPIException() {super();}

    public TranslatorAPIException(String message) {super(message);}

    public TranslatorAPIException(Throwable cause) {super(cause);}

    public TranslatorAPIException(String message, Throwable cause) {super(message, cause);}
}
