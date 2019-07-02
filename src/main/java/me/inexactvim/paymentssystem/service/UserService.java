package me.inexactvim.paymentssystem.service;

import me.inexactvim.paymentssystem.exception.user.EmailIsInUsageException;
import me.inexactvim.paymentssystem.exception.EmailMessagingException;
import me.inexactvim.paymentssystem.exception.user.IncorrectCredentialsException;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.User;

public interface UserService {

    User getUser(String email) throws DAOException, IncorrectCredentialsException;

    User checkCredentialsAndGetUser(String email, String password) throws DAOException, IncorrectCredentialsException;

    void checkPassword(User user, String password) throws IncorrectCredentialsException;

    User saveUser(String name, String surname, String email, String password) throws DAOException, EmailIsInUsageException;

    void resetUserPassword(String email) throws DAOException, IncorrectCredentialsException, EmailMessagingException;

}
