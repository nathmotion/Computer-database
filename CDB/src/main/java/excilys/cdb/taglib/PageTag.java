package main.java.excilys.cdb.taglib;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;

public class PageTag extends SimpleTagSupport {
	private Page<Computer> page;
	private String target;
	private String action;
	private String nbElement;


	StringWriter sw = new StringWriter();

	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		if (action.equals("next")) {
			out.println("<a href=\"dashboard.html?actionpage=next\" aria-label=\"Next\"> <span aria-hidden=true>&raquo;</span>\n" + "</a>");
		}
		if (action.equals("previous")) {
			out.println(" <a href=\"dashboard.html?actionpage=previous\" \n"
					+ "					aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span>\n"
					+ "				</a>");
		}
		if(action.equals("numpage")) {
			int nbPage=(Integer.parseInt(nbElement)/page.limit);
			if(page.current>2) {
				out.println("<a href=\"dashboard.html?gopage="+(page.current-2)+"\" >"+(page.current-2) + "</a>");
			}
			if(page.current>1) {
				out.println("<a href=\"dashboard.html?gopage="+(page.current-1)+"\" >"+(page.current-1) + "</a>");
			}
			out.println("<a href=\"#\"> - </a>");
			if(page.current<nbPage) {
				out.println("<a href=\"dashboard.html?gopage="+(page.current+1)+"\" >"+(page.current+1) + "</a>");
			}
			if(page.current<nbPage-1) {
				out.println("<a href=\"dashboard.html?gopage="+(page.current+2)+"\" >"+(page.current+2) + "</a>");
			}
			if(page.current<2) {
				out.println("<a href=\"dashboard.html?gopage="+(page.current+3)+"\" >"+(page.current+3) + "</a>");
			}
			if(page.current<1) {
				out.println("<a href=\"dashboard.html?gopage="+(page.current+4)+"\" >"+(page.current+4) + "</a>");
			}
		}
	}

	public Page<Computer> getPage() {
		return page;
	}

	public void setPage(Page<Computer> page) {
		this.page = page;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	public String getNbElement() {
		return nbElement;
	}

	public void setNbElement(String nbElement) {
		this.nbElement = nbElement;
	}



}
