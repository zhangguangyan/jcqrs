package cqrs.mr.ui.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cqrs.mr.ui.mvc.Controller;

public class RequestProcessor {
	private final HashMap<String,RequestMethodProcessor> requestMethods = new HashMap<String,RequestMethodProcessor>(){
		private static final long serialVersionUID = 1L;
		{
			put("GET",new GetMethodProcessor());
			put("POST",new PostMethodProcessor());
		}
	};
	
	private Controller controller;	
	
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestMethodProcessor methodProcessor = requestMethods.get(request.getMethod());
		methodProcessor.process(controller,request,response);
	}
	//
	public void setController(Controller controller) {
		this.controller = controller;
	}
}
