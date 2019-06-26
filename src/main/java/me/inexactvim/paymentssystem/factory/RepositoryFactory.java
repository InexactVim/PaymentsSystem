package me.inexactvim.paymentssystem.factory;

import me.inexactvim.paymentssystem.repository.AccountRepository;
import me.inexactvim.paymentssystem.repository.UserRepository;
import me.inexactvim.paymentssystem.repository.sql.SqlAccountRepository;
import me.inexactvim.paymentssystem.repository.sql.SqlUserRepository;

public class RepositoryFactory {

    private static UserRepository userRepository;
    private static AccountRepository accountRepository;

    static {
        userRepository = new SqlUserRepository(SqlDatabaseFactory.getDatabaseManager());
        accountRepository = new SqlAccountRepository(SqlDatabaseFactory.getDatabaseManager());
    }

    public static synchronized UserRepository getUserRepository() {
        return userRepository;
    }

    public static synchronized AccountRepository getAccountRepository() {
        return accountRepository;
    }
}
