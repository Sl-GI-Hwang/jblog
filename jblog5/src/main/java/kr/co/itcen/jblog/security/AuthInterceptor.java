package kr.co.itcen.jblog.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.itcen.jblog.vo.UserVo;


public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler)
		throws Exception {
		
		//1. handler 종류(DefaultServletHttpRequestHandler, HandlerMethod) 
		if( handler instanceof HandlerMethod == false ) {
			return true;
		}

		//2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;

		//3. @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

		//4. @Auth가 없으면 class type에 있을 수 있으므로...
		if(auth == null) {
			//class type에서 @Auth가 있는 지를 확인해 봐야한다.
			if(handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class) != null) {
				auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
			}
		}
		
		//5. @Auth가 없으면
		if(auth == null) {
			return true;
		}
		
		//6. @Auth가 class나 method에 붙어 있기 때문에 인증 여부를 체크한다.
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("authUser") == null) {
			response.sendRedirect(request.getContextPath()+ "/user/login");
			return false;
		}

		//8. Method의 @Auth의 Role 가져오기
		String role = auth.value();
		
		//9. PathVariable에 있는것을 꺼내옴
		Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		//10. 메소드의 @Auth의 Role이 "ADMIN"인 경우
		// AuthUser가 관리자인지 확인한 다음, 아니면 첫페이지로 보내버림
		if("ADMIN".equals(role)) {
			UserVo isAdmin = (UserVo) session.getAttribute("authUser");
			if(isAdmin.getId().equals(pathVariables.get("id")) == false) {
				response.sendRedirect(request.getContextPath());
				return false;
			}
			return true;
		}

		return true;
	}

}