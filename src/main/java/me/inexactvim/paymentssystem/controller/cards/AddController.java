package me.inexactvim.paymentssystem.controller.cards;

import me.inexactvim.paymentssystem.exception.CardIsExpiredException;
import me.inexactvim.paymentssystem.exception.CreditCardAlreadyAddedException;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.factory.ServiceFactory;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.service.CreditCardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/cards/add")
public class AddController extends HttpServlet {

    private static SimpleDateFormat expirationDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    private CreditCardService creditCardService;

    @Override
    public void init() {
        creditCardService = ServiceFactory.getCreditCardService();
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);
        Account account = (Account) httpSession.getAttribute("account");

        long cardNumber;
        try {
            cardNumber = Long.parseLong(req.getParameter("card_number"));
        } catch (NumberFormatException e) {
            alert(req, resp, "danger", "Please, enter the correct card number in the \"Card number\" field");
            return;
        }

        short code;
        try {
            code = Short.parseShort(req.getParameter("code"));
        } catch (NumberFormatException e) {
            alert(req, resp, "danger", "Please, enter the correct card code in the \"Code\" field");
            return;
        }

        Date expirationDate;
        try {
            expirationDate = new Date(expirationDateFormat.parse(req.getParameter("expiration_date")).getTime());
        } catch (ParseException e) {
            alert(req, resp, "danger", "Please, enter the correct expiration date in the \"Expiration date\" field");
            return;
        }

        try {
            creditCardService.addCreditCard(account.getNumber(), cardNumber, code, expirationDate);
            alert(req, resp, "success", "Credit card added");
        } catch (DAOException e) {
            e.printStackTrace();
            alert(req, resp, "danger", "An error occurred. Please, try again later");
        } catch (CreditCardAlreadyAddedException e) {
            alert(req, resp, "danger", "This card is already added");
        } catch (CardIsExpiredException e) {
            alert(req, resp, "danger", "Can not add a new credit card. This card is expired");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        clearAttributes(req);
        req.getRequestDispatcher("/cards/add.jsp").forward(req, resp);
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
        httpServletRequest.getRequestDispatcher("/cards/add.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
