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

@WebFilter(urlPatterns = {
        "/login",
        "/resetPassword",
        "/register"
})
public class AuthorizedUserFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession(true);

        if (session.getAttribute("user") == null) {
            chain.doFilter(req, res);
        } else {
            User user = (User) session.getAttribute("user");

            switch (user.getRole()) {
                case ADMIN:
                    res.sendRedirect("/admin");
                    break;
                case CLIENT:
                    res.sendRedirect("/client");
                    break;
            }
        }
    }
}
