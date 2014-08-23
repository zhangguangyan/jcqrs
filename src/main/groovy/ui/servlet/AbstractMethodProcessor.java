package ui.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ui.mvc.Controller;
import ui.mvc.View;

public abstract class AbstractMethodProcessor implements RequestMethodProcessor {
	protected View invokeMethod(Controller controller, String name, Map params) {
		try {
			Object result = null;
			Method m = null;
			if (params.size()==0) {
				m = controller.getClass().getDeclaredMethod(name);
				result = m.invoke(controller);
			} else {
				m = controller.getClass().getDeclaredMethod(name, Map.class);
				result = m.invoke(controller,params);
			}
			return (View)result;
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} 
	}
	
	protected String getMethodName(HttpServletRequest request) {
		String fullUrl = request.getRequestURI();
	    int lastPathSeparator = fullUrl.lastIndexOf("/") + 1;
	    
	    if (lastPathSeparator != -1) {
	        String selectedURL = fullUrl.substring(lastPathSeparator, fullUrl.length());
	        int dotPosition = selectedURL.indexOf('.');
	        String action = selectedURL.substring(0,dotPosition);
	        
	        return action;
	    } else {
	    	throw new RuntimeException("wrong request url: "+ fullUrl);
	    }
	}
	
	protected void dispatch(String page, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getSession().getServletContext()
				.getRequestDispatcher("/" + page);

		dispatcher.forward(request, response);
	}
	
	protected void redirect(String page, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect(page);
	}
}
