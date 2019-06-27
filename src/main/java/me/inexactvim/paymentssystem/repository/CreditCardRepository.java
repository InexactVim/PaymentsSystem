package me.inexactvim.paymentssystem.repository;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.CreditCard;

import java.util.Collection;

public interface CreditCardRepository {

    Collection<CreditCard> loadCreditCardsByAccountNumber(long accountNumber) throws DAOException;

    boolean exists(long accountNumber, long creditCardNUmber) throws DAOException;

    long saveCreditCard(CreditCard card) throws DAOException;

    int removeCreditCard(long accountNumber, long creditCardNumber) throws DAOException;

}
