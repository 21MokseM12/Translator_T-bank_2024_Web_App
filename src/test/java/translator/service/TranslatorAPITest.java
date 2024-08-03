package translator.service;

import com.app.App;
import com.app.translator.exceptions.TranslatorAPIException;
import com.app.translator.services.TranslatorAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {App.class})
public class TranslatorAPITest {

    @Autowired
    private TranslatorAPI translator;

    @Test
    public void translateSentenceTestRussianEnglish() throws TranslatorAPIException {
        Assertions.assertEquals("This word I wrote very quickly and not want his divide on two", translator.translate("ru", "en",
                "Это слово я написал очень быстро и не хочу его разделять на два"));
    }

    @Test
    public void translateSentenceTestEnglishRussian() throws TranslatorAPIException {
        Assertions.assertEquals("Здравствуйте мир, этот является мой первый программа", translator.translate("en", "ru",
                "Hello world, this is my first program"));
    }

    @Test
    public void translateSentenceTestEnglishSpanish() throws TranslatorAPIException {
        Assertions.assertEquals("Hola mundo, esto es mi primero programa", translator.translate("en", "es",
                "Hello world, this is my first program"));
    }
}
