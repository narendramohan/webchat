package com.chat.application.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements javax.servlet.Filter {
    protected ServletContext servletContext;
    protected FilterConfig filterConfig;
    public void init(FilterConfig filterConfig) {
        servletContext = filterConfig.getServletContext();
        this.filterConfig = filterConfig;
    }
 
    public void doFilter(
        ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        HttpSession session = req.getSession(false);
        String uri = req.getRequestURI();
        //System.out.println("session: "+session);
        //System.out.println("getRequestURI(): "+req.getRequestURI());
    	//System.out.println("getRequestURL(): "+req.getRequestURL());
    	if ( uri.indexOf("/css") > 0){
            chain.doFilter(request, response);
        }
        else if( uri.indexOf("/images") > 0){
            chain.doFilter(request, response);
        }
        else if( uri.indexOf("/accessDenied") >= 0){
            chain.doFilter(request, response);
        }
        else if( uri.indexOf("/js") > 0){
            chain.doFilter(request, response);
        }else if( uri.equals("/webchat/") || uri.indexOf("auth/login-user")>0 || uri.indexOf("/logout")>0 || 
        		uri.indexOf("auth/forgot-pwd")>0 || uri.indexOf("/register")>0 || uri.indexOf("/accessDenied")>0){
            chain.doFilter(request, response);
        }else if( uri.indexOf("/doLogin")>0){
            chain.doFilter(request, response);
        } else   if(session!=null){
	        String userName = (String) session.getAttribute("username");
	      //  System.out.println("userName: "+userName);
	        if (userName == null || "".equals(userName)) {
	        	RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
	            PrintWriter out= response.getWriter();
	            out.println("<font color=red>Please login before using the webchat.</font>");
	            rd.include(request, response);
	        		return; //break filter chain, requested JSP/servlet will not be executed
	        }
	        chain.doFilter(request, response);  
        } else {
        	RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            PrintWriter out= response.getWriter();
            out.println("<font color=red>Please login before using the webchat.</font>");
            rd.include(request, response);
        }
     
        //propagate to next element in the filter chain, ultimately JSP/ servlet gets executed
        //chain.doFilter(request, response);        
    }
 
    /**
     * logic to accept or reject access to the page, check log in status
     * @return true when authentication is deemed valid
     */
    protected boolean isAuth(){
		return false;
    	
    }

	public void destroy() {
		this.filterConfig = null;
		
	}
 
}