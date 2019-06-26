package me.inexactvim.paymentssystem.factory;

import me.inexactvim.paymentssystem.sql.HikariDatabaseManager;
import me.inexactvim.paymentssystem.sql.SqlDatabaseManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SqlDatabaseFactory {

    private static SqlDatabaseManager databaseManager;

    static {
        databaseManager = new HikariDatabaseManager();
        Properties properties = new Properties();

        try (InputStream inputStream = SqlDatabaseFactory.class.getResourceAsStream("/database.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        databaseManager.openConnection(properties);
    }

    public static synchronized SqlDatabaseManager getDatabaseManager() {
        return databaseManager;
    }

}
