package me.inexactvim.paymentssystem.repository.sql;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.Payment;
import me.inexactvim.paymentssystem.repository.PaymentRepository;
import me.inexactvim.paymentssystem.sql.SqlDatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SqlPaymentRepository implements PaymentRepository {

    private SqlDatabaseManager databaseManager;

    public SqlPaymentRepository(SqlDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public Collection<Payment> loadAccountPayments(long accountNumber) throws DAOException {
        return databaseManager.executeQuery("SELECT * FROM payments WHERE sender_account_number=? OR recipient_account_number=?", resultSet -> {
            Collection<Payment> payments;

            if (resultSet.next()) {
                payments = new ArrayList<>();
                payments.add(fetchPaymentFromResultSet(resultSet));

                while (resultSet.next()) {
                    payments.add(fetchPaymentFromResultSet(resultSet));
                }
            } else {
                payments = Collections.emptyList();
            }

            return payments;
        }, accountNumber, accountNumber);
    }

    private Payment fetchPaymentFromResultSet(ResultSet resultSet) throws SQLException {
        Payment payment = new Payment();
        payment.setSenderAccountNumber(resultSet.getLong("sender_account_number"));
        payment.setRecipientAccountNumber(resultSet.getLong("recipient_account_number"));
        payment.setId(resultSet.getLong("id"));
        payment.setAmount(resultSet.getBigDecimal("amount"));
        payment.setComment(resultSet.getString("comment"));
        payment.setTimestamp(resultSet.getTimestamp("time"));
        return payment;
    }

    @Override
    public long savePayment(Payment payment) throws DAOException {
        return 0;
    }
}
