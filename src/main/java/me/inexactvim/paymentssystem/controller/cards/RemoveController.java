package me.inexactvim.paymentssystem.controller.cards;

import me.inexactvim.paymentssystem.controller.AbstractController;
import me.inexactvim.paymentssystem.exception.card.CardNotFoundException;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.user.IncorrectCredentialsException;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.CreditCard;
import me.inexactvim.paymentssystem.object.User;
import me.inexactvim.paymentssystem.util.display.CreditCardDisplay;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@WebServlet("/cards/remove")
public class RemoveController extends AbstractController {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User user = getSessionAttribute(session, "user");
        Account account = getSessionAttribute(session, "account");
        String password = request.getParameter("password");
        String cardInfoString = request.getParameter("card_info");

        if (cardInfoString == null) {
            alertWarning("There are no cards to delete", request, response);
            return;
        }

        long creditCardNumber;
        try {
            creditCardNumber = Long.parseLong(cardInfoString.split(",")[0].replace(" ", ""));
        } catch (NumberFormatException e) {
            alertError("An error occurred. Can not validate card number", request, response);
            return;
        }

        try {
            userService.checkCredentialsAndGetUser(user.getEmail(), password);
        } catch (DAOException e) {
            alertError("An error occurred. Please, try again later", request, response);
            return;
        } catch (IncorrectCredentialsException e) {
            alertError("Password is incorrect", request, response);
            return;
        }

        try {
            creditCardService.removeCreditCard(account.getNumber(), creditCardNumber);
            Collection<CreditCardDisplay> creditCardDisplays = getSessionAttribute(session, "creditCards");
            creditCardDisplays.removeIf(creditCardDisplay -> (creditCardDisplay.getNumber() + ", " + creditCardDisplay.getExpirationDate()).equals(cardInfoString));
            alertSuccess("Credit card successfully removed", request, response);
        } catch (DAOException e) {
            alertError("An error occurred. Please, try again later", request, response);
        } catch (CardNotFoundException e) {
            alertError("An error occurred. Can not find credit card", request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        clearAttributes(request);
        HttpSession session = request.getSession(true);

        if (loadCreditCards(getSessionAttribute(session, "account"), session)) {
            forward(request, response);
        } else {
            alertError("An error occurred while loading credit cards list. Please, try again later", request, response);
        }
    }

    private boolean loadCreditCards(Account account, HttpSession session) {
        Collection<CreditCard> creditCards;

        try {
            creditCards = creditCardService.getAccountCreditCards(account.getNumber());
        } catch (DAOException e) {
            return false;
        }

        session.setAttribute("creditCards", creditCards.stream()
                .map(CreditCardDisplay::new)
                .collect(Collectors.toList()));
        return true;
    }
}
