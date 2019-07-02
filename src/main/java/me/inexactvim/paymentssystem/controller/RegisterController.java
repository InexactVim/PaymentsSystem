package me.inexactvim.paymentssystem.controller;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.user.EmailIsInUsageException;
import me.inexactvim.paymentssystem.object.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends AbstractController {

    private static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userService.saveUser(name, surname, email, password);
            httpSession.invalidate();
            httpSession = request.getSession(true);
            httpSession.setMaxInactiveInterval(15 * 60);
            httpSession.setAttribute("user", user);
            clearAttributes(request);
            response.sendRedirect("/user");
            logger.info("A new user has just registered (email: " + email + ")");
        } catch (DAOException e) {
            logger.error("An error occurred with database while saving user data", e);
            alertError("An error occurred. Please, try again later", request, response);
        } catch (EmailIsInUsageException e) {
            alertError("This email is already in use", request, response);
        }
    }
}
