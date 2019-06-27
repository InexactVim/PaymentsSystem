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
            clearAttributes(req);
            resp.sendRedirect("/user");
        } catch (DAOException e) {
            alert(req, resp, "An error occurred. Please, try again later");
        } catch (IncorrectCredentialsException e) {
            alert(req, resp, e.getMessage());
        } catch (AccountNotFoundException e) {
            alert(req, resp, "Your account is not available. Contact the administrator");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        clearAttributes(req);
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    private void clearAttributes(HttpServletRequest request) {
        /*request.removeAttribute("email");
        request.removeAttribute("password");*/
        request.removeAttribute("alert");
    }

    private void alert(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       String message) throws ServletException, IOException {
        clearAttributes(httpServletRequest);
        httpServletRequest.setAttribute("alert", "<p class=\"alert alert-danger\">" + message + "</p>");
        httpServletRequest.getRequestDispatcher("/login.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {
        SqlDatabaseFactory.getDatabaseManager().closeConnection();
    }
}
