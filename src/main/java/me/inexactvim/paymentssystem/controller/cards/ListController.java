package me.inexactvim.paymentssystem.controller.cards;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.factory.ServiceFactory;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.CreditCard;
import me.inexactvim.paymentssystem.service.CreditCardService;
import me.inexactvim.paymentssystem.util.display.CreditCardDisplay;

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

@WebServlet("/cards/list")
public class ListController extends HttpServlet {

    private CreditCardService creditCardService;

    @Override
    public void init() {
        creditCardService = ServiceFactory.getCreditCardService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        clearAttributes(req);
        Account account = (Account) session.getAttribute("account");
        Collection<CreditCard> creditCards;

        try {
            creditCards = creditCardService.getAccountCreditCards(account.getNumber());
        } catch (DAOException e) {
            req.setAttribute("creditCards", Collections.emptyList());
            alert(req, resp, "An error occurred while loading credit cards list. Please, try again later");
            return;
        }

        req.setAttribute("creditCards", creditCards.stream()
                .map(CreditCardDisplay::new)
                .collect(Collectors.toList()));
        req.getRequestDispatcher("/cards/list.jsp").forward(req, resp);
    }

    private void clearAttributes(HttpServletRequest request) {
        request.removeAttribute("alert");
        request.removeAttribute("creditCards");
    }

    private void alert(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       String message) throws ServletException, IOException {
        clearAttributes(httpServletRequest);
        httpServletRequest.setAttribute("alert", "<p class=\"alert alert-danger\">" + message + "</p>");
        httpServletRequest.getRequestDispatcher("/cards/list.jsp").forward(httpServletRequest, httpServletResponse);
    }

}
