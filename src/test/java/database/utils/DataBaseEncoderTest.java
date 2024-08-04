package database.utils;

import com.app.App;
import com.app.database.utils.DataEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {App.class})
public class DataBaseEncoderTest {

    @Autowired
    private DataEncoder encoder;

    @Test
    public void encodeStringTest() {
        Assertions.assertEquals("0J/RgNC40LLQtdGCIQ==", encoder.encode("Привет!"));
    }
    @Test
    public void decodeStringTest() {
        Assertions.assertEquals("Привет!", encoder.decode("0J/RgNC40LLQtdGCIQ=="));
    }

}
