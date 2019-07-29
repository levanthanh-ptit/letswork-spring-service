package com.letswork.springservice.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(1)
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String partURI = req.getRequestURI();
        System.out.println(partURI);
        //
        //No need to authenticate part
        //
//        if (!(partURI.compareTo("/auth/login") == 0
////                || partURI.compareTo("/auth/sign_up") == 0
////                || partURI.compareTo("/project/info") == 0
////                || partURI.compareTo("/user/info") == 0
////        )) {
////            if(req.getHeader("Authorization")!=null){
////                System.out.println("Authentication failed");
////                throw new BadRequestException("Auth fail");
////            }
////        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
