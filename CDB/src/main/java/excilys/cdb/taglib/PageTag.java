package main.java.excilys.cdb.taglib;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PageTag extends SimpleTagSupport {
	private String offset ;
	public String target;
	private String limit;
	StringWriter sw = new StringWriter();

	public void doTag() throws JspException , IOException{
		JspWriter out = getJspContext().getOut();
		out.println(" <a href=\\"+target+"?page"+offset+"\n" + 
				"					aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span>\n" + 
				"				</a>");
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
