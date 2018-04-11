package main.java.excilys.cdb.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import main.java.excilys.cdb.hibernateconfiguration.JpaConfig;

@Configuration
@ComponentScan(basePackages = { "main.java.excilys.cdb.repositories","main.java.excilys.cdb.model","main.java.excilys.cdb.configuration","main.java.excilys.cdb.hibernateconfiguration"})
@EnableTransactionManagement
public class AppAnnontationConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {JpaConfig.class};
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
