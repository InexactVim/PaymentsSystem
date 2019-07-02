package me.inexactvim.paymentssystem.repository.sql;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.AccountStatus;
import me.inexactvim.paymentssystem.repository.AccountRepository;
import me.inexactvim.paymentssystem.sql.SqlDatabaseManager;
import me.inexactvim.paymentssystem.util.info.BlockedAccountInfo;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    @Override
    public Collection<BlockedAccountInfo> loadBlockedAccounts() throws DAOException {
        return databaseManager.executeQuery("SELECT accounts.number AS number, users.name AS name, users.surname AS surname " +
                        "FROM accounts " +
                        "INNER JOIN users ON accounts.number=users.account_number " +
                        "WHERE accounts.status=?",
                resultSet -> {
                    Collection<BlockedAccountInfo> blockedAccounts;

                    if (resultSet.next()) {
                        blockedAccounts = new ArrayList<>();
                        blockedAccounts.add(fetchBlockedAccountFromResultSet(resultSet));

                        while (resultSet.next()) {
                            blockedAccounts.add(fetchBlockedAccountFromResultSet(resultSet));
                        }
                    } else {
                        blockedAccounts = Collections.emptyList();
                    }

                    return blockedAccounts;
                }, AccountStatus.BLOCKED.ordinal());
    }

    private BlockedAccountInfo fetchBlockedAccountFromResultSet(ResultSet resultSet) throws SQLException {
        return new BlockedAccountInfo(
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getLong(1)

        );
    }
}
