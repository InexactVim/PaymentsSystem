package me.inexactvim.paymentssystem.controller.account;

import me.inexactvim.paymentssystem.controller.AbstractController;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.account.AccountNotFoundException;
import me.inexactvim.paymentssystem.exception.card.CardNotFoundException;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.CreditCard;
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
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@WebServlet("/account/refill")
public class RefillController extends AbstractController {

    private static Logger logger = LoggerFactory.getLogger(RefillController.class);

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User user = getSessionAttribute(session, "user");
        String cardInfoString = request.getParameter("card_info");
        String refillAmountString = request.getParameter("refill_amount");

        if (cardInfoString == null) {
            alertWarning("There are no cards from which you can refill your account", request, response);
            return;
        }

        try {
            long creditCardNumber = Long.parseLong(cardInfoString.split(",")[0].replace(" ", ""));
            CreditCard creditCard = creditCardService.getCreditCard(creditCardNumber);
            if (creditCard.getExpirationDate().before(new Date())) {
                alertError("Can not refill account. This card is expired", request, response);
                return;
            }
        } catch (NumberFormatException e) {
            logger.error("Can not validate card number", e);
            alertError("An error occurred. Can not validate card number", request, response);
            return;
        } catch (DAOException e) {
            logger.error("An error occurred with database while refilling account", e);
            alertError("An error occurred. Please, try again later", request, response);
            return;
        } catch (CardNotFoundException e) {
            logger.error("Unable to recognize credit card (" + cardInfoString + ")", e);
            alertError("Unable to recognize this credit card. Contact the administrator", request, response);
            return;
        }

        BigDecimal refillAmount;
        try {
            refillAmount = new BigDecimal(refillAmountString);
            Account account = accountService.getAccount(user.getAccountNumber());
            accountService.depositAccount(account, refillAmount);
            alertSuccess("Account successfully refilled", request, response);
            logger.info("Account " + NumberUtil.accountNumberFormat(account.getNumber()) + " was refilled by " + refillAmount + "$");
        } catch (NumberFormatException e) {
            alertError("Please, enter the correct number in the \"Refill amount\" field", request, response);
        } catch (DAOException e) {
            logger.error("An error occurred with database while refilling account", e);
            alertError("An error occurred. Please, try again later", request, response);
        } catch (AccountNotFoundException e) {
            logger.error("Unable to recognize user account (number: " + user.getAccountNumber() + ")", e);
            alertError("Account not found. Contact the administrator", request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User user = getSessionAttribute(session, "user");

        try {
            Collection<CreditCardInfo> creditCards = creditCardService.getAccountCreditCards(user.getAccountNumber());
            if (creditCards.isEmpty()) {
                request.removeAttribute("creditCards");
                alertWarning("You must add at least one card to refill your account", request, response);
            } else {
                request.setAttribute("creditCards", creditCards);
                forward(request, response);
            }
        } catch (DAOException e) {
            logger.error("An error occurred with database while loading credit cards list for user with email " + user.getEmail(), e);
            alertError("An error occurred while loading credit cards list. Please, try again later", request, response);
        }
    }
}
