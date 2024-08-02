package com.app.database.dao;

import com.app.database.entities.Request;
import com.app.database.exceptions.DaoException;
import com.app.database.config.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public class RequestDao implements DAO<Long, Request> {

    private static final String SAVE_SQL = """
            INSERT INTO request
            (user_id, request_sentence, response_sentence)
            VALUES
            (?, ?, ?)
            """;

    private static final String DELETE_SQL = """
            DELETE
            FROM request
            WHERE id = ?
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT
                id,
                user_id,
                request_sentence,
                response_sentence
            FROM request
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE request
            SET
                user_id = ?,
                request_sentence = ?,
                response_sentence = ?
            WHERE id = ?
            """;

    private final ConnectionManager connectionManager;

    private final UserInfoDao userInfoDao;

    @Autowired
    public RequestDao(ConnectionManager connectionManager, UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
        this.connectionManager = connectionManager;
    }

    @Override
    public Request save(Request entity) throws DaoException {
        try {
            return save(entity, connectionManager.get());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Request save(Request entity, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getUserInfo().getId());
            statement.setString(2, entity.getRequest());
            statement.setString(3, entity.getResponse());

            statement.executeUpdate();
            ResultSet response = statement.getGeneratedKeys();

            if (response.next())
                entity.setId(response.getLong("id"));

            return entity;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try {
            return delete(id, connectionManager.get());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Long id, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Request> findById(Long id) throws DaoException {
        try {
            return findById(id, connectionManager.get());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Request> findById(Long id, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);

            Request request = null;
            ResultSet response = statement.executeQuery();
            if (response.next()) request = buildRequestEntity(response);

            return Optional.ofNullable(request);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Request entity) throws DaoException {
        try {
            return update(entity, connectionManager.get());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(Request entity, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setLong(1, entity.getUserInfo().getId());
            statement.setString(2, entity.getRequest());
            statement.setString(3, entity.getResponse());
            statement.setLong(4, entity.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Request buildRequestEntity(ResultSet response) throws SQLException, DaoException {
        return new Request(
                response.getLong("id"),
                userInfoDao.findById(response.getLong("user_id")).orElse(null),
                response.getString("request_sentence"),
                response.getString("response_sentence")
        );
    }
}
