package database.service;

import com.app.App;
import com.app.database.exceptions.DataBaseAPIException;
import com.app.database.service.DataBaseAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {App.class})
public class DataBaseAPITest {

    @Autowired
    private DataBaseAPI dataBase;

    @Test
    public void saveAllInputDataInDatabase() throws DataBaseAPIException {
        Assertions.assertTrue(dataBase.save("ip_address2", "request", "response"));
    }

    @Test
    public void saveDataIfUserAlreadyExist() throws DataBaseAPIException {
        dataBase.save("By", "By!", "By!");
        Assertions.assertTrue(dataBase.save("By", "By2!","By2!"));
    }
}
