package me.inexactvim.paymentssystem.controller.cards;

import me.inexactvim.paymentssystem.controller.AbstractController;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.card.CardNotFoundException;
import me.inexactvim.paymentssystem.exception.user.IncorrectCredentialsException;
import me.inexactvim.paymentssystem.object.User;
import me.inexactvim.paymentssystem.util.NumberUtil;
import me.inexactvim.paymentssystem.util.info.CreditCardInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/cards/remove")
public class RemoveController extends AbstractController {
    
    private static Logger logger = LoggerFactory.getLogger(RemoveController.class);

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User user = getSessionAttribute(session, "user");
        String password = request.getParameter("password");
        String cardInfoString = request.getParameter("card_info");

        if (cardInfoString == null) {
            alertWarning("There are no cards to remove", request, response);
            return;
        }

        long creditCardNumber;
        try {
            creditCardNumber = Long.parseLong(cardInfoString.split(",")[0].replace(" ", ""));
            userService.checkCredentialsAndGetUser(user.getEmail(), password);
            creditCardService.removeCreditCard(user.getAccountNumber(), creditCardNumber);
            Collection<CreditCardInfo> creditCardsInfo = getSessionAttribute(session, "creditCards");
            creditCardsInfo.removeIf(creditCardInfo -> (creditCardInfo.getNumber() + ", " + creditCardInfo.getExpirationDate()).equals(cardInfoString));
            if (creditCardsInfo.isEmpty()) {
                session.removeAttribute("creditCards");
            }
            alertInfo("Credit card successfully removed", request, response);
            logger.info("User with email " + user.getEmail() + " has just removed credit card with number " + NumberUtil.creditCardNumberFormat(creditCardNumber));
        } catch (DAOException e) {
            logger.error("An error occurred with database while loading user credit cards (email: " + user.getEmail() + ")", e);
            alertError("An error occurred. Please, try again later", request, response);
        } catch (CardNotFoundException e) {
            logger.error("An error occurred with database while finding credit card (string: " + cardInfoString + ")", e);
            alertError("An error occurred. Can not find credit card", request, response);
        } catch (IncorrectCredentialsException e) {
            alertError("Password is incorrect", request, response);
        } catch (NumberFormatException e) {
            logger.error("An error occurred while parsing credit card number (string: " + cardInfoString + ")", e);
            alertError("An error occurred. Can not validate card number", request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        clearAttributes(request);
        HttpSession session = request.getSession(true);
        User user = getSessionAttribute(session, "user");

        try {
            Collection<CreditCardInfo> creditCards = creditCardService.getAccountCreditCards(user.getAccountNumber());
            if (creditCards.isEmpty()) {
                session.removeAttribute("creditCards");
                alertWarning("There are no cards to remove", request, response);
            } else {
                session.setAttribute("creditCards", creditCards);
                forward(request, response);
            }
        } catch (DAOException e) {
            logger.error("An error occurred with database while loading user credit cards (email: " + user.getEmail() + ")", e);
            alertError("An error occurred while loading credit cards list. Please, try again later", request, response);
        }
    }
}
