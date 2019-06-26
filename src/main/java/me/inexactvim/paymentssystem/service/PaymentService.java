package me.inexactvim.paymentssystem.service;

import me.inexactvim.paymentssystem.exception.*;
import me.inexactvim.paymentssystem.object.Payment;

import java.math.BigDecimal;
import java.util.Collection;

public interface PaymentService {

    Collection<Payment> getUserPayments(long userId) throws DAOException;

    void createPayment(long senderAccountNumber, long recipientAccountNumber, BigDecimal amount, String comment)
            throws DAOException, AccountNotFoundException, AccountBlockedException, NegativeBalanceException;
}
