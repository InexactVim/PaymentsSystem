package me.inexactvim.paymentssystem.controller;

import me.inexactvim.paymentssystem.exception.AccountNotFoundException;
import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.EmailIsInUsageException;
import me.inexactvim.paymentssystem.factory.ServiceFactory;
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

@WebServlet("/register")
public class RegisterController extends HttpServlet {

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
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User user = userService.saveUser(name, surname, email, password);
            Account account = accountService.getAccount(user.getAccountNumber());
            httpSession.invalidate();
            httpSession = req.getSession(true);
            httpSession.setMaxInactiveInterval(15 * 60);
            httpSession.setAttribute("user", user);
            httpSession.setAttribute("account", account);
            clearAttributes(httpSession);
            resp.sendRedirect("/user");
        } catch (DAOException e) {
            alertError(httpSession, req, resp, "An error occurred. Please, try again later");
        } catch (EmailIsInUsageException e) {
            alertError(httpSession, req, resp, "This email is already in use");
        } catch (AccountNotFoundException e) {
            alertError(httpSession, req, resp, "Your account is not available. Contact the administrator");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        clearAttributes(req.getSession(true));
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    private void clearAttributes(HttpSession session) {
        session.removeAttribute("name");
        session.removeAttribute("surname");
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
        httpServletRequest.getRequestDispatcher("/register.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
