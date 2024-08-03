package web.utils;

import com.app.App;
import com.app.web.utils.InputTranslateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {App.class})
public class InputTranslateValidatorTest {

    @Autowired
    private InputTranslateValidator validator;

    @Test
    public void validateValidLanguageTest() {
        Assertions.assertTrue(validator.isValidLanguage("ru"));
    }

    @Test
    public void validateInvalidLanguageTest() {
        Assertions.assertFalse(validator.isValidLanguage("rr"));
    }

    @Test
    public void validateValidSentenceTest() {
        Assertions.assertFalse(validator.isValidSentence(""));
    }

    @Test
    public void validateInvalidSentenceTest() {
        Assertions.assertFalse(validator.isValidLanguage("rr  ffqf "));
    }

    @Test
    public void validateSpacesTestFirst() {
        Assertions.assertEquals("ru", validator.validateSpaces(" ru "));
    }

    @Test
    public void validateSpacesTestSecond() {
        Assertions.assertEquals("r  u", validator.validateSpaces("     r  u      "));
    }

    @Test
    public void validateSpacesTestThird() {
        Assertions.assertEquals("", validator.validateSpaces("           "));
    }
}
