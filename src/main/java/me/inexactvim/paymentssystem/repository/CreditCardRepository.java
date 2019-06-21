package me.inexactvim.paymentssystem.repository;

import me.inexactvim.paymentssystem.object.CreditCard;

import java.util.Collection;
import java.util.Optional;

public interface CreditCardRepository {

    Optional<CreditCard> loadByNumber(long number);

    Collection<CreditCard> loadByAccountNumber(long accountNumber);

    Collection<CreditCard> loadAll();

}
