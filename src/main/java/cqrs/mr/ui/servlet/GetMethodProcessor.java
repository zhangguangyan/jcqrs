package cqrs.mr.ui.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cqrs.mr.ui.mvc.Controller;
import cqrs.mr.ui.mvc.View;

public class GetMethodProcessor extends AbstractMethodProcessor {

	@Override
	public void process(Controller controller, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String methodName = getMethodName(request);
		Map params = request.getParameterMap();
		View view = invokeMethod(controller, methodName, params);
		//
		if (view.getBeanId()!=null) {
			request.setAttribute(view.getBeanId(), view.getData());
		}
		dispatch(view.getName(), request, response);
	}
}
