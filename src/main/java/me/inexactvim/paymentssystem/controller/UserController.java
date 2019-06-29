package me.inexactvim.paymentssystem.controller;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.Payment;
import me.inexactvim.paymentssystem.util.display.AccountDisplay;
import me.inexactvim.paymentssystem.util.display.PaymentDisplay;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@WebServlet("/user")
public class UserController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        clearAttributes(req);
        Account account = getSessionAttribute(session, "account");
        req.setAttribute("accountDisplay", new AccountDisplay(account));
        Collection<Payment> payments;

        try {
            payments = paymentService.getAccountPayments(account.getNumber());
        } catch (DAOException e) {
            alertError("An error occurred while loading payments history. Please, try again later", req, resp);
            return;
        }

        req.setAttribute("payments", payments.stream()
                .map(payment -> new PaymentDisplay(account.getNumber(), payment))
                .collect(Collectors.toList()));
        req.getRequestDispatcher("/user.jsp").forward(req, resp);
    }

    protected void clearAttributes(HttpServletRequest request) {
        super.clearAttributes(request);
        request.removeAttribute("payments");
    }
}
