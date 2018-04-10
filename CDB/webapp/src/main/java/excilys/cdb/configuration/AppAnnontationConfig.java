package main.java.excilys.cdb.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppAnnontationConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {JdbcConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {WebMvcConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
	      return new String[] { "/" };
	}

}
