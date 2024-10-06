package spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import spring.security.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private JwtFilter jwtFilter;
	
	/*
	 * Filters added - bean responsible for configuring all the http security 
	 */
	@Bean
	public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
		/*
		http.csrf(customizer -> customizer.disable()); //disables csrf
		http.authorizeHttpRequests(req -> req.requestMatchers("login","register").permitAll()
				.anyRequest().authenticated()); //authenticate all the requests
//		http.formLogin(Customizer.withDefaults());		// allows form loading for login
		http.httpBasic(Customizer.withDefaults());		// allows API hit via Postman			
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		//while accession from browser, after login it tries to hit / which is a new resource and new resource means a new session
		
		
		return http.build();		//Returns the object of SecurityFilterChain which acts as filters before the req reches DispatcherServlet   */
		
		return http.csrf(customizer -> customizer.disable())
					.authorizeHttpRequests(req -> req.requestMatchers("login","register")
					.permitAll()
					.anyRequest().authenticated())
					.httpBasic(Customizer.withDefaults())
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) //tells to add filter before defaults
					.build();
	}
	
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(myUserDetailsService);
		return provider;
	}
	
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
		
	}
}
