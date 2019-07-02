package me.inexactvim.paymentssystem.controller.account;

import me.inexactvim.paymentssystem.controller.AbstractController;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.account.AccountBlockedException;
import me.inexactvim.paymentssystem.exception.account.AccountNotFoundException;
import me.inexactvim.paymentssystem.exception.user.IncorrectCredentialsException;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.User;
import me.inexactvim.paymentssystem.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/account/block")
public class BlockController extends AbstractController {

    private static Logger logger = LoggerFactory.getLogger(BlockController.class);

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User user = getSessionAttribute(session, "user");
        String password = request.getParameter("password");

        try {
            userService.checkPassword(user, password);
            Account account = accountService.getAccount(user.getAccountNumber());
            accountService.blockAccount(account);
            alertInfo("Account successfully was blocked", request, response);
            logger.info("Account #" + NumberUtil.accountNumberFormat(user.getAccountNumber()) + " blocked");
        } catch (IncorrectCredentialsException | AccountBlockedException e) {
            logger.error("User with email " + user.getEmail() + " tried to block account (error message: " + e.getMessage());
            alertError(e.getMessage(), request, response);
        } catch (DAOException e) {
            logger.error("An error occurred with database while blocking account", e);
            alertError("An error occurred. Please, try again later", request, response);
        } catch (AccountNotFoundException e) {
            logger.error("An error occurred while blocking account (account not found)", e);
            alertError("Account not found. Contact the administrator", request, response);
        }
    }
}
