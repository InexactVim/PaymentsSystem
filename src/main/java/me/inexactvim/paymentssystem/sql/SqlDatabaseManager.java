package me.inexactvim.paymentssystem.sql;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.util.function.ThrowableFunction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

public interface SqlDatabaseManager {

    void openConnection(Properties properties);

    int executeUpdate(String query, Object... params) throws DAOException;

    <T> T executeQuery(String query, ThrowableFunction<ResultSet, T> resultSetTFunction, Object... params) throws DAOException;

    <T> T customExecute(ThrowableFunction<Connection, T> function) throws DAOException;

    void closeConnection();

}
