package me.inexactvim.paymentssystem.repository.sql;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.AccountStatus;
import me.inexactvim.paymentssystem.repository.AccountRepository;
import me.inexactvim.paymentssystem.sql.SqlDatabaseManager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class SqlAccountRepository implements AccountRepository {

    private SqlDatabaseManager databaseManager;

    public SqlAccountRepository(SqlDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public Optional<Account> loadAccount(long number) throws DAOException {
        return Optional.ofNullable(databaseManager.executeQuery("SELECT * FROM accounts WHERE number=?", resultSet -> {
            if (!resultSet.next()) {
                return null;
            }

            Account account = new Account();
            account.setNumber(number);
            account.setBalance(resultSet.getBigDecimal("balance"));
            account.setStatus(AccountStatus.valueOf(resultSet.getByte("status")));
            return account;
        }, number));
    }

    @Override
    public long saveAccount(Account account) throws DAOException {
        return databaseManager.customExecute(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts (balance, status) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1, account.getBalance());
            statement.setObject(2, account.getStatus().ordinal());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

            throw new DAOException();
        });
    }

    @Override
    public void setAccountBalance(Account account, BigDecimal amount) throws DAOException {
        databaseManager.executeUpdate("UPDATE accounts SET balance=? WHERE number=?",
                amount, account.getNumber());
    }

    @Override
    public void setAccountStatus(Account account, AccountStatus status) throws DAOException {
        databaseManager.executeUpdate("UPDATE accounts SET status=? WHERE number=?",
                status.ordinal(), account.getNumber());
    }
}
