package me.inexactvim.paymentssystem.service;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.account.AccountBlockedException;
import me.inexactvim.paymentssystem.exception.account.AccountNotFoundException;
import me.inexactvim.paymentssystem.exception.account.NegativeBalanceException;
import me.inexactvim.paymentssystem.util.info.PaymentInfo;

import java.math.BigDecimal;
import java.util.Collection;

public interface PaymentService {

    Collection<PaymentInfo> getAccountPayments(long accountNumber) throws DAOException;

    void createPayment(long senderAccountNumber, long recipientAccountNumber, BigDecimal amount, String comment)
            throws DAOException, AccountNotFoundException, AccountBlockedException, NegativeBalanceException;
}
