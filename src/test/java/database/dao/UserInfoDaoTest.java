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
    public void saveMethodTest() throws DaoException {
        UserInfo user = new UserInfo(11L, "1:2:7");
        Assertions.assertEquals(user, dao.save(new UserInfo("1:2:7")));
    }

    @Test
    public void deleteMethodTest() throws DaoException {
        UserInfo user = dao.save(new UserInfo("1:2:5"));
        Assertions.assertTrue(dao.delete(user.getId()));
    }

    @Test
    public void updateMethodTest() throws DaoException {
        UserInfo user = dao.save(new UserInfo("2:3:4"));
        user.setIpAddress("5:6:7");
        Assertions.assertTrue(dao.update(user));
    }

    @Test
    public void getEntityMethodTest() throws DaoException {
        UserInfo user = dao.save(new UserInfo("aloha!"));
        Assertions.assertEquals(user, dao.findById(user.getId()).orElse(null));
    }
}