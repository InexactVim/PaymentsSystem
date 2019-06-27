package me.inexactvim.paymentssystem.service.impl;

import me.inexactvim.paymentssystem.exception.AccountBlockedException;
import me.inexactvim.paymentssystem.exception.AccountNotFoundException;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.NegativeBalanceException;
import me.inexactvim.paymentssystem.object.Payment;
import me.inexactvim.paymentssystem.repository.PaymentRepository;
import me.inexactvim.paymentssystem.service.PaymentService;

import java.math.BigDecimal;
import java.util.Collection;

public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Collection<Payment> getAccountPayments(long accountNumber) throws DAOException {
        return paymentRepository.loadAccountPayments(accountNumber);
    }

    @Override
    public void createPayment(long senderAccountNumber, long recipientAccountNumber, BigDecimal amount, String comment) throws DAOException, AccountNotFoundException, AccountBlockedException, NegativeBalanceException {
    }
}
