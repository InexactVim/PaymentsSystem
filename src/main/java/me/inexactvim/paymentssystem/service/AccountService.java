package me.inexactvim.paymentssystem.service;

import me.inexactvim.paymentssystem.exception.AccountNotFoundException;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.NegativeBalanceException;
import me.inexactvim.paymentssystem.object.Account;

import java.math.BigDecimal;

public interface AccountService {

    Account getAccount(long number) throws DAOException, AccountNotFoundException;

    Account createAccount() throws DAOException;

    void depositAccount(Account account, BigDecimal amount) throws DAOException;

    void withdrawAccount(Account account, BigDecimal amount) throws DAOException, NegativeBalanceException;

    void blockAccount(Account account) throws DAOException;

    void unblockAccount(Account account) throws DAOException;
}
