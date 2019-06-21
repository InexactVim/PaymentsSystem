package me.inexactvim.paymentssystem.controller;

import me.inexactvim.paymentssystem.factory.ServiceFactory;
import me.inexactvim.paymentssystem.object.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);
        String email = req.getParameter("email");

        if (!ServiceFactory.getUserService().isRegistered(email)) {
            clearAttributes(httpSession);
            httpSession.setAttribute("incorrectEmail", "User with such email is not registered");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {
            String password = req.getParameter("password");

            if (!ServiceFactory.getUserService().isPasswordValid(email, password)) {
                clearAttributes(httpSession);
                httpSession.setAttribute("incorrectPassword", "Password is incorrect");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            } else {
                httpSession.invalidate();
                httpSession = req.getSession(true);
                httpSession.setMaxInactiveInterval(15 * 60);
                User user = ServiceFactory.getUserService().getUser(email);
                httpSession.setAttribute("user", user);
                clearAttributes(httpSession);
                resp.sendRedirect("/client");
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        clearAttributes(req.getSession(true));
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
    
    private void clearAttributes(HttpSession session) {
        session.removeAttribute("email");
        session.removeAttribute("password");
        session.removeAttribute("incorrectEmail");
        session.removeAttribute("incorrectPassword");
    }
}
