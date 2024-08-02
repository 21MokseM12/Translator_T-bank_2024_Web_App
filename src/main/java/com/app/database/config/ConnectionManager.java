package com.app.database.config;

import com.app.config.PropertiesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Service
public class ConnectionManager {

    private static final String URL = "db.url";

    private static final String USERNAME = "db.username";

    private static final String PASSWORD = "db.password";

    private static final String POOL_SIZE = "db.pool.size";

    private static final int POOL_SIZE_DEFAULT = 10;

    private static BlockingQueue<Connection> pool;

    private static List<Connection> sourceConnections;

    private final PropertiesManager propertiesManager;

    @Autowired
    public ConnectionManager(PropertiesManager propertiesManager) throws SQLException {
        this.propertiesManager = propertiesManager;
        initConnectionPool();
    }

    private void initConnectionPool() throws SQLException {
        String poolSize = propertiesManager.get(POOL_SIZE);
        int size = poolSize == null ? POOL_SIZE_DEFAULT : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);
        sourceConnections = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            Connection connection = open();

            Object proxyConnection = Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(), new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close")
                            ? pool.add((Connection) proxy)
                            : method.invoke(connection, args)
                    );

            pool.add((Connection) proxyConnection);
            sourceConnections.add(connection);
        }

    }

    private Connection open() throws SQLException {
        return DriverManager.getConnection(
                propertiesManager.get(URL),
                propertiesManager.get(USERNAME),
                propertiesManager.get(PASSWORD)
        );
    }

    public Connection get() throws SQLException {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new SQLException(e);
        }
    }

    public void closePool() throws SQLException {
        for (Connection connection : sourceConnections) connection.close();
    }
}
