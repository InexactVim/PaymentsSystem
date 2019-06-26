package me.inexactvim.paymentssystem.repository;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.Payment;

import java.util.Collection;

public interface PaymentRepository {

    Collection<Payment> loadUserPayments(long userId) throws DAOException;

    long savePayment(Payment payment) throws DAOException;

}
