package me.inexactvim.paymentssystem.service.impl;

import me.inexactvim.paymentssystem.exception.card.CardIsExpiredException;
import me.inexactvim.paymentssystem.exception.card.CardAlreadyAddedException;
import me.inexactvim.paymentssystem.exception.card.CardNotFoundException;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.CreditCard;
import me.inexactvim.paymentssystem.repository.CreditCardRepository;
import me.inexactvim.paymentssystem.service.CreditCardService;

import java.sql.Date;
import java.util.Collection;

public class CreditCardServiceImpl implements CreditCardService {

    private CreditCardRepository creditCardRepository;

    public CreditCardServiceImpl(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public Collection<CreditCard> getAccountCreditCards(long accountNumber) throws DAOException {
        return creditCardRepository.loadCreditCardsByAccountNumber(accountNumber);
    }

    @Override
    public void addCreditCard(long accountNumber, long creditCardNumber, short code, Date expirationDate) throws DAOException, CardAlreadyAddedException, CardIsExpiredException {
        if (expirationDate.before(new Date(System.currentTimeMillis()))) {
            throw new CardIsExpiredException();
        }

        if (creditCardRepository.exists(accountNumber, creditCardNumber)) {
            throw new CardAlreadyAddedException();
        }

        CreditCard creditCard = new CreditCard();
        creditCard.setNumber(creditCardNumber);
        creditCard.setExpirationDate(expirationDate);
        creditCard.setCode(code);
        creditCard.setAccountNumber(accountNumber);
        long id = creditCardRepository.saveCreditCard(creditCard);
        creditCard.setId(id);
    }

    @Override
    public void removeCreditCard(long accountNumber, long creditCardNumber) throws DAOException, CardNotFoundException {
        if (creditCardRepository.removeCreditCard(accountNumber, creditCardNumber) == 0) {
            throw new CardNotFoundException();
        }
    }
}
