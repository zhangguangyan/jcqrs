package ui.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ui.mvc.Controller;
import ui.mvc.View;

public class PostMethodProcessor extends AbstractMethodProcessor {

	@Override
	public void process(Controller controller, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String methodName = getMethodName(request);
		Map params = request.getParameterMap();
		View view = invokeMethod(controller, methodName, params);
		//request.setAttribute(view.getBeanId(), view.getData());
		redirect(view.getName(), request, response);
	}
}
