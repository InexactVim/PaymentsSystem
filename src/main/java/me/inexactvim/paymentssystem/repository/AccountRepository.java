package me.inexactvim.paymentssystem.repository;

import me.inexactvim.paymentssystem.object.Account;

import java.util.Collection;
import java.util.Optional;

public interface AccountRepository {

    Optional<Account> loadByNumber(long number);

    Optional<Account> loadByUserId(long userId);

    Collection<Account> loadAll();

}
