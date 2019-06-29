package me.inexactvim.paymentssystem.controller.cards;

import me.inexactvim.paymentssystem.controller.AbstractController;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.CreditCard;
import me.inexactvim.paymentssystem.util.display.CreditCardDisplay;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@WebServlet("/cards/list")
public class ListController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Account account = getSessionAttribute(session, "account");
        Collection<CreditCard> creditCards;

        try {
            creditCards = creditCardService.getAccountCreditCards(account.getNumber());
        } catch (DAOException e) {
            alertError("An error occurred while loading credit cards list. Please, try again later", request, response);
            return;
        }

        request.setAttribute("creditCards", creditCards.stream()
                .map(CreditCardDisplay::new)
                .collect(Collectors.toList()));
        request.getRequestDispatcher("/cards/list.jsp").forward(request, response);
    }

    protected void clearAttributes(HttpServletRequest request) {
        super.clearAttributes(request);
        request.removeAttribute("creditCards");
    }
}
