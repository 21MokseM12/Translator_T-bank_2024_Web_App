package translator.http;

import com.app.App;
import com.app.translator.exceptions.RestTemplateHandlerException;
import com.app.translator.http.RestTemplateHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {App.class})
public class RestTemplateHandlerTest {

    @Autowired
    private RestTemplateHandler restTemplateHandler;

    @Test
    public void getRequestTest() throws RestTemplateHandlerException {
        String response = "{\n" +
                " \"translations\": [\n" +
                "  {\n" +
                "   \"text\": \"Hi!\"\n" +
                "  }\n" +
                " ]\n" +
                "}\n";
        Assertions.assertEquals(response, restTemplateHandler.POST("ru", "en", "Привет!").getBody());
    }
}
