package me.inexactvim.paymentssystem.service.impl;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.account.AccountBlockedException;
import me.inexactvim.paymentssystem.exception.account.AccountNotFoundException;
import me.inexactvim.paymentssystem.exception.account.NegativeBalanceException;
import me.inexactvim.paymentssystem.factory.ServiceFactory;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.AccountStatus;
import me.inexactvim.paymentssystem.object.Payment;
import me.inexactvim.paymentssystem.repository.PaymentRepository;
import me.inexactvim.paymentssystem.service.AccountService;
import me.inexactvim.paymentssystem.service.PaymentService;
import me.inexactvim.paymentssystem.util.info.PaymentInfo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.stream.Collectors;

public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private AccountService accountService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, AccountService accountService) {
        this.paymentRepository = paymentRepository;
        this.accountService = accountService;
    }

    @Override
    public Collection<PaymentInfo> getAccountPayments(long accountNumber) throws DAOException {
        return paymentRepository.loadAccountPayments(accountNumber).stream()
                //.sorted(Comparator.comparing(Payment::getTimestamp).reversed())
                .map(payment -> new PaymentInfo(accountNumber, payment))
                .collect(Collectors.toList());
    }

    @Override
    public void createPayment(long senderAccountNumber, long recipientAccountNumber, BigDecimal amount, String comment) throws DAOException, AccountNotFoundException, AccountBlockedException, NegativeBalanceException {
        Account sender = ServiceFactory.getAccountService().getAccount(senderAccountNumber);

        if (sender.getStatus() == AccountStatus.BLOCKED) {
            throw new AccountBlockedException("Your account is blocked");
        }

        Account recipient = ServiceFactory.getAccountService().getAccount(recipientAccountNumber);

        if (recipient.getStatus() == AccountStatus.BLOCKED) {
            throw new AccountBlockedException("Recipient account is blocked");
        }

        accountService.withdrawAccount(sender, amount);
        accountService.depositAccount(recipient, amount);
        Payment payment = new Payment();
        payment.setTimestamp(new Timestamp(System.currentTimeMillis()));
        payment.setComment(comment);
        payment.setAmount(amount);
        payment.setSenderAccountNumber(senderAccountNumber);
        payment.setRecipientAccountNumber(recipientAccountNumber);
        long id = paymentRepository.savePayment(payment);
        payment.setId(id);
    }
}
