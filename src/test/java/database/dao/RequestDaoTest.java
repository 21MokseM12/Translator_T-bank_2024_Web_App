package database.dao;

import com.app.App;
import com.app.database.dao.RequestDao;
import com.app.database.dao.UserInfoDao;
import com.app.database.entities.Request;
import com.app.database.entities.UserInfo;
import com.app.database.exceptions.DaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {App.class})
public class RequestDaoTest {

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private UserInfoDao userDao;

    @Test
    public void saveEntityTest() throws DaoException {
        UserInfo user = userDao.save(new UserInfo("2:3:5"));

        Request request = new Request(user, "request", "response");
        Request response = requestDao.save(request);
        request.setId(2L);

        Assertions.assertEquals(request, response);
    }

    @Test
    public void deleteEntityTest() throws DaoException {
        UserInfo user = userDao.save(new UserInfo("2:3:6"));

        Request request = requestDao.save(new Request(user, "request1", "response1"));
        Assertions.assertTrue(requestDao.delete(request.getId()));
    }

    @Test
    public void updateEntityTest() throws DaoException {
        UserInfo user = userDao.save(new UserInfo("2:3:7"));

        Request request = requestDao.save(new Request(user, "request2", "response2"));
        request.setRequest("2request");
        Assertions.assertTrue(requestDao.update(request));
    }

    @Test
    public void readEntityTest() throws DaoException {
        UserInfo user = userDao.save(new UserInfo("2:3:8"));

        Request request = requestDao.save(new Request(user, "request3", "response3"));
        Assertions.assertEquals(request, requestDao.findById(request.getId()).orElse(null));
    }
}
