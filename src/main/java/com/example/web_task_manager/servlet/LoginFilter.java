package com.example.web_task_manager.servlet;

import com.example.web_task_manager.users.User;
import com.example.web_task_manager.users.Users;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import static java.util.Objects.nonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);
    @Override public void init(FilterConfig config) {}
    @Override public void destroy() {}
    @Override public void doFilter(ServletRequest req,
                                   ServletResponse res,
                                   FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        String loginURL = request.getContextPath() + "/login.jsp";

        boolean loggedIn = session != null && session.getAttribute("login") != null;// && session.getAttribute("password") != null;
        boolean loginRequest = request.getRequestURI().equals(loginURL) || request.getRequestURI().equals(loginURL + ".xhtml");

        if(loggedIn || loginRequest) {
            log.info("doFilter");
            chain.doFilter(req, res);
        }
        else {
            log.info("redirect");
            response.sendRedirect("login.jsp");
        }
    }
}
/*public class Auth implements Filter {
    private FilterConfig filterConfig;

    private static final Logger log = LoggerFactory.getLogger(Auth.class);

    public Auth() throws IOException {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
            throws IOException, ServletException {

        log.info("doFilter");
        //ServletContext ctx = filterConfig.getServletContext();
        //RequestDispatcher dispatcher = ctx.getRequestDispatcher("/login.jsp");
        //dispatcher.forward(request, response);
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        req.getRequestDispatcher("/login.jsp").forward(req, res);
        Users users = new Users();
        users.addUser(new User("a", "qwerty"));
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        log.info("login " + login);
        log.info("password " + password);

        final HttpSession session = req.getSession();
        if (nonNull(session) &&
                nonNull(session.getAttribute("login")) &&
                nonNull(session.getAttribute("password"))) {
            req.getRequestDispatcher("/tasks.jsp").forward(req, res);
        } else {
            req.getSession().setAttribute("password", password);
            req.getSession().setAttribute("login", login);
            req.getRequestDispatcher("/tasks.jsp").forward(req, res);
        }

    }


    private void moveToTasks(final HttpServletRequest req,
                             final HttpServletResponse res,
                             final String login,
                             final String password,
                             final Users users)
            throws ServletException, IOException {
        User requiredUser = users.getUser(login);
        log.info("user " + requiredUser.getName());
        User user = null;
        //if (requiredUser.getEncPassword().equals(password)) {
            log.info("111");
            user = requiredUser;
            req.getRequestDispatcher("/tasks.jsp").forward(req, res);
            //res.sendRedirect("/WEB-INF/tasks.jsp");
        /*} else {
            PrintWriter pw = res.getWriter();
            pw.println("alert('login or password is incorrect');");
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, res);
            //res.sendRedirect("/login.jsp");
        }


    }

    @Override
    public void destroy() {
    }

}*/
