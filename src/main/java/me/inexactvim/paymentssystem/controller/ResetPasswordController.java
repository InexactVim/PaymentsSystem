package me.inexactvim.paymentssystem.controller;

import me.inexactvim.paymentssystem.exception.DAOException;
import me.inexactvim.paymentssystem.exception.EmailMessagingException;
import me.inexactvim.paymentssystem.exception.user.IncorrectCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reset_password")
public class ResetPasswordController extends AbstractController {

    private static Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        try {
            userService.resetUserPassword(email);
            alertSuccess("A new password was sent to email", request, response);
            logger.info("User with email " + email + " has just reset password");
        } catch (DAOException e) {
            logger.error("An error occurred with database", e);
            alertError("An error occurred. Please, try again later", request, response);
        } catch (IncorrectCredentialsException e) {
            alertError(e.getMessage(), request, response);
        } catch (EmailMessagingException e) {
            logger.error("An error occurred with email sending", e);
            alertError("An error occurred. Please, try again later", request, response);
        }
    }
}
