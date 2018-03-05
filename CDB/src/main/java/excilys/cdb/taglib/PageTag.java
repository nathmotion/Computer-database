package main.java.excilys.cdb.taglib;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PageTag extends SimpleTagSupport {
	private int offset=0;
	public String target;
	private int limit;
	StringWriter sw = new StringWriter();
	
	public void doTag() throws JspException , IOException{
		if(offset!=0) {
			JspWriter out = getJspContext().getOut();
			out.println(" taglib different de null");
		}
		else {
			getJspBody().invoke(sw);
	        getJspContext().getOut().println(sw.toString());
		}
	}

	
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}


	public String getTarget() {
		return target;
	}


	public void setTarget(String target) {
		this.target = target;
	}
	
	
	
}
