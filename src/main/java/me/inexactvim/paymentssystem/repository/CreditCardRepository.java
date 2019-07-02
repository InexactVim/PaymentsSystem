package me.inexactvim.paymentssystem.repository;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.CreditCard;

import java.util.Collection;
import java.util.Optional;

public interface CreditCardRepository {

    Optional<CreditCard> loadCreditCard(long number) throws DAOException;

    Collection<CreditCard> loadCreditCardsByAccountNumber(long accountNumber) throws DAOException;

    boolean exists(long accountNumber, long creditCardNUmber) throws DAOException;

    long saveCreditCard(CreditCard card) throws DAOException;

    int removeCreditCard(long accountNumber, long creditCardNumber) throws DAOException;

}
