package me.inexactvim.paymentssystem.service;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.card.CardAlreadyAddedException;
import me.inexactvim.paymentssystem.exception.card.CardIsExpiredException;
import me.inexactvim.paymentssystem.exception.card.CardNotFoundException;
import me.inexactvim.paymentssystem.object.CreditCard;
import me.inexactvim.paymentssystem.util.info.CreditCardInfo;

import java.sql.Date;
import java.util.Collection;

public interface CreditCardService {

    CreditCard getCreditCard(long number) throws DAOException, CardNotFoundException;

    Collection<CreditCardInfo> getAccountCreditCards(long accountNumber) throws DAOException;

    void addCreditCard(long accountNumber, long creditCardNumber, short code, Date expirationDate) throws DAOException, CardAlreadyAddedException, CardIsExpiredException;

    void removeCreditCard(long accountNumber, long creditCardNumber) throws DAOException, CardNotFoundException;
}
