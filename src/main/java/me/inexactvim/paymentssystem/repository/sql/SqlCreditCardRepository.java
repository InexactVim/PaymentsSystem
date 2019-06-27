package me.inexactvim.paymentssystem.repository.sql;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.CreditCard;
import me.inexactvim.paymentssystem.repository.CreditCardRepository;
import me.inexactvim.paymentssystem.sql.SqlDatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SqlCreditCardRepository implements CreditCardRepository {

    private SqlDatabaseManager databaseManager;

    public SqlCreditCardRepository(SqlDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public Collection<CreditCard> loadCreditCardsByAccountNumber(long accountNumber) throws DAOException {
        return databaseManager.executeQuery("SELECT * FROM credit_cards WHERE account_number=?", resultSet -> {
            Collection<CreditCard> creditCards;

            if (resultSet.next()) {
                creditCards = new ArrayList<>();
                creditCards.add(fetchCreditCardFromResultSet(resultSet));

                while (resultSet.next()) {
                    creditCards.add(fetchCreditCardFromResultSet(resultSet));
                }
            } else {
                creditCards = Collections.emptyList();
            }

            return creditCards;
        }, accountNumber);
    }

    private CreditCard fetchCreditCardFromResultSet(ResultSet resultSet) throws SQLException {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(resultSet.getLong("id"));
        creditCard.setNumber(resultSet.getLong("number"));
        creditCard.setAccountNumber(resultSet.getLong("account_number"));
        creditCard.setCode(resultSet.getShort("code"));
        creditCard.setExpirationDate(resultSet.getDate("expiration_date"));
        return creditCard;
    }

    @Override
    public boolean exists(long accountNumber, long creditCardNUmber) throws DAOException {
        return databaseManager.executeQuery("SELECT id FROM credit_cards WHERE account_number=? AND number=?", ResultSet::next, accountNumber, creditCardNUmber);
    }

    @Override
    public long saveCreditCard(CreditCard card) throws DAOException {
        return databaseManager.customExecute(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO credit_cards (number, code, expiration_date, account_number) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1, card.getNumber());
            statement.setObject(2, card.getCode());
            statement.setObject(3, card.getExpirationDate());
            statement.setObject(4, card.getAccountNumber());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

            throw new DAOException();
        });
    }

    @Override
    public int removeCreditCard(long accountNumber, long creditCardNumber) throws DAOException {
        return databaseManager.executeUpdate("DELETE FROM credit_cards WHERE account_number=? AND number=?", accountNumber, creditCardNumber);
    }
}
