package me.inexactvim.paymentssystem.filter;

import me.inexactvim.paymentssystem.object.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        urlPatterns = {
                "/admin",
                "/admin/*"
        })
public class AdminFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession(true);

        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");

            switch (user.getRole()) {
                case ADMIN:
                    chain.doFilter(req, res);
                    break;
                case CLIENT:
                    res.sendRedirect("/user");
                    break;
            }
        } else {
            res.sendRedirect("/login");
        }
    }
}
