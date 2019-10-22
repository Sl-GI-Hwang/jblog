package kr.co.itcen.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@PropertySource("classpath:kr/co/itcen/jblog/config/web/properties/multipart.properties")
public class FileuploadConfig extends WebMvcConfigurerAdapter {
	

	@Autowired
	Environment env;
	
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(env.getProperty("maxUUploadSize", Integer.class));
		commonsMultipartResolver.setMaxInMemorySize(env.getProperty("maxInMemorySize", Integer.class));
		commonsMultipartResolver.setDefaultEncoding(env.getProperty("UTF-8"));
		
		return commonsMultipartResolver;
		
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler(env.getProperty("resourceMapping"))
			.addResourceLocations("file:" + env.getProperty("uploadLocation"));
	}
}
