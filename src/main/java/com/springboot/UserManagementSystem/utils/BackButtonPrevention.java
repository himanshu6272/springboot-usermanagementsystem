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
//@WebFilter(urlPatterns = {"/admin","/viewPage", "/editPage"})
//public class BackButtonPrevention implements Filter {
//	private static final Logger log = Logger.getLogger(BackButtonPrevention.class);
//	public void destroy() {	}
//
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//		HttpServletResponse res = (HttpServletResponse) response;
//		HttpServletRequest req = (HttpServletRequest) request;
//
//		 HttpSession session=req.getSession();
////		 Object obj = session.getAttribute("loggedIn");
//		 if(session==null)
//		 {
//			res.sendRedirect("login");
//		 }
//		 else
//		 {
//			 	res.setHeader("Cache-Control","no-chache,no-store,must-revalidate");
//				res.setHeader("Pragma", "no-chache");
//				res.setDateHeader("Expires", 0);
//				chain.doFilter(request, response);
//		 }
//	}
//	public void init(FilterConfig fConfig) throws ServletException {	}
//
//}
