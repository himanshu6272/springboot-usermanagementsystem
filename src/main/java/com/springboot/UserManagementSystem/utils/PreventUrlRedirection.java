//package com.springboot.UserManagementSystem.utils;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//@WebFilter({"/","/home","/login","/register","/forgot","/reset"})
//public class PreventUrlRedirection implements Filter {
//    private static final Logger log = Logger.getLogger(PreventUrlRedirection.class);
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse res = (HttpServletResponse) response;
//        HttpServletRequest req = (HttpServletRequest) request;
//
//        HttpSession session=req.getSession(false);
//        Object obj = null;
//        if (session!=null){
//            obj = session.getAttribute("loggedIn");
//        }
//        if (obj != null){
//            String loggedIn = (String) obj;
//            if (loggedIn.equals("admin")){
//                res.sendRedirect("admin");
//            }else {
//                res.sendRedirect("viewPage");
//            }
//        }
//        else
//        {
//            res.setHeader("Cache-Control","no-chache,no-store,must-revalidate");
//            res.setHeader("Pragma", "no-chache");
//            res.setDateHeader("Expires", 0);
//            chain.doFilter(request, response);
//        }
//    }
//}
