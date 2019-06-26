package me.inexactvim.paymentssystem.repository.sql;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.User;
import me.inexactvim.paymentssystem.object.UserRole;
import me.inexactvim.paymentssystem.repository.UserRepository;
import me.inexactvim.paymentssystem.sql.SqlDatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class SqlUserRepository implements UserRepository {

    private SqlDatabaseManager databaseManager;

    public SqlUserRepository(SqlDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    /*@Override
    public Optional<User> loadById(long id) {
        return Optional.ofNullable(databaseManager.executeQuery("SELECT * FROM `users` WHERE `id`=?",
                this::fetchUserFromResultSet, id));
    }*/

    @Override
    public Optional<User> loadByEmail(String email) throws DAOException {
        return Optional.ofNullable(databaseManager.executeQuery("SELECT * FROM users WHERE email=?",
                this::fetchUserFromResultSet,
                email));
    }

    private User fetchUserFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setAccountNumber(resultSet.getLong("account_number"));
            user.setRole(UserRole.valueOf(resultSet.getByte("role")));
            return user;
        } else {
            return null;
        }
    }

    @Override
    public Optional<User> loadByAccountNumber(long accountNumber) throws DAOException {
        return Optional.ofNullable(databaseManager.executeQuery("SELECT * FROM users WHERE account_number=?",
                this::fetchUserFromResultSet,
                accountNumber));
    }

    @Override
    public long saveUser(User user) throws DAOException {
        return databaseManager.customExecute(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, surname, email, password, role, account_number) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1, user.getName());
            statement.setObject(2, user.getSurname());
            statement.setObject(3, user.getEmail());
            statement.setObject(4, user.getPassword());
            statement.setObject(5, user.getRole().ordinal());
            statement.setObject(6, user.getAccountNumber());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

            throw new DAOException();
        });
    }

    @Override
    public void updateUser(User user) throws DAOException {
        int result = databaseManager.executeUpdate("UPDATE users SET name=?, surname=?, password=?, role=? WHERE id=?",
                user.getName(), user.getSurname(), user.getPassword(), user.getRole().ordinal(), user.getId());

        if (result == 0) {
            throw new DAOException();
        }
    }
}
