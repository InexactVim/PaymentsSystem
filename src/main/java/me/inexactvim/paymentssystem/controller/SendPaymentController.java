package me.inexactvim.paymentssystem.controller;

import me.inexactvim.paymentssystem.exception.AccountBlockedException;
import me.inexactvim.paymentssystem.exception.AccountNotFoundException;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.NegativeBalanceException;
import me.inexactvim.paymentssystem.factory.ServiceFactory;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.service.PaymentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/send_payment")
public class SendPaymentController extends HttpServlet {

    private PaymentService paymentService;

    @Override
    public void init() {
        paymentService = ServiceFactory.getPaymentService();
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);
        Account account = (Account) httpSession.getAttribute("account");

        long recipientAccountNumber;
        try {
            recipientAccountNumber = (long) req.getAttribute("account_number");
        } catch (ClassCastException e) {
            alert(req, resp, "danger", "Please, enter the correct account number in the \"Account number\" field");
            return;
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal((String) req.getAttribute("payment_amount"));
        } catch (NumberFormatException e) {
            alert(req, resp, "danger", "Please, enter the correct number in the \"Payment amount\" field");
            return;
        }

        String comment = (String) req.getAttribute("comment");

        try {
            paymentService.createPayment(account.getNumber(), recipientAccountNumber, amount, comment);
            alert(req, resp, "success", "Payment successfully completed");
        } catch (DAOException e) {
            alert(req, resp, "danger", "An error occurred. Please, try again later");
        } catch (AccountNotFoundException e) {
            alert(req, resp, "danger", "Account not found");
        } catch (AccountBlockedException e) {
            alert(req, resp, "danger", e.getMessage());
        } catch (NegativeBalanceException e) {
            alert(req, resp, "danger", "You do not have enough money to make a transfer");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        clearAttributes(req);
        req.getRequestDispatcher("/send_payment.jsp").forward(req, resp);
    }

    private void clearAttributes(HttpServletRequest request) {
        request.removeAttribute("account_number");
        request.removeAttribute("payment_amount");
        request.removeAttribute("comment");
        request.removeAttribute("alert");
    }

    private void alert(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       String type,
                       String message) throws ServletException, IOException {
        clearAttributes(httpServletRequest);
        httpServletRequest.setAttribute("alert", "<p class=\"alert alert-" + type + "\">" + message + "</p>");
        httpServletRequest.getRequestDispatcher("/send_payment.jsp").forward(httpServletRequest, httpServletResponse);
    }

}
