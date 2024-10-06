package spring.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import spring.security.entity.Users;
import spring.security.repository.MyUserRepository;
import spring.security.service.UserService;

@RestController
public class UserController {

	@Autowired
	public UserService userService;
	
	@PostMapping("/register")
	public Users registerUser(@RequestBody Users newUser) {
		return userService.register(newUser);
	}
	
	@GetMapping("/users")
	public List<Users> getUsers(){
		return userService.findall();
	}
	
	@PostMapping("/login")
	public String loginUser(@RequestBody Users u) {
		
		return userService.verify(u);
	}
}
