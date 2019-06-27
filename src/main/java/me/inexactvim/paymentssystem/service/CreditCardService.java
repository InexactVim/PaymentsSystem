package me.inexactvim.paymentssystem.service;

import me.inexactvim.paymentssystem.exception.CardIsExpiredException;
import me.inexactvim.paymentssystem.exception.CreditCardAlreadyAddedException;
import me.inexactvim.paymentssystem.exception.CreditCardNotFoundException;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.CreditCard;

import java.sql.Date;
import java.util.Collection;

public interface CreditCardService {

    Collection<CreditCard> getAccountCreditCards(long accountNumber) throws DAOException;

    void addCreditCard(long accountNumber, long creditCardNumber, short code, Date expirationDate) throws DAOException, CreditCardAlreadyAddedException, CardIsExpiredException;

    void removeCreditCard(long accountNumber, long creditCardNumber) throws DAOException, CreditCardNotFoundException;
}
