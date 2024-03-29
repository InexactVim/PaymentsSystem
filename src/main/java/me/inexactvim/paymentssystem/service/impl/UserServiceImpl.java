package me.inexactvim.paymentssystem.service.impl;

import me.inexactvim.paymentssystem.exception.*;
import me.inexactvim.paymentssystem.exception.user.EmailIsInUsageException;
import me.inexactvim.paymentssystem.exception.user.IncorrectCredentialsException;
import me.inexactvim.paymentssystem.factory.ServiceFactory;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.User;
import me.inexactvim.paymentssystem.object.UserRole;
import me.inexactvim.paymentssystem.repository.UserRepository;
import me.inexactvim.paymentssystem.security.PasswordEncryption;
import me.inexactvim.paymentssystem.service.UserService;
import me.inexactvim.paymentssystem.util.RandomString;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncryption passwordEncryption;

    public UserServiceImpl(UserRepository userRepository, PasswordEncryption passwordEncryption) {
        this.userRepository = userRepository;
        this.passwordEncryption = passwordEncryption;
    }

    @Override
    public User getUser(String email) throws DAOException, IncorrectCredentialsException {
        return userRepository.loadByEmail(email)
                .orElseThrow(() -> new IncorrectCredentialsException("User not found"));
    }

    @Override
    public User checkCredentialsAndGetUser(String email, String password) throws DAOException, IncorrectCredentialsException {
        User user = getUser(email);
        checkPassword(user, password);
        return user;
    }

    @Override
    public void checkPassword(User user, String password) throws IncorrectCredentialsException {
        if (!passwordEncryption.isValid(password, user.getPassword())) {
            throw new IncorrectCredentialsException("User password is incorrect");
        }
    }

    @Override
    public User saveUser(String name, String surname, String email, String password) throws DAOException, EmailIsInUsageException {
        Optional<User> userOptional = userRepository.loadByEmail(email);

        if (userOptional.isPresent()) {
            throw new EmailIsInUsageException();
        }

        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(passwordEncryption.encrypt(password));
        user.setRole(UserRole.CLIENT);
        Account account = ServiceFactory.getAccountService().createAccount();
        user.setAccountNumber(account.getNumber());
        long userId = userRepository.saveUser(user);
        user.setId(userId);

        return user;
    }

    @Override
    public void resetUserPassword(String email) throws DAOException, IncorrectCredentialsException, EmailMessagingException {
        User user = getUser(email);
        String oldPassword = user.getPassword();
        String newPassword = new RandomString(8).nextString();
        user.setPassword(passwordEncryption.encrypt(newPassword));

        try {
            userRepository.updateUser(user);
        } catch (DAOException e) {
            user.setPassword(oldPassword);
            throw new DAOException(e);
        }

        try {
            ServiceFactory.getEmailService().sendMail(user.getEmail(),
                    "Password recovery",
                    String.format("Dear, %s %s, your new password is %s", user.getName(), user.getSurname(), newPassword));
        } catch (EmailMessagingException e) {
            user.setPassword(oldPassword);
            userRepository.updateUser(user);
            throw new EmailMessagingException(e);
        }
    }
}
