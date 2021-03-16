package com.example.web_task_manager.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        String loginURL = request.getContextPath() + "/login";
        String signupURL = request.getContextPath() + "/signup";
        boolean loggedIn = session != null && session.getAttribute("login") != null;// && session.getAttribute("password") != null;
        boolean isSmthWrong = session != null && session.getAttribute("attempt") != null;
        String reqURI = request.getRequestURI();
//        log.info("requestUI " + reqURI);
        boolean loginRequest = request.getRequestURI().equals(loginURL) || request.getRequestURI().equals(loginURL + ".jsp");
        boolean signupRequest = request.getRequestURI().equals(signupURL) || request.getRequestURI().equals(signupURL + ".jsp");

//        log.info("loginRequest " + (loginRequest ? "true" : "false"));
//        log.info("signupRequest " + (signupRequest ? "true" : "false"));
//        log.info("loggedIn " + (loggedIn ? "true" : "false"));
//        log.info("reqURI " + reqURI);
        if ((loggedIn && reqURI.contains("tasks")) || (loginRequest && !isSmthWrong) || (signupRequest && !isSmthWrong)) {

//            log.info("doFilter");
            chain.doFilter(req, res);
        } else if (loggedIn) {
//            log.info("go to tasks");
            response.sendRedirect("tasks");
        } else {
            log.info("redirect");
            String page = reqURI.contains("signup") ? "signup.jsp" : "login.jsp";
            log.info("to " + page);
            if (session != null) session.setAttribute("attempt", null);
            response.sendRedirect(page);
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
            req.getRequestDispatcher("/index.jsp").forward(req, res);
        } else {
            req.getSession().setAttribute("password", password);
            req.getSession().setAttribute("login", login);
            req.getRequestDispatcher("/index.jsp").forward(req, res);
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
            req.getRequestDispatcher("/index.jsp").forward(req, res);
            //res.sendRedirect("/WEB-INF/index.jsp");
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
