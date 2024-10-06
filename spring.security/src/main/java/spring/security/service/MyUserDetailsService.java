package spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import spring.security.entity.Users;
import spring.security.model.UserPrincipal;
import spring.security.repository.MyUserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	public MyUserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user = userRepo.findByName(username);
		if(user == null) {
			System.out.println("User not exists!");
			throw new UsernameNotFoundException("User not exists!");
		}
		
		return new UserPrincipal(user);
	}

}
