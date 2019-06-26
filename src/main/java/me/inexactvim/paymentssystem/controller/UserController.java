package me.inexactvim.paymentssystem.controller;

import me.inexactvim.paymentssystem.object.User;
import me.inexactvim.paymentssystem.service.PaymentService;
import me.inexactvim.paymentssystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user")
public class UserController extends HttpServlet {

    private UserService userService;
    private PaymentService paymentService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            alertError(session, req, resp, "An error occurred while loading payments history. Please, try again later");
            return;
        }
        req.getRequestDispatcher("/user.jsp").forward(req, resp);
    }

    private void clearAttributes(HttpSession session) {
        session.removeAttribute("alert");
    }

    private void alertError(HttpSession httpSession,
                            HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse,
                            String message) throws ServletException, IOException {
        clearAttributes(httpSession);
        httpSession.setAttribute("alert", "<p class=\"alert alert-danger\">" + message + "</p>");
        httpServletRequest.getRequestDispatcher("/user.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
