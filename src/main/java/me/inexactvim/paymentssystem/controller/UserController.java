package me.inexactvim.paymentssystem.controller;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.account.AccountNotFoundException;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.User;
import me.inexactvim.paymentssystem.util.info.AccountInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user")
public class UserController extends AbstractController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        clearAttributes(request);
        User user = getSessionAttribute(session, "user");

        try {
            Account account = accountService.getAccount(user.getAccountNumber());
            request.setAttribute("accountDisplay", new AccountInfo(account));
            request.setAttribute("payments", paymentService.getAccountPayments(account.getNumber()));
            forward(request, response);
        } catch (DAOException e) {
            logger.error("An error occurred with database", e);
            alertError("An error occurred while loading payments history. Please, try again later", request, response);
        } catch (AccountNotFoundException e) {
            logger.error("An error occurred with finding account (user email: " + user.getEmail() + ")", e);
            alertError("Account not found. Contact the administrator", request, response);
        }
    }

    protected void clearAttributes(HttpServletRequest request) {
        super.clearAttributes(request);
        request.removeAttribute("payments");
    }
}
