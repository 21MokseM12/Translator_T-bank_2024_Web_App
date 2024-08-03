package com.app.translator.enums;

public enum HttpKeysConstants {

    AUTHORIZATION_KEY("Authorization"),

    TARGET_LANGUAGE_KEY("targetLanguageCode"),

    SOURCE_LANGUAGE_KEY("sourceLanguageCode"),

    SENTENCE_TRANSLATE_KEY("texts"),

    API_KEY_FORMAT("Api-Key %s"),

    TRANSLATIONS_KEY("translations"),

    TEXT_KEY("text");

    private final String key;

    HttpKeysConstants(String key) {this.key = key;}

    @Override
    public String toString() {return this.key;}
}
