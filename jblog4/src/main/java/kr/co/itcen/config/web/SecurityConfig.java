package kr.co.itcen.config.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import kr.co.itcen.jblog.security.AuthInterceptor;
import kr.co.itcen.jblog.security.LoginIntercepter;
import kr.co.itcen.jblog.security.LogoutIntercepter;

public class SecurityConfig extends WebMvcConfigurerAdapter {
		
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub
		super.addArgumentResolvers(argumentResolvers);
	}

	@Bean
	public LoginIntercepter loginIntercepter() {
		return new LoginIntercepter();
	}
	
	@Bean
	public LogoutIntercepter logoutIntercepter() {
		return new LogoutIntercepter();
	}
	
	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(loginIntercepter())
			.addPathPatterns("/user/auth");
		registry
			.addInterceptor(logoutIntercepter())
			.addPathPatterns("/user/logout");
		registry
			.addInterceptor(authInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/user/auth")
			.excludePathPatterns("/user/logout")
			.excludePathPatterns("/assets/**");
	}
}
