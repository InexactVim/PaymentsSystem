package me.inexactvim.paymentssystem.repository;

import me.inexactvim.paymentssystem.object.Payment;

import java.util.Collection;
import java.util.Optional;

public interface PaymentRepository {

    Optional<Payment> loadById(long id);

    Collection<Payment> loadByUserAsSender(long userId);

    Collection<Payment> loadByUserAsRecipient(long userId);

    Collection<Payment> loadAll();

}
