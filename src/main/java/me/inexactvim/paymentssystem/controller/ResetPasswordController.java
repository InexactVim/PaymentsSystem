package me.inexactvim.paymentssystem.controller;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.EmailMessagingException;
import me.inexactvim.paymentssystem.exception.IncorrectCredentialsException;
import me.inexactvim.paymentssystem.factory.ServiceFactory;
import me.inexactvim.paymentssystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/reset_password")
public class ResetPasswordController extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = ServiceFactory.getUserService();
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);
        String email = req.getParameter("email");

        try {
            userService.resetUserPassword(email);
            alert(httpSession, req, resp, "success", "A new password was sent to email");
        } catch (DAOException | EmailMessagingException e) {
            alert(httpSession, req, resp, "danger", "An error occurred. Please, try again later");
        } catch (IncorrectCredentialsException e) {
            alert(httpSession, req, resp, "danger", e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        clearAttributes(req.getSession(true));
        req.getRequestDispatcher("/reset_password.jsp").forward(req, resp);
    }

    private void clearAttributes(HttpSession session) {
        session.removeAttribute("email");
        session.removeAttribute("alert");
    }

    private void alert(HttpSession httpSession,
                       HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       String type,
                       String message) throws ServletException, IOException {
        clearAttributes(httpSession);
        httpSession.setAttribute("alert", "<p class=\"alert alert-" + type + "\">" + message + "</p>");
        httpServletRequest.getRequestDispatcher("/reset_password.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
