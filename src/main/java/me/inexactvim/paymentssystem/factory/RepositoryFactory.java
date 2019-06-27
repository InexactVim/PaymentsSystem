package me.inexactvim.paymentssystem.factory;

import me.inexactvim.paymentssystem.repository.AccountRepository;
import me.inexactvim.paymentssystem.repository.CreditCardRepository;
import me.inexactvim.paymentssystem.repository.PaymentRepository;
import me.inexactvim.paymentssystem.repository.UserRepository;
import me.inexactvim.paymentssystem.repository.sql.SqlAccountRepository;
import me.inexactvim.paymentssystem.repository.sql.SqlCreditCardRepository;
import me.inexactvim.paymentssystem.repository.sql.SqlPaymentRepository;
import me.inexactvim.paymentssystem.repository.sql.SqlUserRepository;

public class RepositoryFactory {

    private static UserRepository userRepository;
    private static AccountRepository accountRepository;
    private static PaymentRepository paymentRepository;
    private static CreditCardRepository creditCardRepository;

    static {
        userRepository = new SqlUserRepository(SqlDatabaseFactory.getDatabaseManager());
        accountRepository = new SqlAccountRepository(SqlDatabaseFactory.getDatabaseManager());
        paymentRepository = new SqlPaymentRepository(SqlDatabaseFactory.getDatabaseManager());
        creditCardRepository = new SqlCreditCardRepository(SqlDatabaseFactory.getDatabaseManager());
    }

    public static synchronized UserRepository getUserRepository() {
        return userRepository;
    }

    public static synchronized AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public static synchronized PaymentRepository getPaymentRepository() {
        return paymentRepository;
    }

    public static synchronized CreditCardRepository getCreditCardRepository() {
        return creditCardRepository;
    }
}
