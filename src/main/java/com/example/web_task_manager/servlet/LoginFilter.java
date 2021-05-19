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
        String reqURI = request.getRequestURI();
        log.info("requestUI " + reqURI);
        boolean loginRequest = request.getRequestURI().equals(loginURL) || request.getRequestURI().equals(loginURL + ".jsp");
        boolean signupRequest = request.getRequestURI().equals(signupURL) || request.getRequestURI().equals(signupURL + ".jsp");
        String cookieValue = cookieController.getCookieValue(request, CookieName.LOGIN);
//            log.info(cookieValue);
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }



        if (loggedIn || loginRequest || signupRequest) {
            chain.doFilter(req, res);
        } else if (cookieValue != null) {
            User cookieUser = new UserDAO().getUserByName(cookieValue);
            if (cookieUser != null) {
                session.setAttribute("login", cookieValue);
                session.setAttribute("role", cookieUser.getRole());
                response.sendRedirect(request.getContextPath() + "/tasks");
            }
        } else {
            String page = reqURI.contains("signup") ? "signup" : "login";
            log.info("to " + page);
            response.sendRedirect(request.getContextPath() + "/" + page);
        }
    }

}
