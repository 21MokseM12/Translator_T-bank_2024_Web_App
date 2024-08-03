package com.app.translator.parsers;

import com.app.translator.enums.HttpKeysConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class JsonParserAPI {

    public String parse(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray translationsArray = jsonObject.getJSONArray(HttpKeysConstants.TRANSLATIONS_KEY.toString());
        JSONObject translatedText = translationsArray.getJSONObject(0);
        return translatedText.getString(HttpKeysConstants.TEXT_KEY.toString());
    }
}
