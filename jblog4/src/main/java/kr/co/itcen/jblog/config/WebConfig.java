package kr.co.itcen.jblog.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import kr.co.itcen.config.web.FileuploadConfig;
import kr.co.itcen.config.web.MVCConfig;
import kr.co.itcen.config.web.SecurityConfig;

@Configuration
@EnableWebMvc
@ComponentScan({"kr.co.itcen.jblog.controller"})
@Import({MVCConfig.class, SecurityConfig.class, FileuploadConfig.class})
public class WebConfig {
//
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		// TODO Auto-generated method stub
//		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//		
//		context.setConfigLocations("kr.co.itcen.jblog.config.WebConfig");
//		servletContext.addListener(new ContextLoaderListener(context));
//		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet());
//		
//		dispatcher.setLoadOnStartup(1);
//		dispatcher.addMapping("/");
//	}

}
