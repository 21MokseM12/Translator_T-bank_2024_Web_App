package com.app.database.dao;

import com.app.database.entities.UserInfo;
import com.app.database.exceptions.DaoException;
import com.app.database.config.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public class UserInfoDao implements DAO<Long, UserInfo> {

    private static final String SAVE_SQL = """
            INSERT INTO user_info
            (ip_address)
            VALUES
            (?)
            """;

    private static final String DELETE_SQL = """
            DELETE
            FROM user_info
            WHERE id = ?
            """;

    private static final String DELETE_REQUESTS_BY_ID_SQL = """
            DELETE
            FROM request
            WHERE user_id = ?
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT
                id,
                ip_address
            FROM user_info
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE user_info
            SET ip_address = ?
            WHERE id = ?
            """;

    private static final String FIND_BY_IP_SQL = """
            SELECT id
            FROM user_info
            WHERE ip_address = ?
            """;

    private final ConnectionManager connectionManager;

    @Autowired
    public UserInfoDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public Optional<Long> getId(String ipAddress) throws DaoException {
        try {
            return getId(ipAddress, connectionManager.get());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Long> getId(String ipAddress, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_IP_SQL)) {
            statement.setString(1, ipAddress);

            Long id = null;
            ResultSet response = statement.executeQuery();
            if (response.next()) id = response.getLong("id");

            return Optional.ofNullable(id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public UserInfo save(UserInfo entity) throws DaoException {
        try {
            return save(entity, connectionManager.get());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public UserInfo save(UserInfo entity, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getIpAddress());

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
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statementDeleteUserInfo = connection.prepareStatement(DELETE_SQL);
                 PreparedStatement statementDeleteRequests = connection.prepareStatement(DELETE_REQUESTS_BY_ID_SQL)) {
                statementDeleteUserInfo.setLong(1, id);
                statementDeleteRequests.setLong(1, id);

                statementDeleteRequests.executeUpdate();
                int result = statementDeleteUserInfo.executeUpdate();

                connection.commit();
                return result > 0;
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException(e);
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<UserInfo> findById(Long id) throws DaoException {
        try {
            return findById(id, connectionManager.get());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<UserInfo> findById(Long id, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);

            UserInfo userInfo = null;
            ResultSet response = statement.executeQuery();
            if (response.next()) userInfo = buildUserInfoEntity(response);

            return Optional.ofNullable(userInfo);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(UserInfo entity) throws DaoException {
        try {
            return update(entity, connectionManager.get());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(UserInfo entity, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, entity.getIpAddress());
            statement.setLong(2, entity.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private UserInfo buildUserInfoEntity(ResultSet response) throws SQLException {
        return new UserInfo(
                response.getLong("id"),
                response.getString("ip_address")
        );
    }
}