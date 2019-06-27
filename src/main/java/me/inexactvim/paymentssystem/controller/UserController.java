package me.inexactvim.paymentssystem.controller;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.factory.ServiceFactory;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.Payment;
import me.inexactvim.paymentssystem.service.PaymentService;
import me.inexactvim.paymentssystem.util.NumberUtil;
import me.inexactvim.paymentssystem.util.display.PaymentDisplay;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@WebServlet("/user")
public class UserController extends HttpServlet {

    private PaymentService paymentService;

    @Override
    public void init() {
        paymentService = ServiceFactory.getPaymentService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        clearAttributes(req);
        Account account = (Account) session.getAttribute("account");
        req.setAttribute("accountBalance", NumberUtil.amountFormat(account.getBalance()));
        req.setAttribute("accountNumber", NumberUtil.accountNumberFormat(account.getNumber()));
        Collection<Payment> payments;

        try {
            payments = paymentService.getAccountPayments(account.getNumber());
        } catch (DAOException e) {
            req.setAttribute("payments", Collections.emptyList());
            alert(req, resp, "An error occurred while loading payments history. Please, try again later");
            return;
        }

        req.setAttribute("payments", payments.stream()
                .map(payment -> new PaymentDisplay(account.getNumber(), payment))
                .collect(Collectors.toList()));
        req.getRequestDispatcher("/user.jsp").forward(req, resp);
    }

    private void clearAttributes(HttpServletRequest request) {
        request.removeAttribute("alert");
        request.removeAttribute("payments");
    }

    private void alert(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       String message) throws ServletException, IOException {
        clearAttributes(httpServletRequest);
        httpServletRequest.setAttribute("alert", "<p class=\"alert alert-danger\">" + message + "</p>");
        httpServletRequest.getRequestDispatcher("/user.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
