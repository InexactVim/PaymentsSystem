package me.inexactvim.paymentssystem.repository;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.AccountStatus;
import me.inexactvim.paymentssystem.util.info.BlockedAccountInfo;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public interface AccountRepository {

    Optional<Account> loadAccount(long number) throws DAOException;

    long saveAccount(Account account) throws DAOException;

    void setAccountBalance(Account account, BigDecimal amount) throws DAOException;

    void setAccountStatus(Account account, AccountStatus status) throws DAOException;

    Collection<BlockedAccountInfo> loadBlockedAccounts() throws DAOException;
}
