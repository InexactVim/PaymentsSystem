package me.inexactvim.paymentssystem.controller;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.user.IncorrectCredentialsException;
import me.inexactvim.paymentssystem.object.User;
import me.inexactvim.paymentssystem.object.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends AbstractController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userService.checkCredentialsAndGetUser(email, password);
            httpSession.invalidate();
            httpSession = request.getSession(true);
            httpSession.setMaxInactiveInterval(15 * 60);
            httpSession.setAttribute("user", user);
            if (user.getRole() == UserRole.CLIENT) {
                response.sendRedirect("/user");
            } else {
                response.sendRedirect("/admin");
            }
            logger.info("User " + user.getName() + " " + user.getSurname() + " has just logged in");
        } catch (DAOException e) {
            logger.error("An error occurred with database while loading user data", e);
            alertError("An error occurred. Please, try again later", request, response);
        } catch (IncorrectCredentialsException e) {
            logger.warn("Someone has tried to logged in with incorrect credentials (Email: '" + email + ", Info: '" + e.getMessage() + "'')");
            alertError(e.getMessage(), request, response);
        }
    }
}
