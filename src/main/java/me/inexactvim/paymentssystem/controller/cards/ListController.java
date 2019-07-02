package me.inexactvim.paymentssystem.controller.cards;

import me.inexactvim.paymentssystem.controller.AbstractController;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.object.User;
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

@WebServlet("/cards/list")
public class ListController extends AbstractController {
    
    private static Logger logger = LoggerFactory.getLogger(ListController.class);

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User user = getSessionAttribute(session, "user");

        try {
            Collection<CreditCardInfo> creditCards;
            creditCards = creditCardService.getAccountCreditCards(user.getAccountNumber());
            if (creditCards.isEmpty()) {
                alertWarning("Credit cards have not been added yet", request, response);
            } else {
                request.setAttribute("creditCards", creditCards);
                forward(request, response);
            }
        } catch (DAOException e) {
            logger.error("An error occurred with database while loading user credit cards (email: " + user.getEmail() + ")", e);
            alertError("An error occurred while loading credit cards list. Please, try again later", request, response);
        }
    }

    protected void clearAttributes(HttpServletRequest request) {
        super.clearAttributes(request);
        request.removeAttribute("creditCards");
    }
}
