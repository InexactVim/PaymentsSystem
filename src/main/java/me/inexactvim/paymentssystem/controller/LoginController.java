package me.inexactvim.paymentssystem.controller;

import me.inexactvim.paymentssystem.exception.AccountNotFoundException;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.IncorrectCredentialsException;
import me.inexactvim.paymentssystem.factory.ServiceFactory;
import me.inexactvim.paymentssystem.factory.SqlDatabaseFactory;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.object.User;
import me.inexactvim.paymentssystem.service.AccountService;
import me.inexactvim.paymentssystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private UserService userService;
    private AccountService accountService;

    @Override
    public void init() {
        userService = ServiceFactory.getUserService();
        accountService = ServiceFactory.getAccountService();
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User user = userService.checkCredentialsAndGetUser(email, password);
            Account account = accountService.getAccount(user.getAccountNumber());
            httpSession.invalidate();
            httpSession = req.getSession(true);
            httpSession.setMaxInactiveInterval(15 * 60);
            httpSession.setAttribute("account", account);
            httpSession.setAttribute("user", user);
            clearAttributes(httpSession);
            resp.sendRedirect("/user");
        } catch (DAOException e) {
            alertError(httpSession, req, resp, "An error occurred. Please, try again later");
        } catch (IncorrectCredentialsException e) {
            alertError(httpSession, req, resp, e.getMessage());
        } catch (AccountNotFoundException e) {
            alertError(httpSession, req, resp, "Your account is not available. Contact the administrator");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        clearAttributes(req.getSession(true));
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    private void clearAttributes(HttpSession session) {
        session.removeAttribute("email");
        session.removeAttribute("password");
        session.removeAttribute("alert");
    }

    private void alertError(HttpSession httpSession,
                            HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse,
                            String message) throws ServletException, IOException {
        clearAttributes(httpSession);
        httpSession.setAttribute("alert", "<p class=\"alert alert-danger\">" + message + "</p>");
        httpServletRequest.getRequestDispatcher("/login.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {
        SqlDatabaseFactory.getDatabaseManager().closeConnection();
    }
}
