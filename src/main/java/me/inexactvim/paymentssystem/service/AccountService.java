package me.inexactvim.paymentssystem.service;

import me.inexactvim.paymentssystem.exception.account.AccountBlockedException;
import me.inexactvim.paymentssystem.exception.account.AccountNotFoundException;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.account.NegativeBalanceException;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.util.info.BlockedAccountInfo;

import java.math.BigDecimal;
import java.util.Collection;

public interface AccountService {

    Account getAccount(long number) throws DAOException, AccountNotFoundException;

    Account createAccount() throws DAOException;

    void depositAccount(Account account, BigDecimal amount) throws DAOException;

    void withdrawAccount(Account account, BigDecimal amount) throws DAOException, NegativeBalanceException;

    void blockAccount(Account account) throws DAOException, AccountBlockedException;

    Collection<BlockedAccountInfo> getBlockedAccounts() throws DAOException;

    void unblockAccount(Account account) throws DAOException;
}
