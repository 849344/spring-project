package spring.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import spring.security.model.Candidates;

@RestController
public class SecurityController {
	
	List<Candidates> users =new ArrayList<>(List.of(
			new Candidates(1, "Kritika",29),
			new Candidates(2, "Vishwakarma", 26)
			));

	@GetMapping("/")
	public String defaultPath(HttpServletRequest req) {
		return "Welcome Friend with session id : "+ req.getSession().getId();
	}
	
	@GetMapping("/csrf-token")
	public CsrfToken csrfToken(HttpServletRequest req) {
		return (CsrfToken) req.getAttribute("_csrf");
	}
	

	@GetMapping("/getUsers")
	public List<Candidates> getUsers() {
		return users;
	}
	
	
	@PostMapping("/add")
	public Candidates adUser(@RequestBody Candidates u) {
		users.add(u);
		return u;
	}
}
