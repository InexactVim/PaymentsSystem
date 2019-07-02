package me.inexactvim.paymentssystem.service.impl;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.account.AccountBlockedException;
import me.inexactvim.paymentssystem.exception.account.AccountNotFoundException;
import me.inexactvim.paymentssystem.exception.account.NegativeBalanceException;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.AccountStatus;
import me.inexactvim.paymentssystem.repository.AccountRepository;
import me.inexactvim.paymentssystem.service.AccountService;
import me.inexactvim.paymentssystem.util.info.BlockedAccountInfo;

import java.math.BigDecimal;
import java.util.Collection;

public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccount(long number) throws DAOException, AccountNotFoundException {
        return accountRepository.loadAccount(number)
                .orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public Account createAccount() throws DAOException {
        Account account = new Account();
        account.setBalance(new BigDecimal("0"));
        account.setStatus(AccountStatus.ACTIVE);
        long accountNumber = accountRepository.saveAccount(account);
        account.setNumber(accountNumber);
        return account;
    }

    @Override
    public void depositAccount(Account account, BigDecimal amount) throws DAOException {
        BigDecimal newBalance = account.getBalance().add(amount);
        accountRepository.setAccountBalance(account, newBalance);
        account.setBalance(newBalance);
    }

    @Override
    public void withdrawAccount(Account account, BigDecimal amount) throws DAOException, NegativeBalanceException {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new NegativeBalanceException();
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        accountRepository.setAccountBalance(account, newBalance);
        account.setBalance(newBalance);
    }

    @Override
    public void blockAccount(Account account) throws DAOException, AccountBlockedException {
        if (account.getStatus() == AccountStatus.BLOCKED) {
            throw new AccountBlockedException("Account already blocked");
        }

        accountRepository.setAccountStatus(account, AccountStatus.BLOCKED);
        account.setStatus(AccountStatus.BLOCKED);
    }

    @Override
    public Collection<BlockedAccountInfo> getBlockedAccounts() throws DAOException {
        return accountRepository.loadBlockedAccounts();
    }

    @Override
    public void unblockAccount(Account account) throws DAOException {
        accountRepository.setAccountStatus(account, AccountStatus.ACTIVE);
        account.setStatus(AccountStatus.ACTIVE);
    }
}
