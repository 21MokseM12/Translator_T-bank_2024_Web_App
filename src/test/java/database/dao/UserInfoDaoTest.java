package database.dao;

import com.app.App;
import com.app.database.dao.UserInfoDao;
import com.app.database.entities.UserInfo;
import com.app.database.exceptions.DaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {App.class})
public class UserInfoDaoTest {

    @Autowired
    private UserInfoDao dao;

    @Test
    public void saveEntityTest() throws DaoException {
        UserInfo user = new UserInfo(11L, "1:2:7");
        Assertions.assertEquals(user, dao.save(new UserInfo("1:2:7")));
    }

    @Test
    public void deleteEntityTest() throws DaoException {
//        UserInfo user = dao.save(new UserInfo("1:2:5"));
        Assertions.assertTrue(dao.delete(23L));
    }

    @Test
    public void updateEntityTest() throws DaoException {
        UserInfo user = dao.save(new UserInfo("2:3:4"));
        user.setIpAddress("5:6:7");
        Assertions.assertTrue(dao.update(user));
    }

    @Test
    public void readEntityTest() throws DaoException {
        UserInfo user = dao.save(new UserInfo("aloha!"));
        Assertions.assertEquals(user, dao.findById(user.getId()).orElse(null));
    }

    @Test
    public void getIdByIpAddressTest() throws DaoException {
        UserInfo user = dao.save(new UserInfo("Hello, world!"));
        Assertions.assertEquals(user.getId(), dao.getId("Hello, world!").orElse(null));
    }
}