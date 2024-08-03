package com.app.database.services;

import com.app.database.config.ConnectionManager;
import com.app.database.dao.RequestDao;
import com.app.database.dao.UserInfoDao;
import com.app.database.entities.Request;
import com.app.database.entities.UserInfo;
import com.app.database.exceptions.DaoException;
import com.app.database.exceptions.DataBaseAPIException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
@Slf4j
public class DataBaseAPI {

    private final ConnectionManager connectionManager;

    private final UserInfoDao userDao;

    private final RequestDao requestDao;

    @Autowired
    public DataBaseAPI(UserInfoDao userDao, RequestDao requestDao, ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.userDao = userDao;
        this.requestDao = requestDao;
    }

    public boolean save(String ipAddress, String requestSentence, String responseSentence) throws DataBaseAPIException {
        try {
            Connection connection = connectionManager.get();
            try {
                connection.setAutoCommit(false);

                Long userId = userDao.getId(ipAddress, connection).orElse(null);

                UserInfo user;
                if (userId == null) user = userDao.save(new UserInfo(ipAddress), connection);
                else user = userDao.findById(userId, connection).orElse(null);

                Request result = requestDao.save(new Request(user, requestSentence, responseSentence), connection);

                connection.commit();

                return result.getId() > 0;
            } catch (SQLException | DaoException e) {
                connection.rollback();
                throw new DataBaseAPIException(e);
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DataBaseAPIException(e);
        }

    }
}
