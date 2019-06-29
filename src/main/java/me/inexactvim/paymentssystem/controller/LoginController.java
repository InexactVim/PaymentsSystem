package me.inexactvim.paymentssystem.controller;

import me.inexactvim.paymentssystem.exception.account.AccountNotFoundException;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.user.IncorrectCredentialsException;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends AbstractController {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userService.checkCredentialsAndGetUser(email, password);
            Account account = accountService.getAccount(user.getAccountNumber());
            httpSession.invalidate();
            httpSession = request.getSession(true);
            httpSession.setMaxInactiveInterval(15 * 60);
            httpSession.setAttribute("account", account);
            httpSession.setAttribute("user", user);
            response.sendRedirect("/user");
        } catch (DAOException e) {
            alertError("An error occurred. Please, try again later", request, response);
        } catch (IncorrectCredentialsException e) {
            alertError(e.getMessage(), request, response);
        } catch (AccountNotFoundException e) {
            alertError("Your account is not available. Contact the administrator", request, response);
        }
    }
}
