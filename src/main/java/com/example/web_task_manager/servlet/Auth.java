package com.example.web_task_manager.servlet;

import com.example.web_task_manager.users.Users;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.nonNull;

public class Auth implements Filter {
    Users users = new Users("users.txt");

    public Auth() throws IOException {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        Users users = new Users("users.txt");;

        final HttpSession session = req.getSession();

        if (!nonNull(session) ||
                !nonNull(session.getAttribute("login")) ||
                !nonNull(session.getAttribute("password"))) {
            Objects.requireNonNull(req.getSession()).setAttribute("password", password);
            req.getSession().setAttribute("login", login);
        } else if (users.getUser(login) != null) {
            moveToTasks(req, res, login);
        }
    }
    /**
     * Move user to menu.
     * If access 'admin' move to admin menu.
     * If access 'user' move to user menu.
     */
    private void moveToTasks(final HttpServletRequest req,
                            final HttpServletResponse res,
                            final String login) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/tasks.jsp").forward(req, res);
    }

    @Override
    public void destroy() {
    }

}
