package com.letswork.springservice.filter;

import com.letswork.springservice.auth.JwtTokenProvider;
import com.letswork.springservice.generalexception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class AuthFilter implements Filter {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws AuthenticationException, ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;
        String partURI = req.getRequestURI();
        String method = req.getMethod();
        System.out.println(method + " " + partURI);

        if (method.compareTo("OPTIONS") == 0
                ||partURI.compareTo("/auth/login") == 0
                || partURI.compareTo("/auth/sign_up") == 0
                || partURI.compareTo("/auth/failed") == 0) {
            //
            //No need to authenticate part
            //
            chain.doFilter(request, response);
        } else {
            String authString = req.getHeader("Authorization");
            if (authString == null) {
                System.out.println("Authentication null");
                httpRes.sendError(401, "Authentication failed");
                return;
            }
            String[] result = authString.split(" ");
            if (result[0].isEmpty() || result[1].isEmpty()) {
                System.out.println("Authentication not in form");
                httpRes.sendError(401, "Authentication failed");
                return;
            }
            Long userId = Long.parseLong(result[0]);
            if (!tokenProvider.validateToken(result[1])) {
                httpRes.sendError(401, "Authentication token not valid");
                return;
            }
            Long tokenUserId = tokenProvider.getUserIdFromJWT(result[1]);
            if (userId.compareTo(tokenUserId) != 0) {
                httpRes.sendError(401, "Authentication wrong id");
                return;
            }
            else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
