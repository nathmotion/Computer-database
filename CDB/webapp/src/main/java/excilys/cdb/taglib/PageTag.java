package main.java.excilys.cdb.taglib;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import main.java.excilys.cdb.model.Computer;

public class PageTag extends SimpleTagSupport {
	private String action;
	Page<Computer> page;
	String search;
	Sort.Direction order;
	int numpage;
	StringWriter sw = new StringWriter();

	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		if (action.equals("next")) {
			out.println("<a href=\"dashboard.html?numpage=" + actionPage() + "&search=" + search + "&order=" + order
					+ "\" aria-label=\"Next\"> <span aria-hidden=true>&raquo;</span>\n" + "</a>");
		}
		if (action.equals("previous")) {
			out.println(" <a href=\"dashboard.html?numpage=" + actionPage() + "&search=" + search + "&order=" + order
					+ "\" \n	aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span>\n</a>");
		}
		out.println("<a href=\"dashboard.html?numpage=" + (page.getTotalPages() - 1) + "/>");
	}

	/**
	 * ===== [ GESTION ] : ACTIONS DEMANDE POUR AFFICHER LA PAGE DE COMPUTER ====
	 * 
	 * @param action
	 * @param count
	 */
	public int actionPage() {
		switch (action) {
		case "next":
			if (page.hasNext()) {
				return page.getNumber() + 1;
			} else {
				return page.getNumber();
			}

		case "previous":
			if (page.hasPrevious()) {
				return page.getNumber() - 1;
			} else {
				return page.getNumber();
			}
		}
		return page.getNumber();
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

}
