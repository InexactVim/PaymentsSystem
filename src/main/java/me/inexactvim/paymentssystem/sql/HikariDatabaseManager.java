package me.inexactvim.paymentssystem.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.util.function.ThrowableFunction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class HikariDatabaseManager implements SqlDatabaseManager {

    private HikariDataSource dataSource;

    @Override
    public void openConnection(Properties properties) {
        dataSource = new HikariDataSource(new HikariConfig(properties));
    }

    @Override
    public int executeUpdate(String query, Object... params) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            if (params.length > 0) {
                for (int i = 1; i <= params.length; i++) {
                    statement.setObject(i, params[i - 1]);
                }
            }

            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public <T> T executeQuery(String query, ThrowableFunction<ResultSet, T> resultSetTFunction, Object... params) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            if (params.length > 0) {
                for (int i = 1; i <= params.length; i++) {
                    statement.setObject(i, params[i - 1]);
                }
            }

            return resultSetTFunction.apply(statement.executeQuery());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public <T> T customExecute(ThrowableFunction<Connection, T> function) throws DAOException {
        try (Connection connection = dataSource.getConnection()) {
            return function.apply(connection);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void closeConnection() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
