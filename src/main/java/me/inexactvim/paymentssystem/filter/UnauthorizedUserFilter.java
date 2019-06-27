package me.inexactvim.paymentssystem.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {
        "/",
        "/user",
        "/send_payment",
        "/account/*",
        "/cards/*"
})
public class UnauthorizedUserFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession(true);

        if (session.getAttribute("user") != null) {
            chain.doFilter(req, res);
        } else {
            res.sendRedirect("/login");
        }
    }
}
