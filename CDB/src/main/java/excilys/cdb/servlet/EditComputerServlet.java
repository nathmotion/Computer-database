package main.java.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editComputer.html")
public class EditComputerServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selection =request.getParameter("cmpedit");
		System.out.println(selection);	
		request.getRequestDispatcher("WEB-INF/views/editComputer.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selection =request.getParameter("cmpedit");
		System.out.println(" post "+selection);	
		doGet(request,response);
	}

}
