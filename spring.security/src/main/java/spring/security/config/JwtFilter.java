package spring.security.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import spring.security.service.JWTUtilService;
import spring.security.service.MyUserDetailsService;

@Component
public class JwtFilter extends OncePerRequestFilter{ //one filter created for each req

	@Autowired
	private JWTUtilService jwtService;
	
	@Autowired
	ApplicationContext context;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//1. Fetch the token from header - Authorization
		//2. Get the username from the token
		//3. Validate the token -> if valid, create an authentication obj
		// For validation, one needs token and the userdetails which needs to be fetched from db
		
		
		String authHeader =  request.getHeader("Authorization");
		String token =null;
		String username=null;
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = jwtService.extractUserName(token);
		}
		
		//getting the auth obj
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
			
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            if(jwtService.validateToken(token, userDetails)) {
            	UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
            	authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            	SecurityContextHolder.getContext().setAuthentication(authToken);
            }
		}
		filterChain.doFilter(request, response);
		
	}

}
