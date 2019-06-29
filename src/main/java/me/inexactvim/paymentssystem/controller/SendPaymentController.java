package me.inexactvim.paymentssystem.controller;

import me.inexactvim.paymentssystem.exception.account.AccountBlockedException;
import me.inexactvim.paymentssystem.exception.account.AccountNotFoundException;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.account.NegativeBalanceException;
import me.inexactvim.paymentssystem.object.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/send_payment")
public class SendPaymentController extends AbstractController {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Account account = getSessionAttribute(session, "account");

        long recipientAccountNumber;
        try {
            recipientAccountNumber = Long.parseLong(request.getParameter("account_number"));
        } catch (ClassCastException e) {
            alertError("Please, enter the correct account number in the \"Account number\" field", request, response);
            return;
        }

        if (recipientAccountNumber == account.getNumber()) {
            alertError("You can not send payments to your account", request, response);
            return;
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(request.getParameter("payment_amount"));
        } catch (NumberFormatException e) {
            alertError("Please, enter the correct number in the \"Payment amount\" field", request, response);
            return;
        }

        String comment = request.getParameter("comment");

        try {
            paymentService.createPayment(account.getNumber(), recipientAccountNumber, amount, comment);
            alertSuccess("Payment successfully completed", request, response);
        } catch (DAOException e) {
            alertError("An error occurred. Please, try again later", request, response);
        } catch (AccountNotFoundException e) {
            alertError("Account not found", request, response);
        } catch (AccountBlockedException e) {
            alertError(e.getMessage(), request, response);
        } catch (NegativeBalanceException e) {
            alertError("You do not have enough money to make a transfer", request, response);
        }
    }
}
