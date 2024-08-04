package com.app.database.services;

import com.app.database.config.ConnectionManager;
import com.app.database.dao.RequestDao;
import com.app.database.dao.UserInfoDao;
import com.app.database.entities.Request;
import com.app.database.entities.UserInfo;
import com.app.database.exceptions.DaoException;
import com.app.database.exceptions.DataBaseAPIException;
import com.app.database.utils.DataEncoder;
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

    private final DataEncoder encoder;

    @Autowired
    public DataBaseAPI(UserInfoDao userDao, RequestDao requestDao, ConnectionManager connectionManager, DataEncoder encoder) {
        this.connectionManager = connectionManager;
        this.userDao = userDao;
        this.requestDao = requestDao;
        this.encoder = encoder;
    }

    public boolean save(String ipAddress, String requestSentence, String responseSentence) throws DataBaseAPIException {

        String encodeIpAddress = encoder.encode(ipAddress),
        encodeRequestSentence = encoder.encode(requestSentence),
        encodeResponseSentence = encoder.encode(responseSentence);

        try {
            Connection connection = connectionManager.get();
            try {
                connection.setAutoCommit(false);

                Long userId = userDao.getId(encodeIpAddress, connection).orElse(null);

                UserInfo user;
                if (userId == null) user = userDao.save(new UserInfo(encodeIpAddress), connection);
                else user = userDao.findById(userId, connection).orElse(null);

                Request result = requestDao.save(new Request(user, encodeRequestSentence, encodeResponseSentence), connection);

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
