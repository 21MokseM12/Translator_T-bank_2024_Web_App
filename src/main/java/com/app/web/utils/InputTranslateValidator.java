package com.app.web.utils;

import org.springframework.stereotype.Component;

@Component
public class InputTranslateValidator {

    public boolean isValidLanguage(String language) {
        return switch (validateSpaces(language)) {
            case "af", "am", "ar", "az", "ba", "be", "bg", "bn", "bs", "ca", "ceb", "cs", "cv", "cy", "da", "de", "el",
                 "emj", "en", "eo", "es", "et", "eu", "fa", "fi", "fr", "ga", "gd", "gl", "gu", "he", "hi", "hr", "ht",
                 "hu", "hy", "idй", "is", "it", "ja", "jv", "ka", "kazlat", "kk", "km", "kn", "ko", "ky", "la", "lb",
                 "lo", "lt", "lv", "mg", "mhr", "mi", "mk", "ml", "mn", "mr", "mrj", "ms", "mt", "my", "ne", "nl", "no",
                 "os", "pa", "pap", "pl", "pt", "pt-BR", "ro", "ru", "sah", "si", "sk", "sl", "sq", "sr", "sr-Latn",
                 "su", "sv", "sw", "ta", "te", "tg", "th", "tl", "tr", "tt", "udm", "uk", "ur", "uz", "uzbcyr", "vi",
                 "xh", "yi", "zh", "zu" -> true;
            default -> false;
        };
    }

    public boolean isValidSentence(String sentence) {
        return !validateSpaces(sentence).isEmpty();
    }

    public String validateSpaces(String data) {
        int low, high;
        for (low = 0; low < data.length(); low++)
            if (data.charAt(low) != ' ') break;

        for (high = data.length()-1; high >= 0; high--)
            if (data.charAt(high) != ' ') break;

        if (low == data.length() && high == -1) return "";
        return data.substring(low, high+1);
    }
}
