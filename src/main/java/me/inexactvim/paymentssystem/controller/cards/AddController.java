package me.inexactvim.paymentssystem.controller.cards;

import me.inexactvim.paymentssystem.controller.AbstractController;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.card.CardAlreadyAddedException;
import me.inexactvim.paymentssystem.exception.card.CardIsExpiredException;
import me.inexactvim.paymentssystem.object.User;
import me.inexactvim.paymentssystem.util.DateUtil;
import me.inexactvim.paymentssystem.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;

@WebServlet("/cards/add")
public class AddController extends AbstractController {

    private static Logger logger = LoggerFactory.getLogger(AddController.class);
    
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User user = getSessionAttribute(session, "user");

        long cardNumber;
        try {
            cardNumber = Long.parseLong(request.getParameter("card_number"));
        } catch (NumberFormatException e) {
            alertError("Please, enter the correct card number in the \"Card number\" field", request, response);
            return;
        }

        short code;
        try {
            code = Short.parseShort(request.getParameter("code"));
        } catch (NumberFormatException e) {
            alertError("Please, enter the correct card code in the \"Code\" field", request, response);
            return;
        }

        Date expirationDate;
        try {
            expirationDate = DateUtil.parse(request.getParameter("expiration_date"));
        } catch (ParseException e) {
            alertError("Please, enter the correct expiration date in the \"Expiration date\" field", request, response);
            return;
        }

        try {
            creditCardService.addCreditCard(user.getAccountNumber(), cardNumber, code, expirationDate);
            alertSuccess("Credit card added", request, response);
            logger.info("Added new credit card (" + NumberUtil.creditCardNumberFormat(cardNumber) + ")");
        } catch (DAOException e) {
            logger.error("An error occurred with database while adding a new card (" + NumberUtil.creditCardNumberFormat(cardNumber) + ")", e);
            alertError("An error occurred. Please, try again later", request, response);
        } catch (CardAlreadyAddedException e) {
            alertError("This card is already added", request, response);
        } catch (CardIsExpiredException e) {
            alertError("Can not add a new credit card. This card is expired", request, response);
        }
    }
}
