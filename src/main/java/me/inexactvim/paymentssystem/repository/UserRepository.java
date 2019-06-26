package me.inexactvim.paymentssystem.repository;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> loadByEmail(String email) throws DAOException;

    Optional<User> loadByAccountNumber(long accountNumber) throws DAOException;

    long saveUser(User user) throws DAOException;

    void updateUser(User user) throws DAOException;

}
