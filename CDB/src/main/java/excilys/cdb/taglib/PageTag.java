package main.java.excilys.cdb.taglib;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;

public class PageTag extends SimpleTagSupport {
	private Page<Computer> page ;
	private String target;
	private String action;
	StringWriter sw = new StringWriter();

	public void doTag() throws JspException , IOException{
		JspWriter out = getJspContext().getOut();
		if(action.equals("next")) {
			out.println("<a href=\"dashboard.html?page="+(page.getCurrent()+1)+" aria-label=\"Next\"> <span\n" + 
					"					aria-hidden=\"true\">&raquo;</span>\n" + 
					"			</a>");
		} 
		if(action.equals("previous")){
		out.println(" <a href=\\"+target+"?page"+(page.getCurrent()-1)+"\n" + 
				"					aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span>\n" + 
				"				</a>");
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

}
