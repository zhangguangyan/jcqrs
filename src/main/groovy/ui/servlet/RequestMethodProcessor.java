package ui.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ui.mvc.Controller;


public interface RequestMethodProcessor {
	void process(Controller controller, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
