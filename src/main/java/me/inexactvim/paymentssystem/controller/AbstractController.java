package me.inexactvim.paymentssystem.controller;

import me.inexactvim.paymentssystem.factory.ServiceFactory;
import me.inexactvim.paymentssystem.service.AccountService;
import me.inexactvim.paymentssystem.service.CreditCardService;
import me.inexactvim.paymentssystem.service.PaymentService;
import me.inexactvim.paymentssystem.service.UserService;
import me.inexactvim.paymentssystem.util.Alert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

public class AbstractController extends HttpServlet {

    protected static AccountService accountService;
    protected static CreditCardService creditCardService;
    protected static PaymentService paymentService;
    protected static UserService userService;

    static {
        accountService = ServiceFactory.getAccountService();
        creditCardService = ServiceFactory.getCreditCardService();
        paymentService = ServiceFactory.getPaymentService();
        userService = ServiceFactory.getUserService();
    }

    private String pattern;

    @Override
    public void init() {
        pattern = getMappings().iterator().next();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        clearAttributes(request);
        forward(request, response);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getSessionAttribute(HttpSession session,
                                        String attributeName) {
        return (T) session.getAttribute(attributeName);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getRequestAttribute(HttpServletRequest request,
                                        String attributeName) {
        return (T) request.getAttribute(attributeName);
    }

    protected void alertError(String message,
                              HttpServletRequest request,
                              HttpServletResponse response) throws IOException, ServletException {
        alert0(message, Alert.Type.ERROR, request, response);
    }

    protected void alertSuccess(String message,
                                HttpServletRequest request,
                                HttpServletResponse response) throws IOException, ServletException {
        alert0(message, Alert.Type.SUCCESS, request, response);
    }

    protected void alertWarning(String message,
                                HttpServletRequest request,
                                HttpServletResponse response) throws IOException, ServletException {
        alert0(message, Alert.Type.WARNING, request, response);
    }

    private void alert0(String message,
                        Alert.Type type,
                        HttpServletRequest request,
                        HttpServletResponse response) throws IOException, ServletException {
        clearAttributes(request);
        request.setAttribute("alert", new Alert(type, message));
        forward(request, response);
    }

    protected void clearAttributes(HttpServletRequest request) {
        request.removeAttribute("alert");
    }

    protected void forward(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(pattern + ".jsp").forward(request, response);
    }

    private Collection<String> getMappings() {
        return getServletContext().getServletRegistration(getServletName()).getMappings();
    }
}
