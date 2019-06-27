package me.inexactvim.paymentssystem.controller.cards;

import me.inexactvim.paymentssystem.exception.CreditCardNotFoundException;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.IncorrectCredentialsException;
import me.inexactvim.paymentssystem.factory.ServiceFactory;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.CreditCard;
import me.inexactvim.paymentssystem.object.User;
import me.inexactvim.paymentssystem.service.CreditCardService;
import me.inexactvim.paymentssystem.service.UserService;
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

@WebServlet("/cards/remove")
public class RemoveController extends HttpServlet {

    private UserService userService;
    private CreditCardService creditCardService;

    @Override
    public void init() {
        userService = ServiceFactory.getUserService();
        creditCardService = ServiceFactory.getCreditCardService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);
        Account account = (Account) httpSession.getAttribute("account");
        User user = (User) httpSession.getAttribute("user");

        String password = req.getParameter("password");
        String cardInfoString = req.getParameter("card_number");

        long creditCardNumber;
        try {
            creditCardNumber = Long.parseLong(cardInfoString.split(",")[0].replace(" ", ""));
        } catch (NumberFormatException e) {
            alert(req, resp, "danger", "An error occurred. Can not validate card number");
            return;
        }

        try {
            userService.checkCredentialsAndGetUser(user.getEmail(), password);
        } catch (DAOException e) {
            alert(req, resp, "danger", "An error occurred. Please, try again later");
            return;
        } catch (IncorrectCredentialsException e) {
            alert(req, resp, "danger", "Password is incorrect");
            return;
        }

        try {
            creditCardService.removeCreditCard(account.getNumber(), creditCardNumber);
            if (loadSelectList(req, resp)) {
                alert(req, resp, "success", "Credit card successfully removed");
            }
        } catch (DAOException e) {
            alert(req, resp, "danger", "An error occurred. Please, try again later");
        } catch (CreditCardNotFoundException e) {
            alert(req, resp, "danger", "An error occurred. Can not find credit card");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (loadSelectList(req, resp)) {
            req.getRequestDispatcher("/cards/remove.jsp").forward(req, resp);
        }
    }

    private void clearAttributes(HttpServletRequest request) {
        request.removeAttribute("alert");
    }

    private void alert(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       String type,
                       String message) throws ServletException, IOException {
        clearAttributes(httpServletRequest);
        httpServletRequest.setAttribute("alert", "<p class=\"alert alert-" + type + "\">" + message + "</p>");
        httpServletRequest.getRequestDispatcher("/cards/remove.jsp").forward(httpServletRequest, httpServletResponse);
    }

    private boolean loadSelectList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        clearAttributes(req);
        Account account = (Account) session.getAttribute("account");
        Collection<CreditCard> creditCards;

        try {
            creditCards = creditCardService.getAccountCreditCards(account.getNumber());
        } catch (DAOException e) {
            req.setAttribute("creditCards", Collections.emptyList());
            alert(req, resp, "danger", "An error occurred while loading credit cards list. Please, try again later");
            return false;
        }

        session.setAttribute("creditCards", creditCards.stream()
                .map(CreditCardDisplay::new)
                .collect(Collectors.toList()));
        return true;
    }
}
