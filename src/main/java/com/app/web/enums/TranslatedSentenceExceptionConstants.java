package com.app.web.enums;

public enum TranslatedSentenceExceptionConstants {

    INVALID_INPUT_SOURCE_LANGUAGE("http 400 Не найден язык исходного сообщения"),

    INVALID_INPUT_TARGET_LANGUAGE("http 400 Не найден язык итогового сообщения"),

    INVALID_SENTENCE("http 400 Не найдено выражение для перевода"),

    INVALID_RESPONSE("http 400 Ошибка доступа к ресурсу перевода");

    private final String sentence;

    TranslatedSentenceExceptionConstants(String sentence) {this.sentence = sentence;}

    @Override
    public String toString() {return this.sentence;}
}
