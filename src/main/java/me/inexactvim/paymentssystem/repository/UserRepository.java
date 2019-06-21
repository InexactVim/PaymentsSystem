package me.inexactvim.paymentssystem.repository;

import me.inexactvim.paymentssystem.object.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> loadById(long id);

    Optional<User> loadByEmail(String email);

    boolean exists(String email);

    Optional<User> loadByAccountNumber(long accountNumber);

}
