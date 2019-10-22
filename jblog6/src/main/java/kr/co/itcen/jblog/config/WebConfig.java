package kr.co.itcen.jblog.config;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import kr.co.itcen.jblog.security.AuthInterceptor;
import kr.co.itcen.jblog.security.LoginIntercepter;
import kr.co.itcen.jblog.security.LogoutIntercepter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	// Message Converter
		@Bean
		public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
			Jackson2ObjectMapperBuilder builder = 
					new Jackson2ObjectMapperBuilder()
					.indentOutput(true)
					.dateFormat(new SimpleDateFormat("yyyy-mm-dd"))
					.modulesToInstall(new ParameterNamesModule());
			
			MappingJackson2HttpMessageConverter converter 
				= new MappingJackson2HttpMessageConverter(builder.build());
			
			converter.setSupportedMediaTypes(
				Arrays.asList(
					new MediaType("application", "json", Charset.forName("UTF-8"))
				)
			);
			
			return converter;
		}
		
		@Bean
		public StringHttpMessageConverter stringHttpMessageConverter() {
			StringHttpMessageConverter converter = new StringHttpMessageConverter();
			converter.setSupportedMediaTypes(
				Arrays.asList(
					new MediaType("text", "html", Charset.forName("UTF-8"))
				)
			);
			
			return converter;
		}

		@Override
		public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
			converters.add(mappingJackson2HttpMessageConverter());
			converters.add(stringHttpMessageConverter());
		}
		
	

	@Bean
	public LoginIntercepter LoginIntercepter() {
		return new LoginIntercepter();
	}
	
	@Bean
	public LogoutIntercepter LogoutIntercepter() {
		return new LogoutIntercepter();
	}
	
	@Bean
	public AuthInterceptor AuthInterceptor() {
		return new AuthInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(LoginIntercepter())
			.addPathPatterns("/user/auth");
		registry
			.addInterceptor(LogoutIntercepter())
			.addPathPatterns("/user/logout");
		registry
			.addInterceptor(AuthInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/user/auth")
			.excludePathPatterns("/user/logout")
			.excludePathPatterns("/assets/**");
	}
}
