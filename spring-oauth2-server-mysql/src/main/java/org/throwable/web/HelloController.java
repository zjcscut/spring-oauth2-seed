package org.throwable.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/12/24 14:31
 */
@RestController
public class HelloController {

	@GetMapping("/api/get")
	public String helloGet(){
		return "Get hello world!";
	}

	@PostMapping("/api/post")
	public String helloPost(){
		return "Post hello world!";
	}

	@GetMapping("/api/user")
	public Object getUser(HttpServletRequest request){
		SecurityContextImpl context = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		if (null != context){
			Authentication authentication = context.getAuthentication();
			if (null != authentication){
				return authentication.getPrincipal();
			}
		}
		return "None";
	}
}
