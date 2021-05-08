package com.example.web_task_manager.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import com.example.web_task_manager.CookieName;
import com.example.web_task_manager.controller.CookieController;
import com.example.web_task_manager.dba.UserDAO;
import com.example.web_task_manager.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);
    private static final CookieController cookieController = new CookieController();

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
        boolean loggedIn = session != null && session.getAttribute("login") != null;
        boolean isSmthWrong = session != null && session.getAttribute("attempt") != null;
        String reqURI = request.getRequestURI();
//        log.info("requestUI " + reqURI);
        boolean loginRequest = request.getRequestURI().equals(loginURL) || request.getRequestURI().equals(loginURL + ".jsp");
        boolean signupRequest = request.getRequestURI().equals(signupURL) || request.getRequestURI().equals(signupURL + ".jsp");

//        log.info("loginRequest " + (loginRequest ? "true" : "false"));
//        log.info("signupRequest " + (signupRequest ? "true" : "false"));
//        log.info("loggedIn " + (loggedIn ? "true" : "false"));
//        log.info("reqURI " + reqURI);


        if (loggedIn || (loginRequest && !isSmthWrong) || (signupRequest && !isSmthWrong)) {
//            log.info("doFilter");
            chain.doFilter(req, res);
        } else {
            System.out.println("LOGIN FILTER");
            String userName = cookieController.getCookieValue(request, CookieName.LOGIN);
            System.out.println("username: " + userName);
            String userPassword = cookieController.getCookieValue(request, CookieName.PASSWORD);
            System.out.println("userpassword: " + userPassword);
            User cookieUser = new UserDAO().getUserByName(userName);
            if (cookieUser != null && userPassword.equals(cookieUser.getEncPassword())) {
                System.out.println("EQUALS and etc");
                request.getSession().setAttribute("login", cookieUser.getName()); //dupl
                request.getSession().setAttribute("role", cookieUser.getRole());
                TryChecker.setPropertyOfLoginDiv("none");
                request.getSession().setAttribute("attempt", null);
                chain.doFilter(request, response);
            } else {
                cookieController.eraseCookie(response, CookieName.LOGIN);
                cookieController.eraseCookie(response, CookieName.PASSWORD);
                log.info("redirect");
                String page = reqURI.contains("signup") ? "signup.jsp" : "login.jsp";
                log.info("to " + page);
                if (session != null) session.setAttribute("attempt", null);
                response.sendRedirect(page);
            }
        }
    }

}
