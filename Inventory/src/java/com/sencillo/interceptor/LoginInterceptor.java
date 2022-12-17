package com.sencillo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter
{
	/*@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{

		System.out.println("Interceptors...");
		// final HttpSession session = request.getSession(false);

		/*
		 * if (session == null || session.isNew()) { request.getSession(true); System.out.println(">>> session is null or new");
		 * response.sendRedirect(request.getContextPath()+"/login"); return false; } return true;
		 */
		/*
		 * if(session !=null && !session.isNew()) { return true; } else { request.getSession(true); response.sendRedirect(request.getContextPath()+"/login"); return false; }
		 */

		/*
		 * if(request.getSession().getAttribute("userId") != null) { return true; } else { response.sendRedirect(request.getContextPath()+"/login"); return false; }
		 */
/*
		System.out.println(">>>"+request.getRequestURI());
		//if (!request.getRequestURI().endsWith("/login") && !request.getRequestURI().endsWith("/login-post"))
		if (!request.getRequestURI().contains("login"))
		{
			System.out.println(">>>"+request.getSession().getAttribute("userId"));
			if(request.getSession().getAttribute("userId") == null)
			{
				response.sendRedirect(request.getContextPath()+"/login");
				return false;
			}
		}
		else
		{
			if(request.getSession().getAttribute("userId") != null)
			{
				response.sendRedirect(request.getContextPath()+"/index");
				return false;
			}
		}
		return true;
	}
*/
}
