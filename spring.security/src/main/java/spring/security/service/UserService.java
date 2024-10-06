package spring.security.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import spring.security.entity.Users;
import spring.security.repository.MyUserRepository;

@Service
public class UserService {

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	@Autowired
	private MyUserRepository userRepo;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JWTUtilService jwtutil;
	
	public Users register(Users u) {
		u.setPassword(encoder.encode(u.getPassword()));
		return userRepo.saveAndFlush(u);
	}
	
	public List<Users> findall(){
		return userRepo.findAll();
	}

	public String verify(Users u) {
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword()));
		if(authentication.isAuthenticated()) {
			String token = jwtutil.generateToken(u.getUsername());
			return token;
		}
		return "Failure";
	}

}
